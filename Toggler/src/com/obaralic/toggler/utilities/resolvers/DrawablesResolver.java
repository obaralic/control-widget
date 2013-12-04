
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
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.utilities.values.AirplaneModeUtility;
import com.obaralic.toggler.utilities.values.AutoRotateUtility;
import com.obaralic.toggler.utilities.values.AutoSyncUtility;
import com.obaralic.toggler.utilities.values.BluetoothUtility;
import com.obaralic.toggler.utilities.values.BrightnessUtility;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.GpsUtility;
import com.obaralic.toggler.utilities.values.MobileDataUtility;
import com.obaralic.toggler.utilities.values.RingerModeUtitliy;
import com.obaralic.toggler.utilities.values.WiFiHotspotUtility;
import com.obaralic.toggler.utilities.values.WiFiUtility;

/**
 * Utility class for resolving drawable id for corresponding {@link SkinType}.
 */
public class DrawablesResolver {

    /**
     * Returns the drawable id for the specified {@link SkinType}.
     */
    public static int getBackgroundBackgroundId(SkinType skinType) {
        switch (skinType) {
        case GREY:
            return R.drawable.grey_skin;

        case BLUE:
            return R.drawable.blue_skin;

        case TRANSPARENT:
            return R.drawable.glass_skin;

        case WOOD:
            return R.drawable.wood_skin;

        default:
            return SkinType.UNKNOWN.getSkinId();
        }
    }

    /**
     * Returns widget face selector's drawable id.
     */
    public static int getFaceBackgroundSelector() {
        return R.drawable.glass_selector_blue;
    }

    /**
     * Returns drawable id for the given {@link ContentType} and its corresponding state.
     */
    public static int getFaceIconDrawableId(ContentType contentType, int state) {
        switch (contentType) {
        case BLUETOOTH:
            return (state == BluetoothUtility.BLUETOOTH_ENABLED || state == BluetoothUtility.BLUETOOTH_DISABLING) ? R.drawable.face_icon_bluetooth
                    : R.drawable.face_icon_bluetooth;

        case WIFI:
            return (state == WiFiUtility.WIFI_ENABLED || state == WiFiUtility.WIFI_DISABLING) ? R.drawable.face_icon_wi_fi
                    : R.drawable.face_icon_wi_fi;

        case AUTO_SYNC:
            return (state == AutoSyncUtility.AUTO_SYNC_ENABLED) ? R.drawable.face_icon_auto_sync
                    : R.drawable.face_icon_auto_sync;

        case MOBILE_DATA:
            return (state == MobileDataUtility.MOBILE_DATA_ENABLED) ? R.drawable.face_icon_mobile_data
                    : R.drawable.face_icon_mobile_data;

        case SETTINGS:
            return R.drawable.face_icon_settings;

        case AUTO_ROTATE:
            return (state == AutoRotateUtility.AUTO_ROTATE_ENABLED) ? R.drawable.face_icon_auto_rotate
                    : R.drawable.face_icon_auto_rotate;

        case AIRPLANE_MODE:
            return (state == AirplaneModeUtility.AIRPLANE_MODE_ENABLED) ? R.drawable.face_icon_airplane_mode
                    : R.drawable.face_icon_airplane_mode;

        case GPS:
            return (state == GpsUtility.GPS_ENABLED) ? R.drawable.face_icon_gps : R.drawable.face_icon_gps;

        case FLASH_TORCH:
            return (state == FlashTorchUtility.FLASH_TORCH_ENABLED) ? R.drawable.face_icon_flash_torch
                    : R.drawable.face_icon_flash_torch;

        case WIFI_HOTSPOT:
            return (state == WiFiHotspotUtility.WIFI_HOTSPOT_ENABLED) ? R.drawable.face_icon_wi_fi_hotspot
                    : R.drawable.face_icon_wi_fi_hotspot;

        case RINGER_MODE:
            switch (state) {
            case RingerModeUtitliy.RING_MODE_NORMAL:
                return R.drawable.face_icon_ringer_normal;

            case RingerModeUtitliy.RING_MODE_SILENT:
                return R.drawable.face_icon_ringer_muted;

            case RingerModeUtitliy.RING_MODE_VIBRATE:
                return R.drawable.face_icon_ringer_vibrate;

            default:
                return R.drawable.face_icon_unknown;
            }

        case BRIGHTNESS_MODE:

            switch (state) {
            case BrightnessUtility.BRIGHTNESS_AUTO:
                return R.drawable.face_icon_brightness_auto;

            case BrightnessUtility.BRIGHTNESS_HIGH:
                return R.drawable.face_icon_brightness_high;

            case BrightnessUtility.BRIGHTNESS_MEDIUM:
                return R.drawable.face_icon_brightness_medium;

            case BrightnessUtility.BRIGHTNESS_LOW:
                return R.drawable.face_icon_brightness_low;

            default:
                return R.drawable.face_icon_unknown;
            }

        default:
            return R.drawable.face_icon_unknown;

        }
    }
}
