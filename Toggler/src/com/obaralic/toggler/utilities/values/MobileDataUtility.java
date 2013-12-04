
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the mobile data.
 */
public class MobileDataUtility {

    private static final String TAG = LogUtil.getTag(MobileDataUtility.class);

    /**
     * Mobile data traffic disabled value.
     */
    public static final int MOBILE_DATA_DISABLED = 0;

    /**
     * Mobile data traffic enabled value.
     */
    public static final int MOBILE_DATA_ENABLED = 1;

    /**
     * Performs setting system value for mobile data traffic.
     * 
     * @param context
     *            The Surrounding {@link Context}.
     * @param isEnabled
     *            Set mobile data traffic to this value.
     */
    public static void setEnabled(Context context, boolean isEnabled) {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            Method method = ConnectivityManager.class
                    .getDeclaredMethod("setMobileDataEnabled", boolean.class);

            method.setAccessible(true);
            method.invoke(connectivityManager, isEnabled);

        } catch (NoSuchMethodException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));

        } catch (IllegalArgumentException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));

        } catch (IllegalAccessException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));

        } catch (InvocationTargetException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }

    /**
     * Gets the current system value for the mobile data traffic.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @return Mobile data traffic state.
     */
    public static boolean isEnabled(Context context) {

        boolean isEnabled = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            Class<?> cls = Class.forName(connectivityManager.getClass().getName());
            Method m = cls.getDeclaredMethod("getMobileDataEnabled");
            m.setAccessible(true);
            isEnabled = ((Boolean) m.invoke(connectivityManager)).booleanValue();

        } catch (NullPointerException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

        return isEnabled;
    }

}
