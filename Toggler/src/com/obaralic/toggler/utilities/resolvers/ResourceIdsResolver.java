
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
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;

/**
 * Utility class for resolving layout id for corresponding {@link WidgetSizeType} and {@link FieldType}.
 */
public class ResourceIdsResolver {

    /**
     * Retrieves status bar drawable id for the given {@link WidgetSizeType} and {@link FieldType}.
     */
    public static int getStatusBarDrawableId(WidgetSizeType widgetSizeType, FieldType fieldType) {

        if (widgetSizeType == WidgetSizeType.ONE && fieldType == null) {
            return R.id.toggler_state_ImageView;

        } else if (widgetSizeType == WidgetSizeType.FIVE && fieldType != null) {

            switch (fieldType) {
            case ONE:
                return R.id.toggler_state_ImageView_1;
            case TWO:
                return R.id.toggler_state_ImageView_2;
            case THREE:
                return R.id.toggler_state_ImageView_3;
            case FOUR:
                return R.id.toggler_state_ImageView_4;
            case FIVE:
                return R.id.toggler_state_ImageView_5;
            }
        }

        return 0;

    }

    /**
     * Retrieves image drawable id for the given {@link WidgetSizeType} and {@link FieldType}.
     */
    public static int getImageDrawableId(WidgetSizeType widgetSizeType, FieldType fieldType) {

        if (widgetSizeType == WidgetSizeType.ONE && fieldType == null) {
            return R.id.toggler_content_ImageView;

        } else if (widgetSizeType == WidgetSizeType.FIVE && fieldType != null) {

            switch (fieldType) {
            case ONE:
                return R.id.toggler_content_ImageView_1;
            case TWO:
                return R.id.toggler_content_ImageView_2;
            case THREE:
                return R.id.toggler_content_ImageView_3;
            case FOUR:
                return R.id.toggler_content_ImageView_4;
            case FIVE:
                return R.id.toggler_content_ImageView_5;
            }
        }

        return 0;

    }

}
