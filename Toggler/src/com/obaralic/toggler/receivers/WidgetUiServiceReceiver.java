
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

package com.obaralic.toggler.receivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.validation.IntentValidator;
import com.obaralic.toggler.utilities.values.AirplaneModeUtility;
import com.obaralic.toggler.utilities.values.AutoRotateUtility;
import com.obaralic.toggler.utilities.values.AutoSyncUtility;
import com.obaralic.toggler.utilities.values.BluetoothUtility;
import com.obaralic.toggler.utilities.values.BrightnessUtility;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.GpsUtility;
import com.obaralic.toggler.utilities.values.MobileDataUtility;
import com.obaralic.toggler.utilities.values.WiFiHotspotUtility;
import com.obaralic.toggler.utilities.values.WiFiUtility;

/**
 * Receiver used for detecting changing of the system content states.
 */
public class WidgetUiServiceReceiver extends BroadcastReceiver {

    private static final String TAG = LogUtil.getTag(WidgetUiServiceReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {

        if (new IntentValidator().isOkay(intent)) {
            String action = intent.getAction();
            LogUtil.d(TAG, "Action: " + action);

            boolean shouldRedrawUi = true;
            int unknown = ContentType.UNKNOWN.getContentId();

            ContentStatesPersistence statusesPersistence = ContentStatesPersistence.getInstance();
            int[] statusesArray = statusesPersistence.getContentStates(context);

            // ============ WiFi Intent Received ============================================================
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {

                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, unknown);
                if (state == unknown) {
                    LogUtil.w(TAG, "Empty extra for wifi state. Exiting.");
                    return;
                }

                int contentId = ContentType.WIFI.getContentId();

                if (state == WifiManager.WIFI_STATE_ENABLING) {
                    statusesArray[contentId] = WiFiUtility.WIFI_ENABLING;

                } else if (state == WifiManager.WIFI_STATE_ENABLED) {
                    statusesArray[contentId] = WiFiUtility.WIFI_ENABLED;

                } else if (state == WifiManager.WIFI_STATE_DISABLING) {
                    statusesArray[contentId] = WiFiUtility.WIFI_DISABLING;

                } else if (state == WifiManager.WIFI_STATE_DISABLED) {
                    statusesArray[contentId] = WiFiUtility.WIFI_DISABLED;
                }
            }
            // ============ Bluetooth Intent Received =======================================================
            else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {

                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, unknown);
                if (state == unknown) {
                    LogUtil.w(TAG, "Empty extra for bluetooth state. Exiting.");
                    return;
                }

                int contentId = ContentType.BLUETOOTH.getContentId();

                if (state == BluetoothAdapter.STATE_ON) {
                    statusesArray[contentId] = BluetoothUtility.BLUETOOTH_ENABLED;

                } else if (state == BluetoothAdapter.STATE_OFF) {
                    statusesArray[contentId] = BluetoothUtility.BLUETOOTH_DISABLED;

                } else if (state == BluetoothAdapter.STATE_TURNING_ON) {
                    statusesArray[contentId] = BluetoothUtility.BLUETOOTH_ENABLING;

                } else if (state == BluetoothAdapter.STATE_TURNING_OFF) {
                    statusesArray[contentId] = BluetoothUtility.BLUETOOTH_DISABLING;
                }
            }
            // ============ Mobile Data Intent Received =====================================================
            else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {

                int mobileDataStatus = MobileDataUtility.isEnabled(context) ? MobileDataUtility.MOBILE_DATA_ENABLED
                        : MobileDataUtility.MOBILE_DATA_DISABLED;

                int contentId = ContentType.MOBILE_DATA.getContentId();
                if (mobileDataStatus != statusesArray[contentId]) {
                    statusesArray[contentId] = mobileDataStatus;

                } else {
                    shouldRedrawUi = false;
                }
            }
            // ============ Ringer Mode Intent Received =====================================================
            else if (AudioManager.RINGER_MODE_CHANGED_ACTION.equals(action)) {
                
                int ringerMode = intent.getIntExtra(AudioManager.EXTRA_RINGER_MODE, unknown);
                
                if (ringerMode == unknown) {
                    LogUtil.w(TAG, "Empty extra for ringer mode state. Exiting.");
                    return;
                }
                
                int contentId = ContentType.RINGER_MODE.getContentId();
                statusesArray[contentId] = ringerMode;
            }
            // ============ Airplane Mode Intent Received ===================================================
            else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {

                boolean isEnabled = intent.getBooleanExtra(AirplaneModeUtility.EXTRA_AIRPLANE_MODE, false);
                if (!isEnabled) {
                    isEnabled = AirplaneModeUtility.isAirplaneModeOn(context) ? true : false;
                }

                int contentId = ContentType.AIRPLANE_MODE.getContentId();
                statusesArray[contentId] = isEnabled ? AirplaneModeUtility.AIRPLANE_MODE_ENABLED
                        : AirplaneModeUtility.AIRPLANE_MODE_DISABLED;
            }
            // ============ Auto Sync Intent Received =======================================================
            else if (AutoSyncUtility.ACTION_CHANGE_AUTO_SYNC_STATE.equals(action)) {

                int contentId = ContentType.AUTO_SYNC.getContentId();
                statusesArray[contentId] = AutoSyncUtility.isEnabled(context) ? AutoSyncUtility.AUTO_SYNC_ENABLED
                        : AutoSyncUtility.AUTO_SYNC_DISABLED;
            }
            // ============ WiFi Hotspot Intent Received ====================================================
            else if (WiFiHotspotUtility.ACTION_CHANGE_WIFI_HOTSPOT_STATE.equals(action)) {

                int contentId = ContentType.WIFI_HOTSPOT.getContentId();
                statusesArray[contentId] = WiFiHotspotUtility.isEnabled(context) ? WiFiHotspotUtility.WIFI_HOTSPOT_ENABLED
                        : WiFiHotspotUtility.WIFI_HOTSPOT_DISABLED;
            }
            // ============ Brightness Intent Received ======================================================
            else if (BrightnessUtility.ACTION_CHANGE_BRIGHTNESS_STATE.equals(action)) {

                int contentId = ContentType.BRIGHTNESS_MODE.getContentId();
                statusesArray[contentId] = BrightnessUtility.getBrightnessMode(context);
            }            
            // ============ Auto-Rotate Intent Received =====================================================
            else if (AutoRotateUtility.ACTION_CHANGE_AUTO_ROTATE_STATE.equals(action)) {

                boolean isautoRotateEnabled = AutoRotateUtility.isAutoRotateEnabled(context);
                int autoRotateMode = isautoRotateEnabled ? AutoRotateUtility.AUTO_ROTATE_ENABLED
                        : AutoRotateUtility.AUTO_ROTATE_DISABLED;

                int contentId = ContentType.AUTO_ROTATE.getContentId();
                statusesArray[contentId] = autoRotateMode;
            }
            // ============ GPS Intent Received =============================================================
            else if (GpsUtility.ACTION_CHANGE_GPS_STATE.equals(action)) {

                int contentId = ContentType.GPS.getContentId();
                boolean isGpsEnabled = GpsUtility.isGpsEnabled(context);
                statusesArray[contentId] = isGpsEnabled ? GpsUtility.GPS_ENABLED : GpsUtility.GPS_DISABLED;
            }
            // ============ Flash Torch Intent Received =====================================================
            else if (FlashTorchUtility.ACTION_CHANGE_FLASH_TORCH.equals(action)) {
                
//                if (!FlashTorchUtility.isFlashLightAccessible(context)) {
//                    LogUtil.w(TAG, "Empty extra for flash torchstate.");
//                    Toast.makeText(context, R.string.FlashLightNotSupportedToast, Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                int state = intent.getIntExtra(FlashTorchUtility.EXTRA_CHANGE_FLASH_TORCH, unknown);
//                if (state == unknown) {
//                    LogUtil.w(TAG, "Empty extra for flash torchstate.");
//                    state = FlashTorchUtility.isFlashLightEnabled(context) ? FlashTorchUtility.FLASH_TORCH_ENABLED
//                            : FlashTorchUtility.FLASH_TORCH_DISABLED;
//                }
//
//                boolean enableFlashTorch = (state == FlashTorchUtility.FLASH_TORCH_ENABLED) ? false : true;
//                state = enableFlashTorch ? FlashTorchUtility.FLASH_TORCH_ENABLED
//                        : FlashTorchUtility.FLASH_TORCH_DISABLED;
//
//                boolean outcome = FlashTorchUtility.setFlashLightEnabled(context, enableFlashTorch);
//
//                if (outcome) {
//                    int contentId = ContentType.FLASH_TORCH.getContentId();
//                    statusesArray[contentId] = state;
//
//                } else {
//                    Toast.makeText(context, R.string.CameraAlreadyInUseToast, Toast.LENGTH_LONG).show();
//                    shouldRedrawUi = false;
//                }
            }
            // ============ Not Supported Action ============================================================
            else {
                LogUtil.w(TAG, "This action is currently not supported!");
                shouldRedrawUi = false;
            }

            if (shouldRedrawUi) {
                statusesPersistence.setContentStates(context, statusesArray);
                WidgetUiServiceFacade.get().startForSimpleRedraw(context);                
            }

        } else {
            LogUtil.w(TAG, "Exiting upon bad intent.");
        }

    }

}
