
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

package com.obaralic.toggler.utilities.values;

import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.obaralic.toggler.R;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the flash torch.
 */
public class LedFlashUtility {

    private static final String TAG = LogUtil.getTag(LedFlashUtility.class);

    /**
     * Flash torch disabled.
     */
    public static final int FLASH_TORCH_DISABLED = 0;

    /**
     * Flash torch enabled.
     */
    public static final int FLASH_TORCH_ENABLED = 1;

    /**
     * Flash torch unknown.
     */
    public static final int FLASH_TORCH_UNKNOWN = -1;

    /**
     * Camera used for enabling and disabling led flash.
     */
    public static Camera sCamera;

    /**
     * Surface holder used for attaching to the camera.
     */
    public static SurfaceHolder sHolder;

    /**
     * Check if the LED flash torch is enabled.
     */
    public static boolean isFlashLightEnabled(Context context) {

        if (!isFlashLightAccessible(context)) {
            return false;
        }

        boolean isEnabled = false;

        try {
            sCamera = Camera.open();

            if (sCamera == null) {
                LogUtil.e(TAG, "Device does not have back side camera!");
                return false;
            }

            Parameters parameters = sCamera.getParameters();
            String flashMode = parameters.getFlashMode();
            isEnabled = Parameters.FLASH_MODE_TORCH.equals(flashMode);
            sCamera.release();
            sCamera = null;

        } catch (Exception e) {
            LogUtil.e(TAG, "Camera is in use." + LogUtil.getStackTraceString(e));
            isEnabled = true;
        }

        return isEnabled;
    }

    /**
     * Check if the LED flash torch is accessible.
     */
    public static boolean isFlashLightAccessible(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * Enables LED flash torch.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param callback
     *            The {@link Callback} object used for attaching to the {@link SurfaceHolder}.
     * @param surfaceView
     *            The {@link SurfaceView} used for accessing LED flash torch.
     */
    public static void enableLED(Context context, Callback callback, SurfaceView surfaceView) {

        try {
            sHolder = surfaceView.getHolder();
            sHolder.addCallback(callback);
            sCamera = Camera.open();
            sCamera.setPreviewDisplay(sHolder);

        } catch (IOException e) {
            LogUtil.w(TAG, LogUtil.getStackTraceString(e));
            return;
        }

        PackageManager packageManager = context.getPackageManager();

        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            LogUtil.e(TAG, "Device has no camera!");
            Toast.makeText(context, R.string.FlashLightNotSupportedToast, Toast.LENGTH_SHORT).show();
            return;
        }

        Parameters params = sCamera.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        sCamera.setParameters(params);
        sCamera.startPreview();
    }

    /**
     * Updates surface holder when the new one is created.
     */
    public static void updateHolder(SurfaceHolder surfaceHolder) {
        try {
            sHolder = surfaceHolder;
            sCamera.setPreviewDisplay(sHolder);

        } catch (IOException e) {
            LogUtil.w(TAG, LogUtil.getStackTraceString(e));
        }
    }

    /**
     * Disables LED flash torch.
     */
    public static void disableLED() {
        if (sCamera != null) {
            Parameters params = sCamera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            sCamera.setParameters(params);
            sCamera.stopPreview();
            sCamera.release();
        }
    }

}
