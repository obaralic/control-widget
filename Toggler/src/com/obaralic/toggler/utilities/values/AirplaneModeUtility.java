
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
import android.content.Intent;
import android.provider.Settings;

import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.general.UnsupportedAndroidPlatformVersionResolver;

/**
 * Utility class used for reading and writing state of the airplane mode.
 */
@SuppressLint("NewApi")
public class AirplaneModeUtility {

	private static final String TAG = LogUtil.getTag(AirplaneModeUtility.class);

	/**
	 * Constant used for storing airplane mode state.
	 */
	public static final String EXTRA_AIRPLANE_MODE = "com.obaralic.toggler.extra.EXTRA_AIRPLANE_MODE";
	
	/**
	 * Airplane mode disabled.
	 */
	public static final int AIRPLANE_MODE_DISABLED = 0;
	
	/**
     * Airplane mode enabled.
     */
	public static final int AIRPLANE_MODE_ENABLED = 1;

	
	/**
	 * Check if airplane mode is enabled. 
	 */
	@SuppressWarnings("deprecation")
	public static boolean isAirplaneModeOn(Context context) {
		LogUtil.d(TAG, "Called isAirplaneModeOn");

		boolean isEnabled = false;
		try {
			if (!UnsupportedAndroidPlatformVersionResolver.isAndroidApiLevel_17_OrHigher()) {
				isEnabled = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
				LogUtil.i(TAG, "Used API is less than 17");
				
			} else {
				LogUtil.i(TAG, "Used API is 17 or higher");
				isEnabled = Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
			}

		} catch (Exception exception) {
			LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
		}

		return isEnabled;
	}

	
	/**
	 * Set airplane mode to the given state.
	 */
	@SuppressWarnings("deprecation")
	public static void setAirplaneMode(Context context, boolean isEnabled) {
		LogUtil.d(TAG, "Called setAirplaneMode");

		boolean isAirplaneModeOn = isAirplaneModeOn(context);

		if ((isAirplaneModeOn && isEnabled) || (!isAirplaneModeOn && !isEnabled)) {
			return;
		}

		int value = isEnabled ? 1 : 0;
		try {
			if (!UnsupportedAndroidPlatformVersionResolver.isAndroidApiLevel_17_OrHigher()) {
				Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, value);
				LogUtil.i(TAG, "Used API is less than 17");
				
			} else {
				Settings.Global.putInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, value);
				LogUtil.i(TAG, "Used API is 17 or higher");			
			}

			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra(EXTRA_AIRPLANE_MODE, isEnabled);
			context.sendBroadcast(intent);

		} catch (Exception exception) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(exception));
		}
	}

}
