
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

package com.obaralic.toggler.utilities.values;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the WiFi.
 */
public class WiFiUtility {

    private static final String TAG = LogUtil.getTag(WiFiUtility.class);

    /**
     * WiFi state disabled.
     */
    public static final int WIFI_DISABLED = 0;

    /**
     * WiFi state enabled.
     */
    public static final int WIFI_ENABLED = 1;

    /**
     * WiFi state disabling.
     */
    public static final int WIFI_DISABLING = 2;

    /**
     * WiFi state enabling.
     */
    public static final int WIFI_ENABLING = 3;

    /**
     * Check if WiFi is enabled.
     */
    public static boolean isEnabled(Context context) {
        LogUtil.d(TAG, "Called isEnabled");
        boolean isEnabled = false;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            isEnabled = wifiManager.isWifiEnabled();

        } catch (NullPointerException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
        return isEnabled;
    }

    /**
     * Set WiFi to the given state.
     */
    public static void setEnabled(Context context, boolean isEnabled) {
        LogUtil.d(TAG, "Called setEnabled");
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(isEnabled);

        } catch (NullPointerException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }

}
