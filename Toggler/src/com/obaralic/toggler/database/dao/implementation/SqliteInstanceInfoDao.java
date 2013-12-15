
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

package com.obaralic.toggler.database.dao.implementation;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.obaralic.toggler.database.DatabaseAdapter;
import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;
import com.obaralic.toggler.database.dao.extractors.InstanceInfoCursorExtractor;

/**
 * The specific implementation of the Data Access Object interface by using SQLite database.
 */
public class SqliteInstanceInfoDao implements InstanceInfoDao {

    @Override
    public long createInstanceInfo(Context context, InstanceInfoBean instanceBean) {

        int id = instanceBean.mAppWidgetId;
        SkinType skin = instanceBean.mSkinType;
        WidgetSizeType size = instanceBean.mWidgetSizeType;
        ContentType content1 = instanceBean.mWidgetContents.get(FieldType.ONE.getFieldIndex());
        ContentType content2 = instanceBean.mWidgetContents.get(FieldType.TWO.getFieldIndex());
        ContentType content3 = instanceBean.mWidgetContents.get(FieldType.THREE.getFieldIndex());
        ContentType content4 = instanceBean.mWidgetContents.get(FieldType.FOUR.getFieldIndex());
        ContentType content5 = instanceBean.mWidgetContents.get(FieldType.FIVE.getFieldIndex());

        return DatabaseAdapter.insertInstanceInfo(context, id, skin, size, content1, content2,
                content3, content4, content5);
    }

    @Override
    public int removeInstanceInfo(Context context, int appWidgetId) {
        return DatabaseAdapter.deleteInstanceInfo(context, appWidgetId);
    }
    
    @Override
    public int removeInvalidInstanceInfo(Context context, int[] validAppWidgetIds) {
        return DatabaseAdapter.deleteInvalidInstanceInfo(context, validAppWidgetIds);
    }

    @Override
    public int updateInstanceInfo(Context context, int appWidgetId, ContentType contentType,
            FieldType contentPosition) {
        
        return DatabaseAdapter.updateInstanceInfo(context, appWidgetId, contentType, contentPosition);
    }
 
    @Override
    public int updateInstanceInfoSkinType(Context context, int appWidgetId, SkinType skinType) {
        return DatabaseAdapter.updateInstanceInfoSkinType(context, appWidgetId, skinType);
    }
    
    @Override
    public List<InstanceInfoBean> getInstanceInfo(Context context) {
        Cursor cursor = DatabaseAdapter.queryInstanceInfo(context);
        return InstanceInfoCursorExtractor.extractInstanceInfoIntoList(cursor);
    }

    @Override
    public InstanceInfoBean getInstanceInfo(Context context, int appWidgetId) {
        Cursor cursor = DatabaseAdapter.queryInstanceInfo(context, appWidgetId);
        return InstanceInfoCursorExtractor.extractInstanceInfoIntoBean(cursor);
    }

    @Override
    public List<InstanceInfoBean> getInstanceInfo(Context context, int[] appWidgetIds) {
        Cursor cursor = DatabaseAdapter.queryInstanceInfo(context, appWidgetIds);
        return InstanceInfoCursorExtractor.extractInstanceInfoIntoList(cursor);
    }   
    
}

    