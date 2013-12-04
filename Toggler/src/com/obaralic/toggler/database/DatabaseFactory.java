
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

package com.obaralic.toggler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Singleton factory class used for generating singleton instance to {@link DatabaseHelper}, so that
 * there is no need to worry about leak.
 */
public class DatabaseFactory {

    /**
     * The {@link DatabaseHelper} object that is in charge of all the database-related work.
     */
    private static DatabaseHelper sDatabaseHelper = null;
    
    private DatabaseFactory() {
    }

    /**
     * Retrieves the application's {@link SQLiteDatabase} object that is in charge of all the
     * database-related work. Do not call from the UI Thread, database upgrade takes a lot of time.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @return The mentioned {@link SQLiteDatabase} object.
     */
    static synchronized SQLiteDatabase getDatabaseInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }
        return sDatabaseHelper.getWritableDatabase();
    }

}
