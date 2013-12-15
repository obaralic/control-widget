
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
public enum SkinType {
    
    UNKNOWN(-1), GREY(1), BLUE(2), TRANSPARENT(3), WOOD(4);
    
    private int mSkinId;

    private SkinType(int skinId) {
        this.mSkinId = skinId;
    }

    /**
     * Retrieves constant that represents one of supported skin types.
     * 
     * @return The {@link Integer} representation of the specified skin type.
     */
    public int getSkinId() {
        return mSkinId;
    }
    
    public static SkinType getSkinType(int skinId) {
        switch (skinId) {
        case 1:
            return SkinType.GREY;
        case 2:
            return SkinType.BLUE;
        case 3:
            return SkinType.TRANSPARENT;
        case 4:
            return SkinType.WOOD;
        default:
            return SkinType.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (mSkinId) {
        case 1:
            return "[GREY]";
        case 2:
            return "[BLUE]";
        case 3:
            return "[TRANSPARENT]";
        case 4:
            return "[WOOD]";
        case 5:
            return "[INDUSTRIAL]";
        default:
            return "[UNKNOWN]";
        }            
    }
    
}

    