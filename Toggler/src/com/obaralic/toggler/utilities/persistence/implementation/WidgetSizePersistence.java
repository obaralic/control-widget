
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

import com.obaralic.toggler.database.dao.enums.WidgetSizeType;
import com.obaralic.toggler.service.WidgetUiService;
import com.obaralic.toggler.utilities.persistence.PersistenceFactory;
import com.obaralic.toggler.utilities.persistence.PersistenceInterface;

/**
 * Used by {@link WidgetUiService} to store size of the widget that was used to generate on click
 * event.
 */
public class WidgetSizePersistence {

    private static class PersistenceKeys {

        private static final String SELECTED_WIDGET_SIZE = "SELECTED_WIDGET_SIZE";
    }

    private static class PersistenceDefaultValues {

        private static final int SELECTED_WIDGET_SIZE = WidgetSizeType.ONE.getWidgetSizeId();
    }

    private static WidgetSizePersistence sInstance;

    private WidgetSizePersistence() {
    }

    public static WidgetSizePersistence getInstance() {
        if (sInstance == null) {
            sInstance = new WidgetSizePersistence();
        }
        return sInstance;
    }

    /**
     * Retrieves the currently selected widget size.
     */
    public int getWidgetSize(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getInt(context, PersistenceKeys.SELECTED_WIDGET_SIZE, PersistenceDefaultValues.SELECTED_WIDGET_SIZE);
    }

    /**
     * Sets the selected widget size to be one of the values in {@link WidgetSizeType} constants.
     */
    public void setWidgetSize(Context context, int widgetSize) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putInt(context, PersistenceKeys.SELECTED_WIDGET_SIZE, widgetSize);
    }

}
