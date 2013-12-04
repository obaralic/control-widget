
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

public class GpsEnablingInfoPersistance {

    private static class PersistenceKeys {

        private static final String SHOW_GPS_INFO_DIALOG = "SHOW_GPS_INFO_DIALOG";
    }

    private static class PersistenceDefaultValues {

        private static final boolean SHOW_GPS_INFO_DIALOG = true;
    }

    private static GpsEnablingInfoPersistance sInstance;

    private GpsEnablingInfoPersistance() {
    }

    public static GpsEnablingInfoPersistance getInstance() {
        if (sInstance == null) {
            sInstance = new GpsEnablingInfoPersistance();
        }
        return sInstance;
    }

    public boolean shouldShowGpsInfoDialog(Context context) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        return persistenceInterface.getBoolean(context, PersistenceKeys.SHOW_GPS_INFO_DIALOG,
                PersistenceDefaultValues.SHOW_GPS_INFO_DIALOG);
    }

    public void setGpsInfoDialog(Context context, boolean isShowing) {
        PersistenceInterface persistenceInterface = PersistenceFactory.get();
        persistenceInterface.putBoolean(context, PersistenceKeys.SHOW_GPS_INFO_DIALOG, isShowing);
    }

}
