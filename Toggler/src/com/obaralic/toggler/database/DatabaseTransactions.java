
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

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.provider.types.WidgetProviderForSize1x1;
import com.obaralic.toggler.provider.types.WidgetProviderForSize4x1;

/**
 * Database transactions class that encapsulates two or more queries, inserts or updates into one atomic
 * transaction.
 */
public class DatabaseTransactions {

    /**
     * Insert newly created widget 1x1 info and remove all unused widget info.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetId
     *            The widget id.
     */
    public static void insertAndRefreshInstance1x1Info(Context context, int appWidgetId) {
        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        sqLiteDatabase.beginTransaction();
        try {

            // Insert instance info into database.
            InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
            InstanceInfoBean defaultInstance = InstanceInfoBean.getDefault1x1Instance();
            defaultInstance.mAppWidgetId = appWidgetId;
            dao.createInstanceInfo(context, defaultInstance);

            // Delete removed widget instances.
            dao.removeInvalidInstanceInfo(context, getValidAppWidgetIds(context));

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * Insert newly created widget 5x1 info and remove all unused widget info.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param appWidgetId
     *            The widget id.
     */
    public static void insertAndRefreshInstance5x1Info(Context context, int appWidgetId) {
        SQLiteDatabase sqLiteDatabase = DatabaseFactory.getDatabaseInstance(context);
        sqLiteDatabase.beginTransaction();
        try {

            // Insert instance info into database.
            InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
            InstanceInfoBean defaultInstance = InstanceInfoBean.getDefault5x1Instance();
            defaultInstance.mAppWidgetId = appWidgetId;
            dao.createInstanceInfo(context, defaultInstance);

            // Delete removed widget instances.
            dao.removeInvalidInstanceInfo(context, getValidAppWidgetIds(context));

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * Retrieves array of valid widget ids.
     */
    private static int[] getValidAppWidgetIds(Context context) {

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] appWidget1x1Ids = widgetManager.getAppWidgetIds(WidgetProviderForSize1x1
                .getComponentName(context));
        int[] appWidget5x1Ids = widgetManager.getAppWidgetIds(WidgetProviderForSize4x1
                .getComponentName(context));

        int length1x1 = appWidget1x1Ids.length;
        int length5x1 = appWidget5x1Ids.length;

        int[] validAppWidgetIds = new int[length1x1 + length5x1];

        System.arraycopy(appWidget1x1Ids, 0, validAppWidgetIds, 0, length1x1);
        System.arraycopy(appWidget5x1Ids, 0, validAppWidgetIds, length1x1, length5x1);
        return validAppWidgetIds;
    }

}
