
/*
 * Copyright 2013 oBaralic, Inc. (owner Zivorad Baralic)
 *            ____                   ___
 *     ____  / __ )____ __________ _/ (_)____
 *    / __ \/ __  / __ `/ ___/ __ `/ / / ___/
 *   / /_/ / /_/ / /_/ / /  / /_/ / / / /__
 *   \____/_____/\__,_/_/   \__,_/_/_/\___/
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.obaralic.toggler.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;
import com.obaralic.toggler.service.commands.ui.WidgetUiServiceCommandInterface;
import com.obaralic.toggler.service.factories.WidgetUiServiceCommandFactory;
import com.obaralic.toggler.service.observers.AutoRotateContentObserver;
import com.obaralic.toggler.service.observers.AutoRotateContentObserver.AutoRotateStateChangedListener;
import com.obaralic.toggler.service.observers.BrightnessContentObserver;
import com.obaralic.toggler.service.observers.BrightnessContentObserver.BrightnessStateChangedListener;
import com.obaralic.toggler.service.threads.WidgetUiServiceWorkerThread;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.AppWidgetIdPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ClickedFieldTypePersistence;
import com.obaralic.toggler.utilities.persistence.implementation.InstantiationPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.WidgetSizePersistence;
import com.obaralic.toggler.utilities.validation.IntentValidator;
import com.obaralic.toggler.utilities.values.AutoRotateUtility;
import com.obaralic.toggler.utilities.values.BrightnessUtility;

/**
 * Service used for handling UI changing operations and updating.
 */
public class WidgetUiService extends Service implements BrightnessStateChangedListener,
        AutoRotateStateChangedListener {

    private static final String TAG = LogUtil.getTag(WidgetUiService.class);

    private WidgetUiServiceWorkerThread mWorkerThread;

    private BrightnessContentObserver mBrightnessContentObserver;
    private AutoRotateContentObserver mAutoRotateContentObserver;

    @Override
    public void onCreate() {
        super.onCreate();

        mWorkerThread = new WidgetUiServiceWorkerThread(this);
        mWorkerThread.start();

        mBrightnessContentObserver = new BrightnessContentObserver(new Handler(), this);
        mAutoRotateContentObserver = new AutoRotateContentObserver(new Handler(), this);

        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
                false, mBrightnessContentObserver);

        contentResolver.registerContentObserver(
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE), false,
                mBrightnessContentObserver);

        contentResolver.registerContentObserver(
                Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false,
                mAutoRotateContentObserver);
    }

    @Override
    public void onDestroy() {
        mWorkerThread.interrupt();
        ContentResolver contentResolver = getContentResolver();

        if (mBrightnessContentObserver != null) {
            contentResolver.unregisterContentObserver(mBrightnessContentObserver);
        } else {
            LogUtil.e(TAG, "Error mBrightnessContentObserver == null");
        }

        if (mAutoRotateContentObserver != null) {
            contentResolver.unregisterContentObserver(mAutoRotateContentObserver);
        } else {
            LogUtil.e(TAG, "Error mAutoRotateContentObserver == null");
        }

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "Called onStartCommand");

        IntentValidator validator = new IntentValidator();

        // If received intent is invalid intent or intent with unknown action
        if (!validator.isOkay(intent)) {
            return stopUponBadIntent();
        }

        String action = intent.getAction();
        LogUtil.d(TAG, "Action: " + action);

        // If no widget is present on the Home screen - also stop the service
        // Stops the Service after the last widget has been removed from the home screen.
        InstantiationPersistence persistence = InstantiationPersistence.getInstance();

        if (!persistence.isAnyWidgetPresent(this)) {
            LogUtil.w(TAG, "No widgets are present - stopping.");
            stopSelf();
            return Service.START_STICKY;
        }

        // Extract a Service command from the Intent
        WidgetUiServiceCommandInterface command = WidgetUiServiceCommandFactory.get(intent);
        if (command == null) {
            return stopUponBadIntent();
        }

        // Set select info into persistence only in case of onClick action.
        if (WidgetUiServiceFacade.isOnWidgetCickAction(action)) {

            // TODO: Let the InstanceInfoBean implements Parcelable and transport that object
            // from Ui?x1Write to this service and on forward.
            setOnClickActionPersistence(this, intent);
        }

        // Execute the command on the worker thread.
        mWorkerThread.addJob(command);

        // We want the Service to stick around
        return Service.START_STICKY;
    }

    /**
     * Sets the data about widget size and selected field into persistence.
     */
    private void setOnClickActionPersistence(Context context, Intent intent) {

        // Sets the id of the selected widget.
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
        AppWidgetIdPersistence.getInstance().setAppWidgetId(context, appWidgetId);
        
        int[] values = getWidgetSizeInFields(intent.getAction());

        // Set the widget size measured in fields into persistence.
        int widgetSizeInFields = values[0];
        WidgetSizePersistence.getInstance().setWidgetSize(this, widgetSizeInFields);

        // Set the index of the pressed field into persistence.
        int orderOfThePressedField = values[1];
        ClickedFieldTypePersistence.getInstance().setWidgetFieldOrder(this, orderOfThePressedField);
    }

    /**
     * Resolves widget size and selected field according to the given action.
     * 
     * @param action
     *            The received intent action.
     * @return Array containing widget size on index 0 and widget field on index 1.
     */
    private int[] getWidgetSizeInFields(String action) {
        int[] values = new int[2];

        if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_1x1_FIELD_1_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.ONE.getWidgetSizeId();
            values[1] = FieldType.ONE.getFieldPosition();

        } else if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_1_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.FIVE.getWidgetSizeId();
            values[1] = FieldType.ONE.getFieldPosition();

        } else if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_2_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.FIVE.getWidgetSizeId();
            values[1] = FieldType.TWO.getFieldPosition();

        } else if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_3_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.FIVE.getWidgetSizeId();
            values[1] = FieldType.THREE.getFieldPosition();

        } else if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_4_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.FIVE.getWidgetSizeId();
            values[1] = FieldType.FOUR.getFieldPosition();

        } else if (WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_5_CLICKED.equals(action)) {
            values[0] = WidgetSizeType.FIVE.getWidgetSizeId();
            values[1] = FieldType.FIVE.getFieldPosition();
        }

        return values;
    }

    /**
     * Stops the service when a null intent is sent. That happens after the system decides to silently kill
     * our process.
     * 
     * @return The {@link Service#START_STICKY} integer value
     */
    private int stopUponBadIntent() {
        LogUtil.w(TAG, "Started for a null or unknown Intent - stopping.");
        stopSelf();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void brightnessStateChanged() {
        LogUtil.d(TAG, "Called brightnessStateChanged");
        Intent intent = new Intent(BrightnessUtility.ACTION_CHANGE_BRIGHTNESS_STATE);
        sendBroadcast(intent);
    }

    @Override
    public void autoRotateStateChanged() {
        LogUtil.d(TAG, "Called autoRotateStateChanged");
        Intent intent = new Intent(AutoRotateUtility.ACTION_CHANGE_AUTO_ROTATE_STATE);
        sendBroadcast(intent);
    }

}
