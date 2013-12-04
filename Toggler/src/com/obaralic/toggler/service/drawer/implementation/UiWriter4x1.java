
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

package com.obaralic.toggler.service.drawer.implementation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.obaralic.toggler.R;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;
import com.obaralic.toggler.service.WidgetUiServiceFacade.Actions;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.resolvers.DrawablesResolver;
import com.obaralic.toggler.utilities.resolvers.ResourceIdsResolver;
import com.obaralic.toggler.utilities.values.AirplaneModeUtility;
import com.obaralic.toggler.utilities.values.AutoRotateUtility;
import com.obaralic.toggler.utilities.values.AutoSyncUtility;
import com.obaralic.toggler.utilities.values.BluetoothUtility;
import com.obaralic.toggler.utilities.values.BrightnessUtility;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.GpsUtility;
import com.obaralic.toggler.utilities.values.MobileDataUtility;
import com.obaralic.toggler.utilities.values.RingerModeUtitliy;
import com.obaralic.toggler.utilities.values.WiFiHotspotUtility;
import com.obaralic.toggler.utilities.values.WiFiUtility;

/**
 * Class used for drawing content on widget instance 4x1.
 */
public class UiWriter4x1 {

    private static final String TAG = LogUtil.getTag(UiWriter4x1.class);

    private static UiWriter4x1 sInstance;

    protected UiWriter4x1() {
    }

    /**
     * Retrieves singleton instance of the {@link UiWriter4x1} object.
     */
    public static UiWriter4x1 getsInstance() {
        if (sInstance == null) {
            sInstance = new UiWriter4x1();
        }
        return sInstance;
    }

    /**
     * Used for drawing content on the widget instance 4x1.
     */
    public void draw(Context context, InstanceInfoBean instanceInfoBean, RemoteViews remoteViews) {
        LogUtil.d(TAG, "Called draw for 4x1 widget with: " + instanceInfoBean);

        // Change skin.
        int widgetBackgroundResourceId = DrawablesResolver
                .getBackgroundBackgroundId(instanceInfoBean.mSkinType);
        remoteViews.setInt(R.id.widget_holder_LinearLayout, "setBackgroundResource",
                widgetBackgroundResourceId);

        // Attaching appropriate field selector
        int selectorId = DrawablesResolver.getFaceBackgroundSelector();
        int appWidgetId = instanceInfoBean.mAppWidgetId;

        // Attaching onClickListener to the first toggle content
        remoteViews.setOnClickPendingIntent(R.id.toggler_content_ImageView_1,
                getOnClickPendingIntent(context, FieldType.ONE, appWidgetId));
        remoteViews.setInt(R.id.toggler_content_ImageView_1, "setBackgroundResource", selectorId);

        // Attaching onClickListener to the second toggle content
        remoteViews.setOnClickPendingIntent(R.id.toggler_content_ImageView_2,
                getOnClickPendingIntent(context, FieldType.TWO, appWidgetId));
        remoteViews.setInt(R.id.toggler_content_ImageView_2, "setBackgroundResource", selectorId);

        // Attaching onClickListener to the third toggle content
        remoteViews.setOnClickPendingIntent(R.id.toggler_content_ImageView_3,
                getOnClickPendingIntent(context, FieldType.THREE, appWidgetId));
        remoteViews.setInt(R.id.toggler_content_ImageView_3, "setBackgroundResource", selectorId);

        // Attaching onClickListener to the fourth toggle content
        remoteViews.setOnClickPendingIntent(R.id.toggler_content_ImageView_4,
                getOnClickPendingIntent(context, FieldType.FOUR, appWidgetId));
        remoteViews.setInt(R.id.toggler_content_ImageView_4, "setBackgroundResource", selectorId);

        // Attaching onClickListener to the fifth toggle content
        remoteViews.setOnClickPendingIntent(R.id.toggler_content_ImageView_5,
                getOnClickPendingIntent(context, FieldType.FIVE, appWidgetId));
        remoteViews.setInt(R.id.toggler_content_ImageView_5, "setBackgroundResource", selectorId);

        // Drawing widget's UI
        int length = instanceInfoBean.mWidgetContents.size();
        for (int index = 0; index < length; index++) {
            ContentType contentType = instanceInfoBean.mWidgetContents.get(index);
            if (contentType == null) {
                continue;
            }
            drawField(context, remoteViews, contentType, FieldType.getFieldType(index + 1),
                    instanceInfoBean.mWidgetSizeType);
        }
    }

