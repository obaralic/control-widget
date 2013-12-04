
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

package com.obaralic.toggler.utilities.analytics;

/**
 * Event analytics factory.
 */
public class AnalyticsFactory {

    public static enum AnalyticsProviderType {

        FLURRY_ANALYTICS(1), GOOGLE_ANALYTICS(2);

        private int mAnalyticsProviderId;

        private AnalyticsProviderType(int analyticsProviderId) {
            this.mAnalyticsProviderId = analyticsProviderId;
        }

        /**
         * Retrieves constant that represents one of supported analytics vendor types.
         * 
         * @return The {@link Integer} representation of the specified analytics vendor type.
         */
        public int getAnalyticsProviderId() {
            return mAnalyticsProviderId;
        }
    }

    /**
     * Creates instance of the specified analytics vendor.
     */
    public static AnalyticsInterface get(AnalyticsProviderType analyticsProviderType) {

        AnalyticsInterface analytics = null;

        if (analyticsProviderType == AnalyticsProviderType.FLURRY_ANALYTICS) {
            analytics = FlurryAnalytics.getInstance();
        }

        return analytics;
    }
}
