
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

package com.obaralic.toggler.service.commands.content.implementations;

import android.content.Context;
import android.content.Intent;

import com.obaralic.toggler.activities.FlashTorchControlActivity;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceCommandTemplate;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.LedFlashEnablingInfoPersistence;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.LedFlashUtility;

/**
 * Class that defines command for changing flash torch state.
 */
public class FlashTorchContentChangeCommand extends ContentStateChangeServiceCommandTemplate {

    private static final String TAG = LogUtil.getTag(FlashTorchContentChangeCommand.class);

    @Override
    public void doBeforeToggle(Context context) {
        super.doBeforeToggle(context);

        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_LED_FLASH_STATE_CHANGED, true);
    }

    @Override
    public void performToggle(Context context) {
        LogUtil.d(TAG, "Called performToggle");

        // Read current flash torch state from persistence.
        ContentStatesPersistence statusesPersistence = ContentStatesPersistence.getInstance();
        int[] statusesArray = statusesPersistence.getContentStates(context);
        int contentId = ContentType.FLASH_TORCH.getContentId();
        int value = statusesArray[contentId];

        if (value == FlashTorchUtility.FLASH_TORCH_DISABLED) {
            value = FlashTorchUtility.FLASH_TORCH_ENABLED;
        } else {
            value = FlashTorchUtility.FLASH_TORCH_DISABLED;
        }

        // Send broadcast intent with state value in order to change it.
        //
        // Intent intent = new Intent(FlashTorchUtility.ACTION_CHANGE_FLASH_TORCH);
        // intent.putExtra(FlashTorchUtility.EXTRA_CHANGE_FLASH_TORCH, value);
        // context.sendBroadcast(intent);

        LedFlashEnablingInfoPersistence ledPersistence = LedFlashEnablingInfoPersistence.getInstance();
        boolean useLedFix = ledPersistence.isLedFlashFixInUse(context);

        Intent intent = new Intent(context, FlashTorchControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(FlashTorchControlActivity.EXTRA_FLASH_TORCH_STATE, value);
        intent.putExtra(FlashTorchControlActivity.EXTRA_USE_FIX, useLedFix);
        context.startActivity(intent);
        
        boolean enable = (value == LedFlashUtility.FLASH_TORCH_ENABLED);
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_BLUETOOTH_STATE_CHANGED,
                ContentType.BLUETOOTH.toString(), enable);
    }

}
