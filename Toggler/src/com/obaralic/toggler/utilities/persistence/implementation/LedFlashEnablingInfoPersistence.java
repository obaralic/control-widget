
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

package com.obaralic.toggler.utilities.persistence.implementation;

import android.content.Context;

import com.obaralic.toggler.utilities.persistence.PersistenceFactory;
import com.obaralic.toggler.utilities.persistence.PersistenceInterface;

public class LedFlashEnablingInfoPersistence {

    private static class PersistenceKeys {

        private static final String USE_LED_FLASH_ISSUE_FIX = "USE_LED_FLASH_ISSUE_FIX";
    }

    private static class PersistenceDefaultValues {

        private static final boolean USE_LED_FLASH_ISSUE_FIX = false;
    }

    private static LedFlashEnablingInfoPersistence sInstance;

    private LedFlashEnablingInfoPersistence() {
    }

    public static LedFlashEnablingInfoPersistence getInstance() {
        if (sInstance == null) {
            sInstance = new LedFlashEnablingInfoPersistence();
        }
        return sInstance;
    }

    public boolean isLedFlashFixInUse(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getBoolean(context, PersistenceKeys.USE_LED_FLASH_ISSUE_FIX,
                PersistenceDefaultValues.USE_LED_FLASH_ISSUE_FIX);
    }

    public void setLedFlashFixInUse(Context context, boolean inUse) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putBoolean(context, PersistenceKeys.USE_LED_FLASH_ISSUE_FIX, inUse);
    }

}

    