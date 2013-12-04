
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

package com.obaralic.toggler.service.commands.content.implementations;

import android.content.Context;

import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceCommandTemplate;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.values.WiFiUtility;

/**
 * Class that defines command for changing WiFi state.
 */
public class WiFiContentChangeCommand extends ContentStateChangeServiceCommandTemplate {

    private static final String TAG = LogUtil.getTag(WiFiContentChangeCommand.class);

    @Override
    public void doBeforeToggle(Context context) {
        super.doBeforeToggle(context);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_WIFI_STATE_CHANGED, true);
    }

    @Override
    public void performToggle(Context context) {
        LogUtil.d(TAG, "Called performToggle");
        int[] statusesArray = ContentStatesPersistence.getInstance().getContentStates(context);
        int contentId = ContentType.WIFI.getContentId();
        int wiFiStatus = statusesArray[contentId];
        boolean enable = !(wiFiStatus == WiFiUtility.WIFI_ENABLED || wiFiStatus == WiFiUtility.WIFI_ENABLING);
        WiFiUtility.setEnabled(context, enable);
        
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_WIFI_STATE_CHANGED,
                ContentType.WIFI.toString(), enable);
    }

}
