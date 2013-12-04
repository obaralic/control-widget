
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

package com.obaralic.toggler.activities;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.database.DatabaseTransactions;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * The dummy activity used for refreshing widget instances and for determining size of the newly
 * instantiated widget.
 */
public class Widget5x1ConfigureActivity extends BaseActivity {

    private static final String TAG = LogUtil.getTag(Widget5x1ConfigureActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Analytics
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_WIDGET_5x1_INSTANTIATION, true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String EXTRA_APPWIDGET_ID = AppWidgetManager.EXTRA_APPWIDGET_ID;
            int appWidgetId = extras.getInt(EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                        
            DatabaseTransactions.insertAndRefreshInstance5x1Info(this, appWidgetId);
                        
            // Do a simple widgets redraw.
            WidgetUiServiceFacade widgetUiServiceFacade = WidgetUiServiceFacade.get();
            widgetUiServiceFacade.startForSimpleRedraw(this);
            
            // This is needed in order for the widget to be updated.
            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);  
            setResult(RESULT_OK, intent);
            finish();

        } else {
            LogUtil.w(TAG, "Extras are NULL!");
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
