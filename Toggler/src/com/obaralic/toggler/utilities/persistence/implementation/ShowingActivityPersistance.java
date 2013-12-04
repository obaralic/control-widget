
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

public class ShowingActivityPersistance {

    private static class PersistenceKeys {

        private static final String ACTIVITY_CURRENTLY_SHOWING = "ACTIVITY_CURRENTLY_SHOWING";
    }

    private static class PersistenceDefaultValues {

        private static final boolean ACTIVITY_CURRENTLY_SHOWING = false;
    }

    private static ShowingActivityPersistance sInstance;

    private ShowingActivityPersistance() {
    }

    public static ShowingActivityPersistance getInstance() {
        if (sInstance == null) {
            sInstance = new ShowingActivityPersistance();
        }
        return sInstance;
    }

    /**
     * Retrieves information if some activity is currently showing.
     */
    public boolean isActivityShowing(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getBoolean(context, PersistenceKeys.ACTIVITY_CURRENTLY_SHOWING,
                PersistenceDefaultValues.ACTIVITY_CURRENTLY_SHOWING);
    }

    /**
     * Sets the information if some activity is currently showing. *
     */
    public void setActivityShowing(Context context, boolean isShowing) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putBoolean(context, PersistenceKeys.ACTIVITY_CURRENTLY_SHOWING, isShowing);
    }
}
