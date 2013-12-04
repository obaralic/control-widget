
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

package com.obaralic.toggler.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.Window;

import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.values.BrightnessUtility;

/**
 * The dummy activity used as hack only for purpose of refreshing screen after changing its
 * brightness.
 */
public class BrightnessControlActivity extends BaseActivity {

    private static final String TAG = LogUtil.getTag(BrightnessControlActivity.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int brightnessMode = extras
                    .getInt(BrightnessUtility.EXTRA_CHANGE_BRIGHTNESS_STATE, -1);

            if (brightnessMode != -1) {
                LogUtil.d(TAG, "Brightness mode: " + brightnessMode);

                BrightnessUtility.setBrightnessMode(this, brightnessMode);

                scheduleDisplayRefreshTimer();
            } else {
                LogUtil.w(TAG, "Invalide brightness mode!");
                finish();
            }

        } else {
            LogUtil.e(TAG, "Extras are NULL!");
            finish();
        }

    }

    /**
     * Creates and schedules timer for refreshing screen after changig brightness.
     */
    private void scheduleDisplayRefreshTimer() {
        Timer displayRefreshTimer = new Timer();
        displayRefreshTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                BrightnessControlActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        }, BrightnessUtility.REFRESH_SCREEN_DELAY);
    }

}
