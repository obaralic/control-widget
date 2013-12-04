
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
import android.content.Intent;

import com.obaralic.toggler.activities.GpsInfoActivity;
import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceCommandTemplate;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.GpsEnablingInfoPersistance;
import com.obaralic.toggler.utilities.values.GpsUtility;

/**
 * Class that defines command for changing gps state.
 */
public class GpsContentChangeCommand extends ContentStateChangeServiceCommandTemplate {

    private static final String TAG = LogUtil.getTag(GpsContentChangeCommand.class);

    @Override
    public void doBeforeToggle(Context context) {
        super.doBeforeToggle(context);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_GPS_OPENED, true);
    }

    @Override
    public void performToggle(Context context) {
        LogUtil.d(TAG, "Called performToggle");

        boolean isGpsEnabled = GpsUtility.isGpsEnabled(context);
        GpsUtility.setGpsEnabled(context, !isGpsEnabled);

        GpsEnablingInfoPersistance gpsPersistence = GpsEnablingInfoPersistance.getInstance();
        boolean showGpsInfoDialog = gpsPersistence.shouldShowGpsInfoDialog(context);
        LogUtil.i(TAG, "Show Gps Info Dialog: " + showGpsInfoDialog);

        if (showGpsInfoDialog) {
            Intent intent = new Intent(context, GpsInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            GpsUtility.launchGpsSettingsPage(context);
        }
        
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_GPS_OPENED);
    }

}
