
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

public class ContentStatesPersistence {

    private static class PersistenceKeys {

        private static final String CONTENT_STATES = "CONTENT_STATES";
        private static final String CONTENT_STATES_LENGTH = "CONTENT_STATES_LENGTH";
    }

    private static class PersistenceDefaultValues {

        private static final int[] CONTENT_STATES = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    }

    private static ContentStatesPersistence sInstance;

    private ContentStatesPersistence() {
    }

    public static ContentStatesPersistence getInstance() {
        if (sInstance == null) {
            sInstance = new ContentStatesPersistence();
        }
        return sInstance;
    }

    public int[] getContentStates(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getIntArray(context, PersistenceKeys.CONTENT_STATES,
                PersistenceKeys.CONTENT_STATES_LENGTH, PersistenceDefaultValues.CONTENT_STATES);
    }

    public void setContentStates(Context context, int[] contentStates) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putIntArray(context, PersistenceKeys.CONTENT_STATES,
                PersistenceKeys.CONTENT_STATES_LENGTH, contentStates);
    }

}
