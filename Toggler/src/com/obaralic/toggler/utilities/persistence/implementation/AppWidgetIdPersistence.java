
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
/**
 * Used to store information about widget id that was selected.
 * This is hopefully temporary solution if idea with bean parcelable works up.
 */
public class AppWidgetIdPersistence {

    private static class PersistenceKeys {
        private static final String SELECTED_APP_WIDGET_ID = "SELECTED_APP_WIDGET_ID";
    }

    private static class PersistenceDefaultValues {
        private static final int SELECTED_APP_WIDGET_ID = 0;
    }

    private static AppWidgetIdPersistence sInstance;

    private AppWidgetIdPersistence() {
    }

    public static AppWidgetIdPersistence getInstance() {
        if (sInstance == null) {
            sInstance = new AppWidgetIdPersistence();
        }
        return sInstance;
    }

    /**
     * Retrieves the currently selected widget id.
     */
    public int getAppWidgetId(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getInt(context, PersistenceKeys.SELECTED_APP_WIDGET_ID,
                PersistenceDefaultValues.SELECTED_APP_WIDGET_ID);
    }

    /**
     * Sets the selected widget id.
     */
    public void setAppWidgetId(Context context, int widgetSize) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putInt(context, PersistenceKeys.SELECTED_APP_WIDGET_ID, widgetSize);
    }
    
}

	