    /**
     * Utility method used for setting content specific to every widget instance.
     */
    private void drawField(Context context, RemoteViews remoteViews, ContentType contentType,
            FieldType fieldType, WidgetSizeType sizeType) {

        int[] contentStatesValues = ContentStatesPersistence.getInstance().getContentStates(context);
        int contentState = contentStatesValues[contentType.getContentId()];

        int iconResourceId = ResourceIdsResolver.getImageDrawableId(sizeType, fieldType);
        int iconDrawableId = DrawablesResolver.getFaceIconDrawableId(contentType, contentState);
        remoteViews.setImageViewResource(iconResourceId, iconDrawableId);

        switch (contentType) {
        // ========== Redrawing widget field containing WiFi toggle switch ==================================
        case WIFI:

            if (contentState == WiFiUtility.WIFI_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == WiFiUtility.WIFI_DISABLED) {
                disableField(context, remoteViews, fieldType);

            } else if (contentState == WiFiUtility.WIFI_ENABLING) {
                transitionOffOnField(context, remoteViews, fieldType);

            } else if (contentState == WiFiUtility.WIFI_DISABLING) {
                transitionOnOffField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Mobile Data toggle switch ===========================
        case MOBILE_DATA:

            if (contentState == MobileDataUtility.MOBILE_DATA_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == MobileDataUtility.MOBILE_DATA_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Bluetooth toggle switch =============================
        case BLUETOOTH:

            if (contentState == BluetoothUtility.BLUETOOTH_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == BluetoothUtility.BLUETOOTH_DISABLED) {
                disableField(context, remoteViews, fieldType);

            } else if (contentState == BluetoothUtility.BLUETOOTH_ENABLING) {
                transitionOffOnField(context, remoteViews, fieldType);

            } else if (contentState == BluetoothUtility.BLUETOOTH_DISABLING) {
                transitionOnOffField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing GPS toggle switch ===================================
        case GPS:

            if (contentState == GpsUtility.GPS_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == GpsUtility.GPS_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Auto Sync toggle switch =============================
        case AUTO_SYNC:

            if (contentState == AutoSyncUtility.AUTO_SYNC_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == AutoSyncUtility.AUTO_SYNC_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;

        // ========== Redrawing widget field containing Settings switch =====================================
        case SETTINGS:

            //hideStatusBar(context, remoteViews, fieldType);

            break;
        // ========== Redrawing widget field containing Brightness states switch ============================
        case BRIGHTNESS_MODE:

            if (contentState == BrightnessUtility.BRIGHTNESS_AUTO) {
                transitionState2(context, remoteViews, fieldType);

            } else if (contentState == BrightnessUtility.BRIGHTNESS_LOW) {
                disableField(context, remoteViews, fieldType);

            } else if (contentState == BrightnessUtility.BRIGHTNESS_MEDIUM) {
                transitionState1(context, remoteViews, fieldType);

            } else if (contentState == BrightnessUtility.BRIGHTNESS_HIGH) {
                enableField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Ringing Mode states switch ==========================
        case RINGER_MODE:

            if (contentState == RingerModeUtitliy.RING_MODE_NORMAL) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == RingerModeUtitliy.RING_MODE_SILENT) {
                disableField(context, remoteViews, fieldType);

            } else if (contentState == RingerModeUtitliy.RING_MODE_VIBRATE) {
                transitionState1(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Airplane Mode states switch =========================
        case AIRPLANE_MODE:

            if (contentState == AirplaneModeUtility.AIRPLANE_MODE_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == AirplaneModeUtility.AIRPLANE_MODE_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing WiFi Hotspot states switch ==========================
        case WIFI_HOTSPOT:

            if (contentState == WiFiHotspotUtility.WIFI_HOTSPOT_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == WiFiHotspotUtility.WIFI_HOTSPOT_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;

        // ========== Redrawing widget field containing Flash Torch states switch ===========================
        case FLASH_TORCH:

            if (contentState == FlashTorchUtility.FLASH_TORCH_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == FlashTorchUtility.FLASH_TORCH_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;
        // ========== Redrawing widget field containing Flash Torch states switch ===========================
        case AUTO_ROTATE:

            if (contentState == AutoRotateUtility.AUTO_ROTATE_ENABLED) {
                enableField(context, remoteViews, fieldType);

            } else if (contentState == AutoRotateUtility.AUTO_ROTATE_DISABLED) {
                disableField(context, remoteViews, fieldType);
            }

            break;
        default:

        }

    }

    // ======================= Utility enable/disable methods ===============================================

    private void enableField(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called enableField");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_on_blue);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

    private void disableField(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called disableField");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_off);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

    private void transitionOnOffField(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called transitionOnOffField");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_transient);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

    private void transitionOffOnField(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called transitionOffOnField");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_transient);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

    private void transitionState1(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called transitionState1");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_transient);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

    private void transitionState2(Context context, RemoteViews remoteViews, FieldType fieldType) {
        LogUtil.d(TAG, "Called transitionState2");

        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
        remoteViews.setImageViewResource(statusBarId, R.drawable.toggle_state_2);
        remoteViews.setViewVisibility(statusBarId, View.VISIBLE);
    }

//    private void hideStatusBar(Context context, RemoteViews remoteViews, FieldType fieldType) {
//        LogUtil.d(TAG, "Called hideStatusBar");
//
//        int statusBarId = ResourceIdsResolver.getStatusBarDrawableId(WidgetSizeType.FIVE, fieldType);
//        remoteViews.setViewVisibility(statusBarId, View.GONE);
//    }

    /**
     * Creates {@link PendingIntent} for performing click on one of the fields on this widget.
     */
    private PendingIntent getOnClickPendingIntent(Context context, FieldType fieldType, int appWidgetId) {
        Intent startServiceIntent = new Intent(Actions.getOnClickAction(WidgetSizeType.FIVE, fieldType));
        startServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return PendingIntent.getService(context, appWidgetId, startServiceIntent, 0);
    }

}
