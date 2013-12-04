
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
 * Enum that contains constant values for every supported widget size.
 */
public enum WidgetSizeType {
    
    ONE(1), FIVE(5);

    private int mWidgetSizeId;

    private WidgetSizeType(int widgetSizeId) {
        this.mWidgetSizeId = widgetSizeId;
    }

    /**
     * Retrieves constant that represents one of supported widget sizes.
     * 
     * @return The {@link Integer} representation of the specified widget sizes.
     */
    public int getWidgetSizeId() {
        return mWidgetSizeId;
    }
    
    public static WidgetSizeType getWidgetSizeType(int widgetSizeId) {
        switch (widgetSizeId) {
        case 1:
            return WidgetSizeType.ONE;
        case 5:
        default:
            return WidgetSizeType.FIVE;
        }
    }

    @Override
    public String toString() {
        switch (mWidgetSizeId) {
        case 1:
            return "[SIZE ONE]";
        case 5:
            return "[SIZE FIVE]";
        default:
            return super.toString();
        }            
    }
}

	