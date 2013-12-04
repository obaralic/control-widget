
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

import com.obaralic.toggler.service.ContentStateChangeServiceFacade;
import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceInterface;
import com.obaralic.toggler.service.commands.content.implementations.*;

import android.content.Intent;

/**
 * Content state change command factory class.
 */
public class ContentStateChangeServiceCommandFactory {

    /**
     * Generates content state change command that corresponds to the type of action passed trough the given
     * {@link Intent}.
     */
    public static ContentStateChangeServiceInterface get(Intent intent) {

        String action = intent.getAction();
        ContentStateChangeServiceInterface togglerServiceCommand = null;

        if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_WIFI.equals(action)) {
            togglerServiceCommand = new WiFiContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_INTERNET.equals(action)) {
            togglerServiceCommand = new MobileDataContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_BLUETOOTH.equals(action)) {
            togglerServiceCommand = new BluetoothContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_GPS.equals(action)) {
            togglerServiceCommand = new GpsContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_AUTO_SYNC.equals(action)) {
            togglerServiceCommand = new AutoSyncContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_SETTINGS.equals(action)) {
            togglerServiceCommand = new SettingsContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_BRIGHTNESS.equals(action)) {
            togglerServiceCommand = new BrightnessContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_SOUND.equals(action)) {
            togglerServiceCommand = new RingModeContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_AIRPLANE_MODE.equals(action)) {
            togglerServiceCommand = new AirplaneModeContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_WIFI_HOTSPOT.equals(action)) {
            togglerServiceCommand = new WiFiHotspotContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_FLASH_TORCH.equals(action)) {
            togglerServiceCommand = new FlashTorchContentChangeCommand();

        } else if (ContentStateChangeServiceFacade.Actions.ACTION_TOGGLE_AUTO_ROTATE.equals(action)) {
            togglerServiceCommand = new AutoRotateContentChangeCommand();
        }

        return togglerServiceCommand;
    }
}
