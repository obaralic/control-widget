
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

package com.obaralic.toggler.service.threads;

import com.obaralic.toggler.service.WidgetUiService;
import com.obaralic.toggler.service.commands.ui.WidgetUiServiceCommandInterface;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.structures.EndlessFifoBuffer;

/**
 * Worker thread used for performing operations received by {@link WidgetUiService}.
 */
public class WidgetUiServiceWorkerThread extends Thread {

    private static final String TAG = LogUtil.getTag(WidgetUiServiceWorkerThread.class);

    // Buffer of commands for execution to take from.
    private EndlessFifoBuffer<WidgetUiServiceCommandInterface> mCommandBuffer = new EndlessFifoBuffer<WidgetUiServiceCommandInterface>();

    // The Service to whom this command belongs.
    protected WidgetUiService mWidgetUiService;

    public WidgetUiServiceWorkerThread(WidgetUiService widgetUiService) {
        this.mWidgetUiService = widgetUiService;
    }

    /**
     * Put a new Command into this thread's buffer of commands for execution. Wakes up the thread if asleep.
     * 
     * @param widgetUiServiceCommand
     *            The command to execute
     */
    public void addJob(WidgetUiServiceCommandInterface widgetUiServiceCommandInterface) {
        mCommandBuffer.putAndYield(widgetUiServiceCommandInterface);
    }

    @Override
    public void run() {

        // Run until interrupted.
        while (!isInterrupted()) {
            WidgetUiServiceCommandInterface command;
            try {
                // Get the command to execute , block if there isn't any.
                command = mCommandBuffer.get();

            } catch (InterruptedException e) {
                // Terminate thread if interrupted.
                LogUtil.w(TAG, "Interrupted the WidgetUiService Worker Thread.");
                return;
            }

            // Execute the command
            command.execute(mWidgetUiService);
        }
    }

}
