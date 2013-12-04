
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

package com.obaralic.toggler.activities.base;

import android.app.ListActivity;

import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;

/**
 * The {@link ListActivity} used as base for every other list activity, with purpose of starting and
 * ending session with analytics tool.
 */
public class BaseListActivity extends ListActivity {

    @Override
    protected void onStart() {
        super.onStart();
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.startAnalyticsSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.endAnalyticsSession(this);
    }

}
