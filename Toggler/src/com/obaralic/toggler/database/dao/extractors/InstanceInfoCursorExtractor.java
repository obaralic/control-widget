
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

package com.obaralic.toggler.database.dao.extractors;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.obaralic.toggler.database.DatabaseConstants.InstanceInfo;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;

/**
 *  Class for extracting fetched database data from {@link Cursor} into {@link InstanceInfoBean}.
 */
public class InstanceInfoCursorExtractor {
    
    /**
     * Packs data from a Cursor row into a {@link InstanceInfoBean}
     * 
     * @param cursor
     *            The surrounding {@link Context}.
     * @return The {@link InstanceInfoBean} that holds the data from the Cursor's row.
     */
    private static InstanceInfoBean extractInstanceInfoBeanFromCursorRow(Cursor cursor) {
        InstanceInfoBean instanceInfoBean = new InstanceInfoBean();
        extractFromCursorRowToInstanceInfoBean(cursor, instanceInfoBean);
        return instanceInfoBean;
    }

    private static void extractFromCursorRowToInstanceInfoBean(Cursor cursor,
            InstanceInfoBean instanceInfoBean) {

        long rowId = cursor.getLong(cursor.getColumnIndex(InstanceInfo._ID));
        int appWidgetId = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_WIDGET_ID));
        int widgetSkin = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_SKIN));
        int widgetSize = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_SIZE));
        int content1 = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_CONTENT_1));
        int content2 = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_CONTENT_2));
        int content3 = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_CONTENT_3));
        int content4 = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_CONTENT_4));
        int content5 = cursor.getInt(cursor.getColumnIndex(InstanceInfo.COLUMN_CONTENT_5));
             
        instanceInfoBean.mId = rowId;
        instanceInfoBean.mAppWidgetId = appWidgetId;
        instanceInfoBean.mWidgetSizeType = WidgetSizeType.getWidgetSizeType(widgetSize);
        instanceInfoBean.mSkinType = SkinType.getSkinType(widgetSkin);
        instanceInfoBean.mWidgetContents.add(ContentType.getContentType(content1));
        instanceInfoBean.mWidgetContents.add(ContentType.getContentType(content2));
        instanceInfoBean.mWidgetContents.add(ContentType.getContentType(content3));
        instanceInfoBean.mWidgetContents.add(ContentType.getContentType(content4));
        instanceInfoBean.mWidgetContents.add(ContentType.getContentType(content5));        
    }

    public static InstanceInfoBean extractInstanceInfoIntoBean(Cursor cursor) {
        InstanceInfoBean instanceInfoBean = null;
        if (cursor.moveToFirst()) {
            instanceInfoBean = extractInstanceInfoBeanFromCursorRow(cursor);
        }
        cursor.close();
        return instanceInfoBean;
    }

    /**
     * Packs data from a {@link Cursor} row into a list of {@link InstanceInfoBean}.
     * 
     * @param cursor
     *            he surrounding {@link Context}.
     * @return The list of {@link InstanceInfoBean} that holds the {@link Cursor} rows' data.
     */
    public static List<InstanceInfoBean> extractInstanceInfoIntoList(Cursor cursor) {
        ArrayList<InstanceInfoBean> list = new ArrayList<InstanceInfoBean>();
        while (cursor.moveToNext()) {
            list.add(extractInstanceInfoBeanFromCursorRow(cursor));
        }
        cursor.close();
        return list;
    }

}

    