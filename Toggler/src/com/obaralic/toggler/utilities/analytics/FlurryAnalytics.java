
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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.flurry.android.FlurryAgent;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomParams;

/**
 * This class represents concrete implementation of the {@link AnalyticsInterface} for the use with the Flurry
 * analytics vendor.
 */
public class FlurryAnalytics implements AnalyticsInterface {

    private static final String FLURRY_API_KEY = "68TK7YFKYWT2JB2TCJZF";

    private static FlurryAnalytics sInstance;

    private FlurryAnalytics() {
    }

    /**
     * Retrieves singleton instance of this class. 
     */
    public static FlurryAnalytics getInstance() {
        if (sInstance == null) {
            sInstance = new FlurryAnalytics();
        }
        return sInstance;
    }

    public String getFlurryProjectId() {
        return FLURRY_API_KEY;
    }

    @Override
    public void startAnalyticsSession(Context context) {
        FlurryAgent.onStartSession(context, getFlurryProjectId());
    }

    @Override
    public void endAnalyticsSession(Context context) {
        FlurryAgent.onEndSession(context);
    }

    @Override
    public void sendEvent(String event) {
        FlurryAgent.logEvent(event);
    }

    @Override
    public void sendEvent(String event, boolean isTimed) {
        FlurryAgent.logEvent(event, isTimed);
    }

    @Override
    public void sendEvent(String event, Map<String, String> paramMap) {
        FlurryAgent.logEvent(event, paramMap);
    }

    @Override
    public void sendEvent(String event, Map<String, String> paramMap, boolean isTimed) {
        FlurryAgent.logEvent(event, paramMap, isTimed);
    }

    @Override
    public void sendEvent(String event, String paramName, boolean paramValue) {
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        String state = paramValue ? FlurryCustomParams.ENABLED : FlurryCustomParams.DISABLED;
        paramsMap.put(paramName, state);
        sendEvent(event, paramsMap);        
    }
    
    @Override
    public void sendEvent(String event, String paramName, String paramValue) {
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put(paramName, paramValue);
        sendEvent(event, paramsMap);        
    }

}
