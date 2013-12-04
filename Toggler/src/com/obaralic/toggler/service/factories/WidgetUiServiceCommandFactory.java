
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

package com.obaralic.toggler.service.factories;

import android.content.Intent;

import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.service.commands.ui.WidgetUiServiceCommandInterface;
import com.obaralic.toggler.service.commands.ui.WidgetUiServiceCommandTemplate;
import com.obaralic.toggler.service.commands.ui.implementations.WidgetClickedCommand;

public class WidgetUiServiceCommandFactory {

    /**
     * Retrieves a {@link WidgetUiServiceCommandInterface} object for execution on Widget UI Service's worker
     * thread.
     * 
     * @param intent
     *            The intent from which to extract the command.
     * @return The Widget UI Service command extracted from the Intent parameter.
     */
    public static WidgetUiServiceCommandInterface get(Intent intent) {

        String action = intent.getAction();
        WidgetUiServiceCommandInterface widgetServiceCommand = null;

        if (WidgetUiServiceFacade.Actions.ACTION_ENABLE.equals(action)) {
            widgetServiceCommand = new WidgetUiServiceCommandTemplate();

        } else if (WidgetUiServiceFacade.Actions.ACTION_SIMPLE_REDRAW.equals(action)) {
            widgetServiceCommand = new WidgetUiServiceCommandTemplate();

        } else if (WidgetUiServiceFacade.Actions.ACTION_CHANGE_SKIN.equals(action)) {
            widgetServiceCommand = new WidgetUiServiceCommandTemplate();

        } else if (WidgetUiServiceFacade.isOnWidgetCickAction(action)) {
            widgetServiceCommand = new WidgetClickedCommand();
        }

        return widgetServiceCommand;
    }

}
