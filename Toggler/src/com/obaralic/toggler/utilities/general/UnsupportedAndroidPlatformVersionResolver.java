
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

package com.obaralic.toggler.utilities.general;

import android.os.Build;

import com.obaralic.toggler.utilities.debug.LogUtil;

public class UnsupportedAndroidPlatformVersionResolver {

    private static final String TAG = LogUtil.getTag(UnsupportedAndroidPlatformVersionResolver.class);

    public static final String ANDROID_4_2 = "4.2";
    public static final String ANDROID_4_2_1 = "4.2.1";
    public static final String ANDROID_3_2 = "3.2";

    public static final int API_16 = 16;
    public static final int API_17 = 17;

    public static boolean isAndroidApiLevel_16() {
        int api = Build.VERSION.SDK_INT;
        LogUtil.d(TAG, "Android is " + api);
        return api == API_16;
    }

    public static boolean isAndroidApiLevel_17() {
        int api = Build.VERSION.SDK_INT;
        LogUtil.d(TAG, "Android is " + api);
        return api == API_16;
    }

    public static boolean isAndroidApiLevel_17_OrHigher() {
        int api = Build.VERSION.SDK_INT;
        LogUtil.d(TAG, "Android is " + api);
        return api >= API_17;
    }
}
