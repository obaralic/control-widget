
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.general.UnsupportedAndroidPlatformVersionResolver;

/**
 * Utility class used for reading and writing state of the roaming.
 */
@SuppressLint("NewApi")
public class RoamingUtility {

    private static final String TAG = LogUtil.getTag(RoamingUtility.class);

    /**
     * Roaming disabled.
     */
    public static final int ROAMING_DISABLED = 0;

    /**
     * Roaming enabled.
     */
    public static final int ROAMING_ENABLED = 1;

    /**
     * Check if roaming is enabled.
     */
    @SuppressWarnings("deprecation")
    public static boolean isRoamingEnabled(Context context) {
        LogUtil.d(TAG, "Called isRoamingEnabled");

        boolean isEnabled = false;

        try {
            if (!UnsupportedAndroidPlatformVersionResolver.isAndroidApiLevel_17_OrHigher()) {
                isEnabled = (Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.DATA_ROAMING) != 0) ? true : false;
                LogUtil.i(TAG, "Used API is less than 17");

            } else {
                isEnabled = (Settings.Global.getInt(context.getContentResolver(),
                        Settings.Global.DATA_ROAMING) != 0) ? true : false;
                LogUtil.i(TAG, "Used API is 17 or higher");

            }

        } catch (SettingNotFoundException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return isEnabled;
    }

    /**
     * Set roaming to the given state.
     */
    @SuppressWarnings("deprecation")
    public static void setRoamingEnabled(Context context, boolean isRoamingEnabled) {
        LogUtil.d(TAG, "Called setRoamingEnabled");

        boolean isEnabled = isRoamingEnabled(context);

        if ((isRoamingEnabled && isEnabled) || (!isRoamingEnabled && !isEnabled)) {
            return;
        }

        int value = isEnabled ? ROAMING_ENABLED : ROAMING_DISABLED;
        try {
            if (!UnsupportedAndroidPlatformVersionResolver.isAndroidApiLevel_17_OrHigher()) {
                Settings.System.putInt(context.getContentResolver(), Settings.Secure.DATA_ROAMING, value);
                LogUtil.i(TAG, "Used API is less than 17");

            } else {
                Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, value);
                LogUtil.i(TAG, "Used API is 17 or higher");
            }

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }

}
