
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

import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.utilities.persistence.PersistenceFactory;
import com.obaralic.toggler.utilities.persistence.PersistenceInterface;

/**
 * Persistence class used for storing information if there is any instance of the widget present on
 * the home screen. Information is preserved by using shared preferences.
 */
public class InstantiationPersistence {

    /**
     * The persistence key used for storing value.
     */
    private static final String IS_WIDGET_INSTANTIATED = "IS_WIDGET_INSTANTIATED";
    
    /**
     * The persistence default value.
     */
    private static final boolean DEFAULT_VALUE = false;
    
    private static InstantiationPersistence sInstance = null;

    private InstantiationPersistence() {
    }

    /**
     * Returns singleton instance of this class used for accessing persistence value.
     * 
     * @return The singleton instance.
     */
    public static InstantiationPersistence getInstance() {
        if (sInstance == null) {
            sInstance = new InstantiationPersistence();
        }
        return sInstance;
    }

    /**
     * Determines if there is at least one widget instance present on the home screen.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @return The outcome of the operation.
     */
    public boolean isAnyWidgetPresent(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        boolean haveInstance = persistenceInterface.getBoolean(context, IS_WIDGET_INSTANTIATED, DEFAULT_VALUE);
        if (haveInstance == DEFAULT_VALUE) {
            InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
            haveInstance = dao.getInstanceInfo(context).size() != 0 ? true : false;
            persistenceInterface.putBoolean(context, IS_WIDGET_INSTANTIATED, haveInstance);
        }        
        return haveInstance;
    }

    /**
     * Performs storing of the given value into shared preferences.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param isAtLeastOneWidgetPresent
     *            The value to be stored.
     */
    public void setAtLeastOneWidgetPresent(Context context, boolean isAtLeastOneWidgetPresent) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putBoolean(context, IS_WIDGET_INSTANTIATED, isAtLeastOneWidgetPresent);
    }
    
}
