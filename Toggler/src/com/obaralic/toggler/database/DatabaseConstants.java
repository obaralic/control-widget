
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

import android.provider.BaseColumns;

/**
 * All the important constants related to the application's database in one place.
 */
public final class DatabaseConstants {

    // This class can not be instantiated.
    private DatabaseConstants() {
    }

    /**
     * The name of the application's database file.
     */
    public static final String DATABASE_NAME = "system_control_toggle.db";

    /**
     * The current version of the database. If the user updates the application and this constant is
     * changed (in relation to the version he/she previously had)
     * {@link DatabaseHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)} will be
     * called.
     */
    public static final int DATABASE_VERSION = 1;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    /**
     * Query for checking if database table {@link DatabaseConstants#TABLE_INSTANCE_INFO} exists.
     */
    public static final String QUERY_IF_TABLE_INSTANCE_INFO_EXISTS = "SELECT count(*) FROM sqlite_master "
            + "WHERE type=\'table\' AND name=\'" + DatabaseConstants.TABLE_INSTANCE_INFO + "\'";

    /**
     * The name of the table used for storing info about widget instance.
     */
    public static final String TABLE_INSTANCE_INFO = "instance_info";

    /**
     * Defines all column names used in the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     */
    public static final class InstanceInfo implements BaseColumns {

        // This class cannot be instantiated
        private InstanceInfo() {
        }
        
        /**
         * Table column used for defining widget id.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_WIDGET_ID = "widget_id";
        
        /**
         * Table column used for defining widget skin.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_SKIN = "widget_skin";
        
        /**
         * Table column used for defining widget size.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_SIZE = "widget_size";

        /**
         * Table column used for referencing row in the {@link ContentInfo}.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_CONTENT_1 = "widget_content_1";

        /**
         * Table column used for referencing row in the {@link ContentInfo}.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_CONTENT_2 = "widget_content_2";

        /**
         * Table column used for referencing row in the {@link ContentInfo}.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_CONTENT_3 = "widget_content_3";

        /**
         * Table column used for referencing row in the {@link ContentInfo}.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_CONTENT_4 = "widget_content_4";

        /**
         * Table column used for referencing row in the {@link ContentInfo}.
         * <p>
         * Content type: INTEGER
         * </p>
         */
        public static final String COLUMN_CONTENT_5 = "widget_content_5";
    }    

    /**
     * The database projection used for accessing table with widget instances.
     */
    public static final String[] INSTANCE_INFO_PROJECTION = new String[] {
        InstanceInfo._ID,
        InstanceInfo.COLUMN_WIDGET_ID,
        InstanceInfo.COLUMN_SKIN,
        InstanceInfo.COLUMN_SIZE,
        InstanceInfo.COLUMN_CONTENT_1,
        InstanceInfo.COLUMN_CONTENT_2,
        InstanceInfo.COLUMN_CONTENT_3,
        InstanceInfo.COLUMN_CONTENT_4,
        InstanceInfo.COLUMN_CONTENT_5
    };
 
    /**
     * Query for creating database table {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     */
    public static final String CREATE_TABLE_INSTANCE_INFO =
            "CREATE TABLE " + TABLE_INSTANCE_INFO + " (" 
                + InstanceInfo._ID + " INTEGER PRIMARY KEY, "
                + InstanceInfo.COLUMN_WIDGET_ID + " INTEGER NOT NULL, "
                + InstanceInfo.COLUMN_SKIN + " INTEGER NOT NULL, "
                + InstanceInfo.COLUMN_SIZE + " INTEGER NOT NULL, "
                + InstanceInfo.COLUMN_CONTENT_1 + " INTEGER, "
                + InstanceInfo.COLUMN_CONTENT_2 + " INTEGER, "
                + InstanceInfo.COLUMN_CONTENT_3 + " INTEGER, "
                + InstanceInfo.COLUMN_CONTENT_4 + " INTEGER, "
                + InstanceInfo.COLUMN_CONTENT_5 + " INTEGER "
                + ")";

    /**
     * Query for dropping database table {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     */
    public static final String DROP_TABLE_INSTANCE_INFO = DROP_TABLE + TABLE_INSTANCE_INFO + ";";
     
}
