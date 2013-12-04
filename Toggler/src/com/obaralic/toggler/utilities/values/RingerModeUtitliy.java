
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
import android.media.AudioManager;

import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.general.UnsupportedAndroidPlatformVersionResolver;

/**
 * Utility class used for reading and writing state of the ringer mode.
 */
public class RingerModeUtitliy {

    private static final String TAG = LogUtil.getTag(RingerModeUtitliy.class);

    /**
     * The number of the supported ringer modes.
     */
    public static final int NUMBER_OF_RINGER_MODES = 3;

    /**
     * Ringer mode silent.
     */
    public static final int RING_MODE_SILENT = 0;

    /**
     * Ringer mode vibrate.
     */
    public static final int RING_MODE_VIBRATE = 1;

    /**
     * Ringer mode normal.
     */
    public static final int RING_MODE_NORMAL = 2;

    /**
     * Read ringer mode state.
     */
    public static int getRingerMode(Context context) {

        int ringerMode = RING_MODE_NORMAL;

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int systemRingerMode = audioManager.getRingerMode();

            if (systemRingerMode == AudioManager.RINGER_MODE_SILENT) {
                ringerMode = RING_MODE_SILENT;

            } else if (systemRingerMode == AudioManager.RINGER_MODE_VIBRATE) {
                ringerMode = RING_MODE_VIBRATE;

            } else if (systemRingerMode == AudioManager.RINGER_MODE_NORMAL) {
                ringerMode = RING_MODE_NORMAL;
            }

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return ringerMode;
    }

    /**
     * Set ringer mode to the given state.
     */
    public static void setRingerMode(Context context, int ringerMode) {

        if (UnsupportedAndroidPlatformVersionResolver.isAndroidApiLevel_16()
                && ringerMode == RING_MODE_NORMAL) {
            ringerMode = (ringerMode + 1) % RingerModeUtitliy.NUMBER_OF_RINGER_MODES;
        }

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if (ringerMode == RING_MODE_VIBRATE) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

            } else if (ringerMode == RING_MODE_SILENT) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            } else if (ringerMode == RING_MODE_NORMAL) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }
    
    public static String valueOf(int state) {
        String value = null;
        switch (state) {
        case RING_MODE_NORMAL:
            value = "RING_MODE_NORMAL";
            break;

        case RING_MODE_SILENT:
            value = "RING_MODE_SILENT";
            break;
            
        case RING_MODE_VIBRATE:
            value = "RING_MODE_VIBRATE";
            break;
            
        default:
            value = "UNKNOWN";
        }
        
        return value;
    }

}
