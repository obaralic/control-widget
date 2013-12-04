
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

package com.obaralic.toggler.utilities.listeners;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;

import com.obaralic.toggler.activities.ConfigurationsActivity;
import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.service.ContentStateChangeServiceFacade;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.AppWidgetIdPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ClickedFieldTypePersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ShowingActivityPersistance;
import com.obaralic.toggler.utilities.persistence.implementation.WidgetSizePersistence;

/**
 * Custom on click listener used for detection single and double click events.
 */
public class DoubleClickAttempt implements OnDoubleClickListener {

    private static final String TAG = LogUtil.getTag(DoubleClickAttempt.class);

    /**
     * Time to wait on second click.
     */
    private static final long DOUBLE_CLICK_TIMEOUT = 300l;

    /**
     * The timer that checks whether the double click attempt has timed out
     */
    private Timer mDoubleClickTimer;

    private int mNumberOfAttempts;
    private int mWidgetSize;
    private int mFieldOrderNumber;

    public DoubleClickAttempt() {
        mNumberOfAttempts = 0;
        mFieldOrderNumber = 0;
        mWidgetSize = 0;
    }

    @Override
    public void onDoubleClickAttempt(Context context) {
        LogUtil.i(TAG, "Called onDoubleClickAttempt");

        int size = WidgetSizePersistence.getInstance().getWidgetSize(context);
        int field = ClickedFieldTypePersistence.getInstance().getWidgetFieldOrder(context);

        if (mNumberOfAttempts == 0) {
            mWidgetSize = size;
            mFieldOrderNumber = field;

        } else if (mWidgetSize != size || mFieldOrderNumber != field) {
            cancel();

            // ********************************************************************
            // Use return for maybe better user experience!!!
            // return;
            // ********************************************************************
        }

        mNumberOfAttempts++;

        if (mNumberOfAttempts == 1) {
            // Set the timer to detect a time-out of an unsuccessful double click attempt
            scheduleConnectionTimeoutTimer(context);

        } else {
            LogUtil.d(TAG, "Double click performed!");
            cancel();

            // Start Configuration Activity for double click performed
            boolean isActivityShowing = ShowingActivityPersistance.getInstance().isActivityShowing(context);

            if (!isActivityShowing) {
                Intent intent = new Intent(context.getApplicationContext(), ConfigurationsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    @Override
    public void cancel() {
        LogUtil.i(TAG, "Called onClick");

        // Cancel and release Timeout Timer
        cancelTimer();
        releaseTimer();
    }

    @Override
    public boolean isAttemptInProgress() {
        LogUtil.i(TAG, "Called isAttemptInProgress");
        return mDoubleClickTimer != null;
    }

    @Override
    public void onAttemptSucceeded() {
        LogUtil.i(TAG, "Called onAttemptSucceeded");
        cancel();
    }

    /**
     * Call the cancel method for the timer object
     */
    private void cancelTimer() {
        if (mDoubleClickTimer != null) {
            mDoubleClickTimer.cancel();
        }
    }

    /**
     * Release the timer object
     */
    private void releaseTimer() {
        mDoubleClickTimer = null;
        mNumberOfAttempts = 0;
        mFieldOrderNumber = 0;
        mWidgetSize = 0;
    }

    /**
     * Schedules operation that needs to be performed in case od single click.
     */
    private void scheduleConnectionTimeoutTimer(final Context context) {

        // Cancel old one if exists
        cancelTimer();

        // Create the new timer and schedule a timer task
        mDoubleClickTimer = new Timer();

        mDoubleClickTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (isAttemptInProgress()) {
                    LogUtil.d(TAG, "One click performed!");

                    // Release the timer object, so that the calling modules will know that no double click
                    // attempt is in progress
                    releaseTimer();

                    // Start Service for regular click performed only if no Activity is currently displaying
                    boolean isActivityShowing = ShowingActivityPersistance.getInstance().isActivityShowing(
                            context);

                    // Start service for performing toggle logic
                    if (!isActivityShowing) {
                        startTogglerService(context);
                    }

                } else {
                    LogUtil.w(TAG, "The double click attempt was cancelled, do not responde to double click.");
                }
            }

        }, DOUBLE_CLICK_TIMEOUT);
    }

    /**
     * Utility method used for starting {@link ContentStateChangeService} for specific toggle operation.
     */
    private void startTogglerService(Context context) {

        // Get widget id in order to get selected content.
        int appWidgetId = AppWidgetIdPersistence.getInstance().getAppWidgetId(context);
        LogUtil.d(TAG, "App Widget Id from persistence: " + appWidgetId);

        // Get instance info content for the widget id.
        InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
        InstanceInfoBean instanceInfo = dao.getInstanceInfo(context, appWidgetId);
        LogUtil.d(TAG, "Instance: " + instanceInfo);

        // Order number of the pressed field
        int widgetField = ClickedFieldTypePersistence.getInstance().getWidgetFieldOrder(context);
        int index = widgetField - 1;
        ContentType contentType = instanceInfo.mWidgetContents.get(index);

        ContentStateChangeServiceFacade.get().startOnToggle(context, contentType);
    }
}
