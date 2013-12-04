
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

package com.obaralic.toggler.utilities.debug;

import android.util.Log;

/**
 * Android log wrapper class.
 */
public abstract class AbstractLogUtil {

    private static final boolean DEBUG = false;

    private static final boolean INFO = false;

    private static final boolean WARNING = true;

    private static final boolean ERROR = true;

    public static void d(String TAG, String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void d(String TAG, String message, Throwable t) {
        if (DEBUG) {
            Log.d(TAG, message, t);
        }
    }

    public static void i(String TAG, String message) {
        if (INFO) {
            Log.i(TAG, message);
        }
    }

    public static void w(String TAG, String message) {
        if (WARNING) {
            Log.w(TAG, message);
        }
    }

    public static void w(String TAG, String message, Throwable t) {
        if (WARNING) {
            Log.w(TAG, message, t);
        }
    }

    public static void e(String TAG, String message) {
        if (ERROR) {
            Log.e(TAG, message);
        }
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

}
