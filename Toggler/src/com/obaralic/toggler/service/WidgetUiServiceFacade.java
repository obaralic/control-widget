
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

package com.obaralic.toggler.service;

import android.content.Context;
import android.content.Intent;

import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;

public class WidgetUiServiceFacade {

    private static WidgetUiServiceFacade sInstance;

    /**
     * Class holding all of the available Intent actions for starting the Widget UI Service.
     */
    public static class Actions {

        public static final String ACTION_ENABLE = "com.obaralic.toggler.action.ACTION_ENABLE";

        public static final String ACTION_CHANGE_SKIN = "com.obaralic.toggler.action.ACTION_CHANGE_SKIN";

        public static final String ACTION_SIMPLE_REDRAW = "com.obaralic.toggler.action.ACTION_SIMPLE_REDRAW";

        public static final String ACTION_WIDGET_1x1_FIELD_1_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_1x1_FIELD_1_CLICKED";

        public static final String ACTION_WIDGET_4x1_FIELD_1_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_4x1_FIELD_1_CLICKED";

        public static final String ACTION_WIDGET_4x1_FIELD_2_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_4x1_FIELD_2_CLICKED";

        public static final String ACTION_WIDGET_4x1_FIELD_3_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_4x1_FIELD_3_CLICKED";

        public static final String ACTION_WIDGET_4x1_FIELD_4_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_4x1_FIELD_4_CLICKED";

        public static final String ACTION_WIDGET_4x1_FIELD_5_CLICKED = "com.obaralic.toggler.action.ACTION_WIDGET_4x1_FIELD_5_CLICKED";

        /**
         * Retrieves on click action for specified widget size and specified field order number
         * 
         * @param widgetSize
         *            The widget size in fields as defined in {@link SizeType}
         * @param fieldOrderNumber
         *            The field order number
         * @return Corresponding action string, or <b>null</b> none are found
         */
        public static String getOnClickAction(WidgetSizeType widgetSize, FieldType fieldType) {
            if (widgetSize == WidgetSizeType.ONE) {
                return ACTION_WIDGET_1x1_FIELD_1_CLICKED;
            }

            if (widgetSize == WidgetSizeType.FIVE) {
                switch (fieldType) {
                case ONE:
                    return ACTION_WIDGET_4x1_FIELD_1_CLICKED;
                case TWO:
                    return ACTION_WIDGET_4x1_FIELD_2_CLICKED;
                case THREE:
                    return ACTION_WIDGET_4x1_FIELD_3_CLICKED;
                case FOUR:
                    return ACTION_WIDGET_4x1_FIELD_4_CLICKED;
                case FIVE:
                    return ACTION_WIDGET_4x1_FIELD_5_CLICKED;
                default:
                    break;
                }
            }

            return null;
        }
    }

    private WidgetUiServiceFacade() {
    }

    public static WidgetUiServiceFacade get() {
        if (sInstance == null) {
            sInstance = new WidgetUiServiceFacade();
        }
        return sInstance;
    }

    public void startOnEnabled(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_ENABLE);
        context.startService(intent);
    }

    public void startForSimpleRedraw(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_SIMPLE_REDRAW);
        context.startService(intent);
    }

    public void startOnWidget1x1Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_1x1_FIELD_1_CLICKED);
        context.startService(intent);
    }

    public void startOnWidget4x1Field1Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_4x1_FIELD_1_CLICKED);
        context.startService(intent);
    }

    public void startOnWidget4x1Field2Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_4x1_FIELD_2_CLICKED);
        context.startService(intent);
    }

    public void startOnWidget4x1Field3Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_4x1_FIELD_3_CLICKED);
        context.startService(intent);
    }

    public void startOnWidget4x1Field4Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_4x1_FIELD_4_CLICKED);
        context.startService(intent);
    }

    public void startOnWidget4x1Field5Clicked(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_WIDGET_4x1_FIELD_5_CLICKED);
        context.startService(intent);
    }

    public void startOnChangeSkinRequest(Context context) {
        Intent intent = new Intent(context, WidgetUiService.class);
        intent.setAction(Actions.ACTION_CHANGE_SKIN);
        context.startService(intent);
    }

    public void stopService(Context context) {
        context.stopService(new Intent(context, WidgetUiService.class));
    }

    public static boolean isOnWidgetCickAction(String action) {
        return WidgetUiServiceFacade.Actions.ACTION_WIDGET_1x1_FIELD_1_CLICKED.equals(action)
                || WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_1_CLICKED.equals(action)
                || WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_2_CLICKED.equals(action)
                || WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_3_CLICKED.equals(action)
                || WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_4_CLICKED.equals(action)
                || WidgetUiServiceFacade.Actions.ACTION_WIDGET_4x1_FIELD_5_CLICKED.equals(action);
    }

}
