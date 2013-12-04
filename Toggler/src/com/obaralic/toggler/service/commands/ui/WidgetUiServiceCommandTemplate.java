
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

package com.obaralic.toggler.service.commands.ui;

import android.content.Context;

import com.obaralic.toggler.service.WidgetUiService;
import com.obaralic.toggler.service.drawer.UiWriterInterface;
import com.obaralic.toggler.service.drawer.implementation.composite.UiWriterComposite;
import com.obaralic.toggler.utilities.debug.LogUtil;

public class WidgetUiServiceCommandTemplate implements WidgetUiServiceCommandInterface {

    private static final String TAG = LogUtil.getTag(WidgetUiServiceCommandTemplate.class);

    // The object to perform actual draw actions on the Widget's UI
    protected UiWriterInterface mUiWriter = new UiWriterComposite();

    @Override
    public void execute(WidgetUiService widgetUiService) {
        LogUtil.d(TAG, "Called execute");

        // Here we need to do database re-query if needed
        doBeforeDrawingUi(widgetUiService);

        // Draw the UI of widgets
        mUiWriter.draw(widgetUiService);
    }

    @Override
    public void doBeforeDrawingUi(Context context) {
    }

}
