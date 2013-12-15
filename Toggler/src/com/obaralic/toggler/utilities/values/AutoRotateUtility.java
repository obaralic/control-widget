
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
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the auto rotate.
 */
public class AutoRotateUtility {

    private static final String TAG = LogUtil.getTag(AutoRotateUtility.class);
    
    /**
     * Constant used  as action for changing auto rotate state.
     */
    public static final String ACTION_CHANGE_AUTO_ROTATE_STATE = "com.obaralic.toggler.action.ACTION_CHANGE_AUTO_ROTATE_STATE";

    /**
     * Auto rotate mode disabled.
     */
    public static final int AUTO_ROTATE_DISABLED = 0;
    
    /**
     * Auto rotate mode enabled.
     */
    public static final int AUTO_ROTATE_ENABLED = 1;
    
    /**
     * Check if auto rotate is enabled. 
     */
    public static boolean isAutoRotateEnabled(Context context) {
        boolean isEnabled = false;
        
        try {
            isEnabled =  (Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) != 0) ? true : false;
            
        } catch (SettingNotFoundException e) {
            LogUtil.w(TAG, LogUtil.getStackTraceString(e));
        }
        
        return isEnabled;
    }

    /**
     * Set auto rotate to the given state.
     */
    public static void setAutoRotateEnabled(Context context, boolean isEnabled) {
        
        boolean isAutoRotateEnabled = isAutoRotateEnabled(context);

        if ((isAutoRotateEnabled && isEnabled) || (!isAutoRotateEnabled && !isEnabled)) {
            return;
        }
        int value = isEnabled ? 1 : 0;
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, value);
                        
        } catch (Exception e) {
            LogUtil.w(TAG, LogUtil.getStackTraceString(e));
        }
        
    }

}
