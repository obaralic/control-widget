
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

import java.lang.reflect.Method;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.IBinder;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for reading and writing state of the flash torch.
 * It is much more advisable to use {@link LedFlashUtility} class instead.
 */
@SuppressLint("DefaultLocale")
public class FlashTorchUtility {

    private static final String TAG = LogUtil.getTag(FlashTorchUtility.class);

    /**
     * Constant used as action for changing flash torch state.
     */
    public static final String ACTION_CHANGE_FLASH_TORCH = "com.obaralic.toggler.action.ACTION_CHANGE_FLASH_TORCH";

    /**
     * Constant used for storing flash torch state.
     */
    public static final String EXTRA_CHANGE_FLASH_TORCH = "com.obaralic.toggler.extra.EXTRA_CHANGE_FLASH_TORCH";

    /**
     * Flash torch disabled.
     */
    public static final int FLASH_TORCH_DISABLED = 0;

    /**
     * Flash torch enabled.
     */
    public static final int FLASH_TORCH_ENABLED = 1;

    public static boolean isFlashLightAccessible(Context context) {
        LogUtil.d(TAG, "Called isFlashLightAccessible");

        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private static Camera sCamera;
    private static DroidLED sDroidLED;       
    
    /**
     * Check if flash torch is enabled.
     */
    public static boolean isFlashLightEnabled(Context context) {
        LogUtil.d(TAG, "Called isFlashLightEnabled");

        if (!isFlashLightAccessible(context)) {
            return false;
        }

        boolean isEnabled = false;

        String manuName = android.os.Build.MANUFACTURER.toLowerCase(Locale.US);

        if (manuName.contains("motorola")) {
            try {

                if (sDroidLED == null) {
                    sDroidLED = new DroidLED();
                }

                sDroidLED = new DroidLED();
                isEnabled = sDroidLED.isEnabled();

            } catch (Exception e) {
                LogUtil.e(TAG, LogUtil.getStackTraceString(e));
                isEnabled = true;
            }

        }
        // If not motorola device
        else {

            try {
                sCamera = Camera.open();

                if (sCamera == null) {
                    LogUtil.e(TAG, "Device does not have back side camera!");
                    return false;
                }

                Parameters parameters = sCamera.getParameters();
                String flashMode = parameters.getFlashMode();

                if (Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
                    isEnabled = true;

                } else {
                    isEnabled = false;

                }
                sCamera.release();
                sCamera = null;

            } catch (Exception e) {
                LogUtil.e(TAG, "Camera is in use." + LogUtil.getStackTraceString(e));
                isEnabled = true;
            }
        }

        return isEnabled;
    }

    /**
     * Set flash torch to the given state.
     */
    public static boolean setFlashLightEnabled(Context context, boolean isFlashTorchEnabled) {

        boolean outcome = true;
        try {

            String manuName = android.os.Build.MANUFACTURER.toLowerCase(Locale.US);
            LogUtil.d(TAG, "Called setFlashLightEnabled for " + manuName);

            // If it's Motorola's device
            if (manuName.contains("motorola")) {
                setFlashTorchForMotorolaDevice(context, isFlashTorchEnabled);
            }
            // If it's Samsung's device
            else if (manuName.equalsIgnoreCase("Samsung")) {
                // setFlashTorchForSamsungDevice(context, isFlashTorchEnabled);
                setFlashTorchForDefaultDevice(isFlashTorchEnabled);
            }
            // Other devices
            else {
                setFlashTorchForDefaultDevice(isFlashTorchEnabled);
            }

        } catch (CameraInUseException e) {
            outcome = false;

        } catch (Exception e) {
            outcome = false;
        }

        return outcome;
    }

    // ================================================================================================================
    // Utility Methods For Motorola Devices
    // ================================================================================================================

    private static void setFlashTorchForMotorolaDevice(Context context, boolean isFlashTorchEnabled)
            throws CameraInUseException {
        LogUtil.i(TAG, "If it's Motorola's device");

        try {

            if (sDroidLED == null) {
                sDroidLED = new DroidLED();
            }

            sDroidLED.enableFlashTorch(isFlashTorchEnabled);

        } catch (Exception e) {
            LogUtil.e(TAG, LogUtil.getStackTraceString(e));
        }
    }

    // ================================================================================================================
    // Utility Methods For Other Devices
    // ================================================================================================================

    private static void setFlashTorchForDefaultDevice(boolean isFlashTorchEnabled)
            throws CameraInUseException {
        LogUtil.i(TAG, "If it's some other device");
        try {

            if (isFlashTorchEnabled) {
                initCamera();
                setCameraParameter(Camera.Parameters.FLASH_MODE_TORCH);

            } else {
                setCameraParameter(Camera.Parameters.FLASH_MODE_OFF);
                releaseCamera();

            }

        } catch (Exception e) {
            LogUtil.e(TAG, "Camera is in use." + LogUtil.getStackTraceString(e));
            throw new CameraInUseException();
        }
    }

    private static void initCamera() {
        if (sCamera == null) {
            sCamera = Camera.open();
            sCamera.startPreview();
        }
    }

    private static void releaseCamera() {
        if (sCamera != null) {
            sCamera.stopPreview();
            sCamera.release();
        }

        sCamera = null;
    }

    private static void setCameraParameter(String value) {
        if (sCamera != null) {
            Camera.Parameters params = sCamera.getParameters();
            params.setFlashMode(value);
            sCamera.setParameters(params);
        }
    }

    // ================================================================================================================
    // Utility Class
    // ================================================================================================================

    private static class DroidLED {

        private Object svc = null;
        private Method getFlashlightEnabled = null;
        private Method setFlashlightEnabled = null;

        public DroidLED() throws Exception {
            try {
                // call ServiceManager.getService("hardware") to get an IBinder for the service.
                // this appears to be totally undocumented and not exposed in the SDK whatsoever.
                Class<?> serviceManager = Class.forName("android.os.ServiceManager");
                Object hardwareBinder = serviceManager.getMethod("getService", String.class).invoke(null,
                        "hardware");

                // get the hardware service stub. this seems to just get us one step closer to the proxy
                Class<?> hardwareServiceStub = Class.forName("android.os.IHardwareService$Stub");
                Method asInterface = hardwareServiceStub.getMethod("asInterface", android.os.IBinder.class);
                svc = asInterface.invoke(null, (IBinder) hardwareBinder);

                // grab the class (android.os.IHardwareService$Stub$Proxy) so we can reflect on its methods
                Class<?> proxy = svc.getClass();

                // save methods
                getFlashlightEnabled = proxy.getMethod("getFlashlightEnabled");
                setFlashlightEnabled = proxy.getMethod("setFlashlightEnabled", boolean.class);

            } catch (Exception e) {
                throw new Exception("LED could not be initialized");
            }
        }

        public boolean isEnabled() {
            try {
                return getFlashlightEnabled.invoke(svc).equals(true);
            } catch (Exception e) {
                return false;
            }
        }

        public void enableFlashTorch(boolean enableFlashTorch) {
            try {
                setFlashlightEnabled.invoke(svc, enableFlashTorch);
            } catch (Exception e) {
            }
        }

    }

    public static class CameraInUseException extends Exception {

        private static final long serialVersionUID = 1L;

        public CameraInUseException() {

        }

    }

}
