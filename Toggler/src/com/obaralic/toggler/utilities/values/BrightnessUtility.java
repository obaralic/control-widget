
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

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing display brightness.
 */
public class BrightnessUtility {

    private static final String TAG = LogUtil.getTag(BrightnessUtility.class);

    /**
     * Constant used  as action for changing brightness state.
     */
    public static final String ACTION_CHANGE_BRIGHTNESS_STATE = "com.obaralic.toggler.action.ACTION_CHANGE_BRIGHTNESS_STATE";
    
    /**
     * Constant used for storing brightness state.
     */
    public static final String EXTRA_CHANGE_BRIGHTNESS_STATE = "com.obaralic.toggler.extra.EXTRA_CHANGE_BRIGHTNESS_STATE";
    
    public static final int BRIGHTNESS_LEVEL_LOW = 30;
    public static final int BRIGHTNESS_LEVEL_MEDIUM = 102;
    public static final int BRIGHTNESS_LEVEL_HIGH = 255;
    public static final int MAX_BRIGHTNESS_LEVLE = 255;
    public static final int REFRESH_SCREEN_DELAY = 500;
    public static final int BRIGHTNESS_AUTO = 0;
    public static final int BRIGHTNESS_LOW = 1;
    public static final int BRIGHTNESS_MEDIUM = 2;
    public static final int BRIGHTNESS_HIGH = 3;    
    
    public static final int NUMBER_OF_BRIGHTNESS_MODES = 4;
    
    /**
     * Read brightness mode.
     */
    public static int getBrightnessMode(Context context) {
        LogUtil.d(TAG, "Caled getBrightnessMode");

        int brightnessMode = BRIGHTNESS_MEDIUM;

        try {
            int brightnessLevel = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            int isAutoBrightnesMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);

            if (brightnessLevel <= BRIGHTNESS_LEVEL_LOW) {
                brightnessMode = BRIGHTNESS_LOW;

            } else if (brightnessLevel > BRIGHTNESS_LEVEL_LOW && brightnessLevel <= BRIGHTNESS_LEVEL_MEDIUM) {
                brightnessMode = BRIGHTNESS_MEDIUM;

            } else if (brightnessLevel > BRIGHTNESS_LEVEL_MEDIUM && brightnessLevel <= BRIGHTNESS_LEVEL_HIGH) {
                brightnessMode = BRIGHTNESS_HIGH;
            }

            brightnessMode = (isAutoBrightnesMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) ? BRIGHTNESS_AUTO : brightnessMode;

        } catch (SettingNotFoundException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return brightnessMode;
    }

    /**
     * Read brightness level.
     */
    public static float getBrightnessLevel(Context context) {
        LogUtil.d(TAG, "Caled getBrightnessLevel");
        
        int brightnessMode = getBrightnessMode(context);
        int brightnessLevel;

        switch (brightnessMode) {
        case BRIGHTNESS_AUTO:
            brightnessLevel = BRIGHTNESS_LEVEL_MEDIUM;
            break;
        case BRIGHTNESS_LOW:
            brightnessLevel = BRIGHTNESS_LEVEL_LOW;
            break;
        case BRIGHTNESS_MEDIUM:
            brightnessLevel = BRIGHTNESS_LEVEL_MEDIUM;
            break;
        case BRIGHTNESS_HIGH:
            brightnessLevel = BRIGHTNESS_LEVEL_HIGH;
            break;
        default:
            brightnessLevel = BRIGHTNESS_LEVEL_HIGH;
            break;
        }

        return (float) brightnessLevel / MAX_BRIGHTNESS_LEVLE;
    }

    /**
     * Set brightness to the given mode.
     */
    public static void setBrightnessMode(Activity context, int brightnessMode) {
        LogUtil.d(TAG, "Caled setBrightnessMode");

        float backLightValuePercentage = BrightnessUtility.getBrightnessLevel(context);
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();
        layoutParams.screenBrightness = backLightValuePercentage;
        context.getWindow().setAttributes(layoutParams);
        
        try {            
            
            if (brightnessMode == BRIGHTNESS_LOW) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS_LEVEL_LOW);
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            } else if (brightnessMode == BRIGHTNESS_MEDIUM) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS_LEVEL_MEDIUM);
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            } else if (brightnessMode == BRIGHTNESS_HIGH) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS_LEVEL_HIGH);
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            } else if (brightnessMode == BRIGHTNESS_AUTO) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, BRIGHTNESS_LEVEL_MEDIUM);
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

            }

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

    }
    
    public static String valueOf(int state) {
        String value = null;
        switch (state) {
        case BRIGHTNESS_AUTO:
            value = "BRIGHTNESS_AUTO";
            break;

        case BRIGHTNESS_HIGH:
            value = "BRIGHTNESS_HIGH";
            break;
            
        case BRIGHTNESS_MEDIUM:
            value = "BRIGHTNESS_MEDIUM";
            break;
            
        case BRIGHTNESS_LOW:
            value = "BRIGHTNESS_LOW";
            break;
            
        default:
            value = "UNKNOWN";
        }
        
        return value;
    }

}
