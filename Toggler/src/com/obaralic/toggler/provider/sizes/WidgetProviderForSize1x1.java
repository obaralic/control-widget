
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

package com.obaralic.toggler.provider.sizes;

import android.content.ComponentName;
import android.content.Context;

import com.obaralic.toggler.provider.WidgetProvider;

/**
 * Helper class used for resolving {@link ComponentName} for this class.
 */
public class WidgetProviderForSize1x1 extends WidgetProvider {

    /**
     * Creates {@link ComponentName} object used for resolving widget id values.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @return The {@link ComponentName} instance.
     */
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context, WidgetProviderForSize1x1.class);
    }
}
