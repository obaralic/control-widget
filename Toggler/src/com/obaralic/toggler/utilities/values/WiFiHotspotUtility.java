
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

import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the WiFi hotspot.
 */
public class WiFiHotspotUtility {

    private static final String TAG = LogUtil.getTag(WiFiHotspotUtility.class);

    /**
     * Constant used as action for changing WiFi hotspot state.
     */
    public static final String ACTION_CHANGE_WIFI_HOTSPOT_STATE = "android.net.wifi.WIFI_AP_STATE_CHANGED";

    /**
     * WiFi hotspot disabled.
     */
    public static final int WIFI_HOTSPOT_DISABLED = 0;

    /**
     * WiFi hotspot enabled.
     */
    public static final int WIFI_HOTSPOT_ENABLED = 1;

    private static final int WIFI_AP_STATE_UNKNOWN = -1;
    private static final int WIFI_AP_STATE_DISABLING = 0;
    private static final int WIFI_AP_STATE_DISABLED = 1;
    private static final int WIFI_AP_STATE_ENABLING = 2;
    private static final int WIFI_AP_STATE_ENABLED = 3;
    private static final int WIFI_AP_STATE_FAILED = 4;

    /**
     * Read WiFi hotspot state.
     */
    private static int getWifiAPState(Context context) {
        int state = WIFI_AP_STATE_UNKNOWN;
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getMethod("getWifiApState");
            state = (Integer) method.invoke(manager);

            // Fix for Android 4
            if (state > 10) {
                state = state - 10;
                LogUtil.d(TAG, "State " + state);
            }

        } catch (Exception e) {
        }

        return state;
    }

    /**
     * Check if WiFi hotspot is enabled.
     */
    public static final boolean isEnabled(Context context) {
        return getWifiAPState(context) == WIFI_AP_STATE_ENABLED;
    }

    /**
     * Set WiFi hotspot to the given state.
     */
    public static int setWifiApEnabled(Context context, boolean enabled) {

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (enabled && manager.getConnectionInfo() != null) {
            manager.setWifiEnabled(false);
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
        }

        try {
            manager.setWifiEnabled(false);
            Method method = manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,
                    boolean.class);
            method.invoke(manager, null, enabled); // true

        } catch (Exception e) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(e));
        }

        if (!enabled) {
            int loopMax = 10;
            int wiFiApState = getWifiAPState(context);
            while (loopMax > 0 && (wiFiApState == WIFI_AP_STATE_DISABLING 
                    || wiFiApState == WIFI_AP_STATE_ENABLED 
                    || wiFiApState == WIFI_AP_STATE_FAILED)) {
                
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {
                }
            }
            manager.setWifiEnabled(true);
        } else if (enabled) {
            int loopMax = 10;
            int wiFiApState = getWifiAPState(context);
            while (loopMax > 0 && (wiFiApState == WIFI_AP_STATE_ENABLING 
                    || wiFiApState == WIFI_AP_STATE_DISABLED 
                    || wiFiApState == WIFI_AP_STATE_FAILED)) {
                
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {
                }
            }
        }

        return getWifiAPState(context);
    }
}
