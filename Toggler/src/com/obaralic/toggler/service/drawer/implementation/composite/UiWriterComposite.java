
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

package com.obaralic.toggler.service.drawer.implementation.composite;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.provider.types.WidgetProviderForSize1x1;
import com.obaralic.toggler.provider.types.WidgetProviderForSize4x1;
import com.obaralic.toggler.service.drawer.UiWriterInterface;
import com.obaralic.toggler.service.drawer.implementation.UiWriter;
import com.obaralic.toggler.service.factories.RemoteViewsFactory;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Class that implements {@link UiWriterInterface} and defines drawing for all instantiated widgets.
 */
public class UiWriterComposite implements UiWriterInterface {

    private static final String TAG = LogUtil.getTag(UiWriterComposite.class);

    /**
     * UI Writer for actual separate widgets
     */
    private UiWriter mUiWriter = new UiWriter();

    @Override
    public void draw(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        updateAllWidgetsOfSize1x1(appWidgetManager, context);
        updateAllWidgetsOfSize5x1(appWidgetManager, context);
    }

    @Override
    public void draw(Context context, boolean isSmallWidget) {
        LogUtil.e(TAG, "Unused draw methode with size param!");
    }

    @Override
    public void draw(Context context, InstanceInfoBean instanceInfoBean, boolean isSmallWidget) {
        LogUtil.e(TAG, "Unused draw method with widget id and size params!");
    }

    /**
     * Redraws all widget 1x1 instances.
     * 
     * @param appWidgetManager
     *            The {@link AppWidgetManager}.
     * @param context
     *            The surrounding {@link Context}.
     */
    private void updateAllWidgetsOfSize1x1(AppWidgetManager appWidgetManager, Context context) {
        LogUtil.d(TAG, "Entering method: update All Widgets Of Size 1x1()");

        // Get widget ids
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(WidgetProviderForSize1x1
                .getComponentName(context));

        InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
        List<InstanceInfoBean> instanceInfoBeans = dao.getInstanceInfo(context, appWidgetIds);

        // Perform UI writes for each widget that belongs to our AppWidgetProvider
        for (InstanceInfoBean instanceInfo : instanceInfoBeans) {
            RemoteViews remoteViews = RemoteViewsFactory.getInstance().get(context, true);

            // Actual Drawing
            mUiWriter.setPanel(remoteViews);
            mUiWriter.draw(context, instanceInfo, true);

            appWidgetManager.updateAppWidget(instanceInfo.mAppWidgetId, remoteViews);
        }
    }

    /**
     * Redraws all widget 5x1 instances.
     * 
     * @param appWidgetManager
     *            The {@link AppWidgetManager}.
     * @param context
     *            The surrounding {@link Context}.
     */
    private void updateAllWidgetsOfSize5x1(AppWidgetManager appWidgetManager, Context context) {
        LogUtil.d(TAG, "Entering method: update All Widgets Of Size 4x1()");

        // Get widget ids
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(WidgetProviderForSize4x1
                .getComponentName(context));

        InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
        List<InstanceInfoBean> instanceInfoBeans = dao.getInstanceInfo(context, appWidgetIds);

        // Perform UI writes for each widget that belongs to our AppWidgetProvider
        for (InstanceInfoBean instanceInfo : instanceInfoBeans) {
            RemoteViews remoteViews = RemoteViewsFactory.getInstance().get(context, false);

            // Actual Drawing
            mUiWriter.setPanel(remoteViews);
            mUiWriter.draw(context, instanceInfo, false);

            appWidgetManager.updateAppWidget(instanceInfo.mAppWidgetId, remoteViews);
            LogUtil.w(TAG, instanceInfo == null ? "null" : instanceInfo.toString());
        }
    }

    @Override
    public void setPanel(RemoteViews remoteViews) {
    }

}
