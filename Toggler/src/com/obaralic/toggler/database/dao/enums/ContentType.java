
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

package com.obaralic.toggler.database.dao.enums;

/**
 * Enum that contains constant values for every widget toggle type.
 */
public enum ContentType {
    
    UNKNOWN(-1), WIFI(0), MOBILE_DATA(1), BLUETOOTH(2), GPS(3), AUTO_SYNC(4), 
    SETTINGS(5), BRIGHTNESS_MODE(6), RINGER_MODE(7), AIRPLANE_MODE(8), 
    WIFI_HOTSPOT(9), FLASH_TORCH(10), AUTO_ROTATE(11);
    
    private static final int CONTENT_COUNT = 12;

    private int mContentId;

    private ContentType(int contentId) {
        this.mContentId = contentId;
    }

    /**
     * Retrieves constant that represents one of supported content type.
     * 
     * @return The {@link Integer} representation of the specified content type.
     */
    public int getContentId() {
        return mContentId;
    }
    
    public static ContentType getContentType(int contentId) {
        switch (contentId) {
        case 0:
            return ContentType.WIFI;
        case 1:
            return ContentType.MOBILE_DATA;
        case 2:
            return ContentType.BLUETOOTH;
        case 3:
            return ContentType.GPS;
        case 4:
            return ContentType.AUTO_SYNC;
        case 5:
            return ContentType.SETTINGS;
        case 6:
            return ContentType.BRIGHTNESS_MODE;
        case 7:
            return ContentType.RINGER_MODE;
        case 8:
            return ContentType.AIRPLANE_MODE;
        case 9:
            return ContentType.WIFI_HOTSPOT;
        case 10:
            return ContentType.FLASH_TORCH;
        case 11:
            return ContentType.AUTO_ROTATE;
        default:
            return ContentType.UNKNOWN;
        }
    }
    
    public static int getContentCount() {
        return CONTENT_COUNT;
    }

    @Override
    public String toString() {
        switch (mContentId) {
        case 0:
            return "[WiFi]";
        case 1:
            return "[Mobile Data]";
        case 2:
            return "[Bluetooth]";
        case 3:
            return "[GPS]";
        case 4:
            return "[Auto Sync]";
        case 5:
            return "[Settings]";
        case 6:
            return "[Brightness mode]";
        case 7:
            return "[Ringer mode]";
        case 8:
            return "[Airplane mode]";
        case 9:
            return "[WiFi Hotspot]";
        case 10:
            return "[Flash Torch]";
        case 11:
            return "[Auto Rotate]";
        case -1:
            return "[Unknown]";
        default:
            return super.toString();
        }            
    }
    
}

    