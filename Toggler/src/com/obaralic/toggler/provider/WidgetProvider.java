
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

package com.obaralic.toggler.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.provider.types.StackWidgetProvider;
import com.obaralic.toggler.provider.types.WidgetProviderForSize1x1;
import com.obaralic.toggler.provider.types.WidgetProviderForSize4x1;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.InstantiationPersistence;
import com.obaralic.toggler.utilities.values.AirplaneModeUtility;
import com.obaralic.toggler.utilities.values.AutoRotateUtility;
import com.obaralic.toggler.utilities.values.AutoSyncUtility;
import com.obaralic.toggler.utilities.values.BluetoothUtility;
import com.obaralic.toggler.utilities.values.BrightnessUtility;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.GpsUtility;
import com.obaralic.toggler.utilities.values.LedFlashUtility;
import com.obaralic.toggler.utilities.values.MobileDataUtility;
import com.obaralic.toggler.utilities.values.RingerModeUtitliy;
import com.obaralic.toggler.utilities.values.WiFiHotspotUtility;
import com.obaralic.toggler.utilities.values.WiFiUtility;

/**
 * Provides native methods utilized by instantiated widgets.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = LogUtil.getTag(WidgetProvider.class);

    @Override
    public void onEnabled(Context context) {
        LogUtil.d(TAG, "Called onEnabled");

        // Save in persistence info about instantiated widget.
        InstantiationPersistence.getInstance().setAtLeastOneWidgetPresent(context, true);

        // Fill status with the current toggle values
        readAndSetContent(context);

        // Start Widget UI Service.
        WidgetUiServiceFacade widgetUiServiceFacade = WidgetUiServiceFacade.get();
        widgetUiServiceFacade.startOnEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        LogUtil.d(TAG, "Called onDisabled");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        final int[] appWidgetIdsFor1x1 = appWidgetManager.getAppWidgetIds(WidgetProviderForSize1x1
                .getComponentName(context));

        final int[] appWidgetIdsFor4x1 = appWidgetManager.getAppWidgetIds(WidgetProviderForSize4x1
                .getComponentName(context));

        final int[] stackAppWidgetIds = appWidgetManager.getAppWidgetIds(StackWidgetProvider
                .getComponentName(context));

        if (appWidgetIdsFor1x1 == null || appWidgetIdsFor4x1 == null
                || stackAppWidgetIds == null) {
            LogUtil.e(TAG, "One of App Widget Ids arrays returned is null. Exiting.");
            return;
        }
        // If there are no more widgets present at home screen.
        else if (appWidgetIdsFor1x1.length == 0 && appWidgetIdsFor4x1.length == 0
                && stackAppWidgetIds.length == 0) {
            LogUtil.d(TAG, "All the widgets have been removed from the home screen.");

            // Stop Widget UI Service
            WidgetUiServiceFacade widgetUiServiceFacade = WidgetUiServiceFacade.get();
            widgetUiServiceFacade.stopService(context);

            // Memorize in persistence that there are none widgets present.
            InstantiationPersistence.getInstance().setAtLeastOneWidgetPresent(context, false);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogUtil.d(TAG, "Called onUpdate");

        // Fill status with the current toggle values
        readAndSetContent(context);

        // Do a simple widgets redraw.
        WidgetUiServiceFacade widgetUiServiceFacade = WidgetUiServiceFacade.get();
        widgetUiServiceFacade.startForSimpleRedraw(context);
    }

    private void readAndSetContent(Context context) {

        // Retrieve WiFi state value
        boolean isWiFiEnabled = WiFiUtility.isEnabled(context);

        // Retrieve Bluetooth state value
        boolean isBluetoothEnabled = BluetoothUtility.isEnabled(context);

        // Retrieve Mobile Data state value
        boolean isMobileDataEnabled = MobileDataUtility.isEnabled(context);

        // Retrieve GPS state value
        boolean isGpsEnabled = GpsUtility.isGpsEnabled(context);

        // Retrieve Auto Sync state value
        boolean isAutoSyncEnabled = AutoSyncUtility.isEnabled(context);

        // Retrieve Ring Mode state value
        int ringerMode = RingerModeUtitliy.getRingerMode(context);

        // Retrieve Airplane mode state value
        boolean isAirplaneModeEnabled = AirplaneModeUtility.isAirplaneModeOn(context);

        // Retrieve Brightness state value
        int brightnessMode = BrightnessUtility.getBrightnessMode(context);

        // Retrieve WiFi Hotspot state value
        boolean isWiFiHotspotEnabled = WiFiHotspotUtility.isEnabled(context);

        // Retrieve Flash torch state value
        boolean isFlashTorchEnabled = LedFlashUtility.isFlashLightEnabled(context);

        // Retrieve Auto Rotate state value
        boolean isAutoRotateEnabled = AutoRotateUtility.isAutoRotateEnabled(context);

        ContentStatesPersistence statusesPersistence = ContentStatesPersistence.getInstance();
        int[] statusesArray = statusesPersistence.getContentStates(context);

        statusesArray[ContentType.WIFI.getContentId()] = isWiFiEnabled ? WiFiUtility.WIFI_ENABLED
                : WiFiUtility.WIFI_DISABLED;

        statusesArray[ContentType.BLUETOOTH.getContentId()] = isBluetoothEnabled ? BluetoothUtility.BLUETOOTH_ENABLED
                : BluetoothUtility.BLUETOOTH_DISABLED;

        statusesArray[ContentType.MOBILE_DATA.getContentId()] = isMobileDataEnabled ? MobileDataUtility.MOBILE_DATA_ENABLED
                : MobileDataUtility.MOBILE_DATA_DISABLED;

        statusesArray[ContentType.GPS.getContentId()] = isGpsEnabled ? GpsUtility.GPS_ENABLED
                : GpsUtility.GPS_DISABLED;

        statusesArray[ContentType.AUTO_SYNC.getContentId()] = isAutoSyncEnabled ? AutoSyncUtility.AUTO_SYNC_ENABLED
                : AutoSyncUtility.AUTO_SYNC_DISABLED;

        statusesArray[ContentType.AIRPLANE_MODE.getContentId()] = isAirplaneModeEnabled ? AirplaneModeUtility.AIRPLANE_MODE_ENABLED
                : AirplaneModeUtility.AIRPLANE_MODE_DISABLED;

        statusesArray[ContentType.WIFI_HOTSPOT.getContentId()] = isWiFiHotspotEnabled ? WiFiHotspotUtility.WIFI_HOTSPOT_ENABLED
                : WiFiHotspotUtility.WIFI_HOTSPOT_DISABLED;

        statusesArray[ContentType.AUTO_ROTATE.getContentId()] = isAutoRotateEnabled ? AutoRotateUtility.AUTO_ROTATE_ENABLED
                : AutoRotateUtility.AUTO_ROTATE_DISABLED;

        statusesArray[ContentType.RINGER_MODE.getContentId()] = ringerMode;

        statusesArray[ContentType.BRIGHTNESS_MODE.getContentId()] = brightnessMode;

        statusesArray[ContentType.FLASH_TORCH.getContentId()] = isFlashTorchEnabled ? FlashTorchUtility.FLASH_TORCH_ENABLED
                : FlashTorchUtility.FLASH_TORCH_DISABLED;

        statusesPersistence.setContentStates(context, statusesArray);
    }

}
