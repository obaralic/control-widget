
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

package com.obaralic.toggler.utilities.resolvers;

import com.obaralic.toggler.R;
import com.obaralic.toggler.database.dao.enums.ContentType;

/**
 * Utility class for resolving string value for corresponding {@link ContentType}.
 */
public class StringsResolver {

    /**
     * Determine string value that needs to be displayed for the given {@link ContentType}.
     * 
     * @param contentType
     *            The {link {@link ContentType}.
     * @return The string representation for UI.
     */
    public static int getFieldTitleResourceId(ContentType contentType) {
        switch (contentType) {
        case WIFI:
            return R.string.FaceTextWiFi;

        case MOBILE_DATA:
            return R.string.FaceTextInternet;

        case BLUETOOTH:
            return R.string.FaceTextBluetooth;

        case GPS:
            return R.string.FaceTextGPS;

        case AUTO_SYNC:
            return R.string.FaceTextAutoSync;

        case SETTINGS:
            return R.string.FaceTextSettings;

        case BRIGHTNESS_MODE:
            return R.string.FaceTextBrightness;

        case RINGER_MODE:
            return R.string.FaceTextSound;

        case AIRPLANE_MODE:
            return R.string.FaceTextAirplaneMode;

        case WIFI_HOTSPOT:
            return R.string.FaceTextWiFiHotspot;

        case FLASH_TORCH:
            return R.string.FaceTextFlashTorch;

        case AUTO_ROTATE:
            return R.string.FaceTextAutoRotate;

        default:
            return android.R.string.untitled;
        }
    }    
}
