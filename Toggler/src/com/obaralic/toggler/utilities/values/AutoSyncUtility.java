
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

import android.content.ContentResolver;
import android.content.Context;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the auto sync.
 */
public class AutoSyncUtility {

    private static final String TAG = LogUtil.getTag(AutoSyncUtility.class);

    /**
     * Constant used  as action for changing auto sync state.
     */
    public static final String ACTION_CHANGE_AUTO_SYNC_STATE = "com.android.sync.SYNC_CONN_STATUS_CHANGED";

    /**
     * Auto sync mode disabled.
     */
    public static final int AUTO_SYNC_DISABLED = 0;
    
    /**
     * Auto sync mode enabled.
     */
    public static final int AUTO_SYNC_ENABLED = 1;

    /**
     * Set auto sync to the given state.
     */
    public static void setEnabled(Context context, boolean isEnabled) {
        LogUtil.d(TAG, "Called setEnabled");

        try {
            ContentResolver.setMasterSyncAutomatically(isEnabled);
        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }

    
    /**
     * Check if auto sync is enabled. 
     */
    public static boolean isEnabled(Context context) {
        LogUtil.d(TAG, "Called isEnabled");
        boolean isEnabled = false;
        try {
            isEnabled = ContentResolver.getMasterSyncAutomatically();

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return isEnabled;
    }

}
