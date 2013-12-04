
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

import android.content.Context;
import android.content.Intent;

import com.obaralic.toggler.database.dao.enums.ContentType;

public class ContentStateChangeServiceFacade {

    private static ContentStateChangeServiceFacade sInstance;

    /**
     * Class holding all the available Intent actions for starting the {@link ContentStateChangeService}.
     */
    public static class Actions {

        public static final String ACTION_TOGGLE_WIFI = "com.obaralic.toggler.action.ACTION_TOGGLE_WIFI";

        public static final String ACTION_TOGGLE_INTERNET = "com.obaralic.toggler.action.ACTION_TOGGLE_INTERNET";

        public static final String ACTION_TOGGLE_BLUETOOTH = "com.obaralic.toggler.action.ACTION_TOGGLE_BLUETOOTH";

        public static final String ACTION_TOGGLE_GPS = "com.obaralic.toggler.action.ACTION_TOGGLE_GPS";

        public static final String ACTION_TOGGLE_AUTO_SYNC = "com.obaralic.toggler.action.ACTION_TOGGLE_AUTO_SYNC";

        public static final String ACTION_TOGGLE_SETTINGS = "com.obaralic.toggler.action.ACTION_TOGGLE_SETTINGS";

        public static final String ACTION_TOGGLE_BRIGHTNESS = "com.obaralic.toggler.action.ACTION_TOGGLE_BRIGHTNESS";

        public static final String ACTION_TOGGLE_SOUND = "com.obaralic.toggler.action.ACTION_TOGGLE_SOUND";

        public static final String ACTION_TOGGLE_AIRPLANE_MODE = "com.obaralic.toggler.action.ACTION_TOGGLE_AIRPLANE_MODE";

        public static final String ACTION_TOGGLE_WIFI_HOTSPOT = "com.obaralic.toggler.action.ACTION_TOGGLE_WIFI_HOTSPOT";

        public static final String ACTION_TOGGLE_FLASH_TORCH = "com.obaralic.toggler.action.ACTION_TOGGLE_FLASH_TORCH";

        public static final String ACTION_TOGGLE_AUTO_ROTATE = "com.obaralic.toggler.action.ACTION_TOGGLE_AUTO_ROTATE";

    }

    private ContentStateChangeServiceFacade() {
    }

    public static ContentStateChangeServiceFacade get() {
        if (sInstance == null) {
            sInstance = new ContentStateChangeServiceFacade();
        }
        return sInstance;
    }

    public void startOnWiFiToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_WIFI);
        context.startService(intent);
    }

    public void startOnInternetToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_INTERNET);
        context.startService(intent);
    }

    public void startOnBluetoothToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_BLUETOOTH);
        context.startService(intent);
    }

    public void startOnGpsToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_GPS);
        context.startService(intent);
    }

    public void startOnAutoSyncToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_AUTO_SYNC);
        context.startService(intent);
    }

    public void startOnSettingsToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_SETTINGS);
        context.startService(intent);
    }

    public void startOnBrightnessToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_BRIGHTNESS);
        context.startService(intent);
    }

    public void startOnSoundToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_SOUND);
        context.startService(intent);
    }

    public void startOnAirplaneModeToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_AIRPLANE_MODE);
        context.startService(intent);
    }

    public void startOnWiFiHotspotToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_WIFI_HOTSPOT);
        context.startService(intent);
    }

    public void startOnFlashTorchToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_FLASH_TORCH);
        context.startService(intent);
    }

    public void startOnAutoRotateToggle(Context context) {
        Intent intent = new Intent(context, ContentStateChangeService.class);
        intent.setAction(Actions.ACTION_TOGGLE_AUTO_ROTATE);
        context.startService(intent);
    }

    public void startOnToggle(Context context, ContentType contentType) {
        switch (contentType) {
        case WIFI:
            startOnWiFiToggle(context);
            break;
        case MOBILE_DATA:
            startOnInternetToggle(context);
            break;
        case BLUETOOTH:
            startOnBluetoothToggle(context);
            break;
        case GPS:
            startOnGpsToggle(context);
            break;
        case AUTO_SYNC:
            startOnAutoSyncToggle(context);
            break;
        case SETTINGS:
            startOnSettingsToggle(context);
            break;
        case BRIGHTNESS_MODE:
            startOnBrightnessToggle(context);
            break;
        case RINGER_MODE:
            startOnSoundToggle(context);
            break;
        case AIRPLANE_MODE:
            startOnAirplaneModeToggle(context);
            break;
        case WIFI_HOTSPOT:
            startOnWiFiHotspotToggle(context);
            break;
        case FLASH_TORCH:
            startOnFlashTorchToggle(context);
            break;
        case AUTO_ROTATE:
            startOnAutoRotateToggle(context);
            break;
        default:
        }
    }
}
