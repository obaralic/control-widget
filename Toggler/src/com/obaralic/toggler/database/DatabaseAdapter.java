
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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.obaralic.toggler.database.DatabaseConstants.InstanceInfo;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * The low-level class for handling the database queries - INSERTs, UPDATEs, DELETEs and SELECTs.
 */
public class DatabaseAdapter {
    
    private static final String TAG = LogUtil.getTag(DatabaseAdapter.class);

    // This class can not be instantiated.
    private DatabaseAdapter() {
    }
    
    /**
     * Symbolic representation for the SQL operation error.
     */
    public static final int SQL_ERROR = -1;
    
    /**
     * Inserting a row into the database table {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param widgetId
     *            The widget's id.
     * @param skinType
     *            The {@link SkinType}.
     * @param widgetSizeType
     *            The {@link WidgetSizeType}.
     * @param content1
     *            The widget's {@link ContentType} for the field No. 1.
     * @param content2
     *            The widget's {@link ContentType} for the field No. 2.
     * @param content3
     *            The widget's {@link ContentType} for the field No. 3.
     * @param content4
     *            The widget's {@link ContentType} for the field No. 4.
     * @param content5
     *            The widget's {@link ContentType} for the field No. 5.
     * @return The _id of the row or {@link DatabaseAdapter#SQL_ERROR} if an error occurred.
     */
    public static long insertInstanceInfo(Context context, int appWidgetId, SkinType skinType,
            WidgetSizeType widgetSizeType, ContentType content1, ContentType content2,
            ContentType content3, ContentType content4, ContentType content5) {

        ContentValues values = new ContentValues();
        values.put(InstanceInfo.COLUMN_WIDGET_ID, appWidgetId);
        values.put(InstanceInfo.COLUMN_SKIN, skinType.getSkinId());
        values.put(InstanceInfo.COLUMN_SIZE, widgetSizeType.getWidgetSizeId());
        values.put(InstanceInfo.COLUMN_CONTENT_1, content1 != null ? content1.getContentId() : null);
        values.put(InstanceInfo.COLUMN_CONTENT_2, content2 != null ? content2.getContentId() : null);
        values.put(InstanceInfo.COLUMN_CONTENT_3, content3 != null ? content3.getContentId() : null);
        values.put(InstanceInfo.COLUMN_CONTENT_4, content4 != null ? content4.getContentId() : null);
        values.put(InstanceInfo.COLUMN_CONTENT_5, content5 != null ? content5.getContentId() : null);

        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.insert(DatabaseConstants.TABLE_INSTANCE_INFO, null, values);
    }    
    
    /**
     * Updates row for the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetId
     *            The widget's id.
     * @param contentType
     *            The updating {@link ContentType}.
     * @param contentPosition
     *            The {@link FieldType}.
     * @return The number of updated rows.
     */
    public static int updateInstanceInfo(Context context, int appWidgetId, ContentType contentType,
            FieldType contentPosition) {

        String contentColumn = resolveInstanceInfoContentPosition(contentPosition);

        ContentValues values = new ContentValues();
        values.put(contentColumn, contentType.getContentId());

        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        String where = InstanceInfo.COLUMN_WIDGET_ID + " =?";
        String[] whereArgs = { "" + appWidgetId };

        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.update(table, values, where, whereArgs);
    }
    
    /**
     * Updates row for the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetId
     *            The widget's id.
     * @param skinType
     *            The widget {@link SkinType}.
     * @return The number of updated rows.
     */
    public static int updateInstanceInfoSkinType(Context context, int appWidgetId, SkinType skinType) {
        ContentValues values = new ContentValues();
        values.put(InstanceInfo.COLUMN_SKIN, skinType.getSkinId());

        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        String where = InstanceInfo.COLUMN_WIDGET_ID + " =?";
        String[] whereArgs = { "" + appWidgetId };

        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.update(table, values, where, whereArgs);
    }  
    
    /**
     * Deletes row from the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param widgetId
     *            The id of removing widget.
     * @return The number of deleted rows.
     */
    public static int deleteInstanceInfo(Context context, int appWidgetId) {        
        String where = InstanceInfo.COLUMN_WIDGET_ID + " =?";
        String[] whereArgs = {"" + appWidgetId}; 
        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.delete(DatabaseConstants.TABLE_INSTANCE_INFO, where, whereArgs);
    }
    
    /**
     * Deletes all invalid row from the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param validAppWidgetIds
     *            The array with valid widget ids.
     * @return The number of deleted rows.
     */
    public static int deleteInvalidInstanceInfo(Context context, int[] validAppWidgetIds) {
        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        int length = validAppWidgetIds.length;
        String[] whereArgs = new String[length];
        StringBuffer where = new StringBuffer();
        where.append(InstanceInfo.COLUMN_WIDGET_ID).append(" NOT IN (");
        for (int index = 0; index < length; index++) {
            whereArgs[index] = "" + validAppWidgetIds[index];
            where.append(" ?");
            if (index + 1 < length) {
                where.append(",");
            }
        }
        where.append(")");

        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.delete(table, where.toString(), whereArgs);
    }
    
    /**
     * Retrieve instance info from the {@link DatabaseConstants#TABLE_INSTANCE_INFO}.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetId
     *            The widget's instance id.
     * @return The {@link Cursor} containing all content states.
     */
    public static Cursor queryInstanceInfo(Context context, int appWidgetId) {
        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        String[] projection = DatabaseConstants.INSTANCE_INFO_PROJECTION;
        String where = InstanceInfo.COLUMN_WIDGET_ID + " =?";
        String[] whereArgs = {"" + appWidgetId};
        
        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.query(table, projection, where, whereArgs, null, null, null);
    }
    
    /**
     * Query all instance info.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @return The {@link Cursor} containing result.
     */
    public static Cursor queryInstanceInfo(Context context) {
        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        String[] projection = DatabaseConstants.INSTANCE_INFO_PROJECTION;
        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.query(table, projection, null, null, null, null, null);
    }
    
    /**
     * Query instance info for the specified widget ids.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetIds
     *            The array with widget ids.
     * @return The {@link Cursor} containing result.
     */
    public static Cursor queryInstanceInfo(Context context, int[] appWidgetIds) {
        String table = DatabaseConstants.TABLE_INSTANCE_INFO;
        String[] projection = DatabaseConstants.INSTANCE_INFO_PROJECTION;
        int length = appWidgetIds.length;
        String[] whereArgs = new String[length];
        StringBuffer where = new StringBuffer();
        where.append(InstanceInfo.COLUMN_WIDGET_ID).append(" IN (");
        for (int index = 0; index < length; index++) {
            whereArgs[index] = "" + appWidgetIds[index];
            where.append(" ?");
            if (index + 1 < length) {
                where.append(",");
            }
        }
        where.append(")");

        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        return sqLiteDatabase.query(table, projection, where.toString(), whereArgs, null, null, null);
    }
    
    /**
     * Utility method used for resolving content column name for the given position.
     */
    private static String resolveInstanceInfoContentPosition(FieldType contentPosition) {
        switch (contentPosition) {
        case ONE:
            return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_1;
        case TWO:
            return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_2;
        case THREE:
            return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_3;
        case FOUR:
            return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_4;
        case FIVE:
            return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_5;
        }

        LogUtil.w(TAG, "Called resolveInstanceInfoContentPosition for invalid content position!");
        return DatabaseConstants.InstanceInfo.COLUMN_CONTENT_5;
    }
        
}
