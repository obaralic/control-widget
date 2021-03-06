
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
import com.obaralic.toggler.utilities.values.AutoRotateUtility;

/**
 * Class that defines command for changing auto-rotate state. 
 */
public class AutoRotateContentChangeCommand extends ContentStateChangeServiceCommandTemplate {

    private static final String TAG = LogUtil.getTag(AutoRotateContentChangeCommand.class);

    @Override
    public void doBeforeToggle(Context context) {
        super.doBeforeToggle(context);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_AUTO_ROTATE_STATE_CHANGED, true);
    }

    @Override
    public void performToggle(Context context) {
        LogUtil.d(TAG, "Called performToggle");
        boolean isAutoRotateEnabled = !AutoRotateUtility.isAutoRotateEnabled(context);
        AutoRotateUtility.setAutoRotateEnabled(context, isAutoRotateEnabled);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_AUTO_ROTATE_STATE_CHANGED,
                ContentType.AUTO_ROTATE.toString(), isAutoRotateEnabled);
    }

}
