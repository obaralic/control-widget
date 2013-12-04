
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

package com.obaralic.toggler.database.dao;

import java.util.List;

import android.content.Context;

import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.SkinType;

/**
 * The Data Access Object interface for interacting with the persistent storage.
 */
public interface InstanceInfoDao {

    long createInstanceInfo(Context context, InstanceInfoBean instanceBean);

    int removeInstanceInfo(Context context, int appWidgetId);

    int removeInvalidInstanceInfo(Context context, int[] validAppWidgetIds);

    int updateInstanceInfo(Context context, int appWidgetId, ContentType contentType,
            FieldType contentPosition);

    int updateInstanceInfoSkinType(Context context, int appWidgetId, SkinType skinType);

    List<InstanceInfoBean> getInstanceInfo(Context context);

    InstanceInfoBean getInstanceInfo(Context context, int appWidgetId);

    List<InstanceInfoBean> getInstanceInfo(Context context, int[] appWidgetIds);

}
