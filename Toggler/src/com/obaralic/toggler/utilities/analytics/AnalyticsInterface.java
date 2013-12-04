
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

import java.util.Map;

import android.content.Context;

/**
 * Interface that defines contract for sending data to analytics vendor.
 */
public interface AnalyticsInterface {

    void startAnalyticsSession(Context context);

    void endAnalyticsSession(Context context);

    void sendEvent(String event);

    void sendEvent(String event, boolean isTimed);

    void sendEvent(String event, Map<String, String> paramMap);

    void sendEvent(String event, Map<String, String> paramMap, boolean isTimed);
    
    void sendEvent(String event, String paramName, boolean paramValue);
    
    void sendEvent(String event, String paramName, String paramValue);

}
