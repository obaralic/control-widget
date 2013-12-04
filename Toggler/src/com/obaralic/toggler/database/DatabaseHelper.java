
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
import android.database.sqlite.SQLiteOpenHelper;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * A subclass of {@link SQLiteOpenHelper} that defines the behavior of the application's database
 * related to its creation and upgrading.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = LogUtil.getTag(DatabaseHelper.class);

    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.d(TAG, "Creating tables for database version " + DatabaseConstants.DATABASE_VERSION);
        db.execSQL(DatabaseConstants.CREATE_TABLE_INSTANCE_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d(TAG, "Database upgrade from v." + oldVersion + " to v." + newVersion + ".");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        LogUtil.d(TAG, "Database connection opened. DatabaseHelper.onOpen() is called.");
    }
}
