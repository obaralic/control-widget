
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

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the bluetooth.
 */
public class BluetoothUtility {

    private static final String TAG = LogUtil.getTag(BluetoothUtility.class);

    /**
     * Constant used as action for changing bluetooth state.
     */
    public static final String ACTION_CHANGE_BLUETOOTH_STATE = "com.obaralic.toggler.action.ACTION_CHANGE_BLUETOOTH_STATE";

    /**
     * Constant used for storing bluetooth state.
     */
    public static final String EXTRA_CHANGE_BLUETOOTH_STATE = "com.obaralic.toggler.extra.EXTRA_CHANGE_BLUETOOTH_STATE";

    /**
     * Default bluetooth state.
     */
    public static final boolean DEFAULT_CHANGE_BLUETOOTH_STATE = false;

    /**
     * Bluetooth disabled.
     */
    public static final int BLUETOOTH_DISABLED = 0;

    /**
     * Bluetooth enabled.
     */
    public static final int BLUETOOTH_ENABLED = 1;

    /**
     * Bluetooth disabling.
     */
    public static final int BLUETOOTH_DISABLING = 2;

    /**
     * Bluetooth enabling.
     */
    public static final int BLUETOOTH_ENABLING = 3;

    /**
     * Check if bluetooth is enabled.
     */
    public static boolean isEnabled(Context context) {
        LogUtil.d(TAG, "Called isEnabled");
        boolean isEnabled = false;
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            isEnabled = bluetoothAdapter.isEnabled();

        } catch (NullPointerException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
        return isEnabled;
    }

    /**
     * Set bluetooth to the given state.
     */
    public static void setEnabled(Context context, boolean isEnabled) {
        LogUtil.d(TAG, "Called setEnabled");
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (isEnabled) {
                bluetoothAdapter.enable();

            } else {
                bluetoothAdapter.disable();
            }

        } catch (NullPointerException exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }
    }

    /**
     * Set bluetooth to the given state using reflection.
     */
    public static final void setEnabledByReflection(Context context, boolean isEnabled) {

        try {

            Object mDevice = context.getSystemService("bluetooth");

            if (mDevice != null) {
                Class<?> c = mDevice.getClass();
                String methodName = isEnabled ? "enable" : "disable";
                Method method = c.getMethod(methodName);
                method.setAccessible(true);
                method.invoke(mDevice);
            }

        } catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
        }

    }

}
