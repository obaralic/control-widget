
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

package com.obaralic.toggler.utilities.debug;

/**
 * Class used for generating tag strings.
 */
public class LogUtil extends AbstractLogUtil {

    private static final String APP_TAG = "SmarT_";

    /**
     * Retrieves log tag for this application.
     * 
     * @return The Tag String.
     */
    public static String getGlobalTagPrefix() {
        return APP_TAG;
    }

    /**
     * Forms a final log tag based on the class.
     * 
     * @param object
     *            The caller object.
     * @return The Tag String.
     */
    public static String getTag(Class<?> clazz) {
        return getGlobalTagPrefix() + clazz.getSimpleName();
    }

}
