
/*
 * Copyright 2013 oBaralic, Ltd. (owner Zivorad Baralic)
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

package com.obaralic.toggler.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.obaralic.toggler.R;
import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.values.FlashTorchUtility;
import com.obaralic.toggler.utilities.values.LedFlashUtility;

/**
 * {@link Activity} class used for displaying flash torch control progress bar. Main purpose of this class is
 * attaching {@link SurfaceView} with layout.
 */
public class FlashTorchControlActivity extends BaseActivity implements Callback {

    private static final String TAG = LogUtil.getTag(FlashTorchControlActivity.class);

    public static final String EXTRA_FLASH_TORCH_STATE = "com.obaralic.toggler.extra.EXTRA_FLASH_TORCH_STATE";
    
    public static final String EXTRA_USE_FIX = "com.obaralic.toggler.extra.EXTRA_USE_FIX";

    private SurfaceView mPreview;
    
    private RelativeLayout mLedLayout;
    
    private ImageView mLedIcon;
    
    private boolean mShouldUseFix;
    
    private int mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.flash_torch_control_layout);
        
        mPreview = (SurfaceView) findViewById(R.id.flash_torch_surface_view);
        
        mState = getIntent().getIntExtra(EXTRA_FLASH_TORCH_STATE, LedFlashUtility.FLASH_TORCH_UNKNOWN);
        mShouldUseFix = getIntent().getBooleanExtra(EXTRA_USE_FIX, false);               

        if (mShouldUseFix) {
            performFixOperation();

        } else {
            performRegularOperation();
        }        
    }
    
    /**
     * Perform fix operation in order to enable LED.
     */
    private void performFixOperation() {
        mLedLayout = (RelativeLayout) findViewById(R.id.flash_torch_transparent_layout);
        mLedIcon = (ImageView) findViewById(R.id.ledFlashIcon);
                    
        mLedLayout.setBackgroundResource(R.drawable.radial_background_blue);
        mLedIcon.setVisibility(View.VISIBLE);
        
        if (LedFlashUtility.isFlashLightEnabled(this)) {
            LedFlashUtility.disableLED();
        }
        
        LedFlashUtility.enableLED(this, this, mPreview);
        
        notifyChange(LedFlashUtility.FLASH_TORCH_ENABLED);
    }
    
    /**
     * Perform regular change of LED state according to the received data.
     */
    private void performRegularOperation() {
        
        if (mState == LedFlashUtility.FLASH_TORCH_UNKNOWN) {
            finish();
            return;
        }

        if (mState == FlashTorchUtility.FLASH_TORCH_ENABLED) {
            LedFlashUtility.enableLED(this, this, mPreview);

        } else {
            LedFlashUtility.disableLED();
        }
        
        notifyChange(mState);
    }

    /**
     * Notify state change and redraw widget face.
     */
    private void notifyChange(int state) {
        ContentStatesPersistence statusesPersistence = ContentStatesPersistence.getInstance();
        int[] statusesArray = statusesPersistence.getContentStates(this);
        int contentId = ContentType.FLASH_TORCH.getContentId();
        statusesArray[contentId] = state;
        statusesPersistence.setContentStates(this, statusesArray);

        WidgetUiServiceFacade.get().startForSimpleRedraw(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mShouldUseFix) {
            finish();            
        }
    }
    
    @Override
    protected void onPause() {       
        super.onPause();
        if (mShouldUseFix) {
            LedFlashUtility.disableLED();
            notifyChange(LedFlashUtility.FLASH_TORCH_DISABLED);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.d(TAG, "Called surfaceCreated");
        LedFlashUtility.updateHolder(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.d(TAG, "Called surfaceDestroyed");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtil.d(TAG, "Called surfaceChanged");
    }

}
