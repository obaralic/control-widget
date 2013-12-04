
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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the GPS.
 */
public class GpsUtility {

    private static final String TAG = LogUtil.getTag(GpsUtility.class);

    /**
     * Constant used  as action for changing GPS state.
     */
    public static final String ACTION_CHANGE_GPS_STATE = "android.location.PROVIDERS_CHANGED";

    /**
     * GPS disabled.
     */
    public static final int GPS_DISABLED = 0;
    
    /**
     * GPS enabled.
     */
    public static final int GPS_ENABLED = 1;
    
    /**
     * Check if GPS is enabled. 
     */
    public static boolean isGpsEnabled(Context context) {

        boolean isEnabled = false;
        try {
            LocationManager locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return isEnabled;
    }

    /**
     * Try to set GPS to the given state.
     */
    public static boolean setGpsEnabled(Context context, boolean isGpsEnabled) {

        if (!canToggleGPS(context)) {
            return false;
        }

        if (isGpsEnabled) {
            String provider = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }

        } else {
            String provider = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (provider.contains("gps")) { // if gps is enabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                context.sendBroadcast(poke);
            }

        }

        return true;

    }

    private static boolean canToggleGPS(Context context) {
        PackageManager pacman = context.getPackageManager();
        PackageInfo pacInfo = null;

        try {
            pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
        } catch (NameNotFoundException e) {
        }

        if (pacInfo != null) {
            for (ActivityInfo actInfo : pacInfo.receivers) {

                // test if receiver is exported. if so, we can toggle GPS.
                if (actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider")
                        && actInfo.exported) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Launches locations settings screen.
     */
    public static void launchGpsSettingsPage(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
