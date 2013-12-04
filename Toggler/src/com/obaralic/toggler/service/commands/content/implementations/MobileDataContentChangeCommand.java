
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
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceCommandTemplate;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.values.MobileDataUtility;

/**
 * Content state change command for mobile data traffic.
 */
public class MobileDataContentChangeCommand extends ContentStateChangeServiceCommandTemplate {

    private static final String TAG = LogUtil.getTag(MobileDataContentChangeCommand.class);

    @Override
    public void doBeforeToggle(Context context) {
        super.doBeforeToggle(context);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_MOBILE_DATA_STATE_CHANGED, true);
    }

    @Override
    public void performToggle(Context context) {
        LogUtil.d(TAG, "Called performToggle");

        int contentId = ContentType.MOBILE_DATA.getContentId();
        int[] statusesArray = ContentStatesPersistence.getInstance().getContentStates(context);
        int mobileDataStatus = statusesArray[contentId];

        boolean isEnabled = MobileDataUtility.isEnabled(context);
        int isEnabledInt = isEnabled ? MobileDataUtility.MOBILE_DATA_ENABLED
                : MobileDataUtility.MOBILE_DATA_DISABLED;

        isEnabled = !isEnabled;

        if (isEnabledInt == mobileDataStatus) {
            isEnabledInt = isEnabled ? MobileDataUtility.MOBILE_DATA_ENABLED
                    : MobileDataUtility.MOBILE_DATA_DISABLED;
            statusesArray[contentId] = isEnabledInt;
            ContentStatesPersistence.getInstance().setContentStates(context, statusesArray);
        }

        MobileDataUtility.setEnabled(context, isEnabled);

        WidgetUiServiceFacade.get().startForSimpleRedraw(context);
        
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_MOBILE_DATA_STATE_CHANGED,
                ContentType.MOBILE_DATA.toString(), isEnabled);
    }
}
