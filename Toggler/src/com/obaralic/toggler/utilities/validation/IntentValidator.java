
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

package com.obaralic.toggler.utilities.validation;

import android.content.Intent;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class for validating received intent.
 */
public class IntentValidator {

    private static final String TAG = LogUtil.getTag(IntentValidator.class);

    /**
     * Validates if the given {@link Intent} object is different from null and has valid action.
     * 
     * @param intent
     *            The Intent object
     * @return The outcome of validation
     */
    public boolean isOkay(Intent intent) {
        if (intent == null) {
            LogUtil.e(TAG, "Null intent!");
            return false;
        }
        String action = intent.getAction();
        if (action == null) {
            LogUtil.e(TAG, "Null intent action!");
            return false;
        }
        return true;
    }
}
