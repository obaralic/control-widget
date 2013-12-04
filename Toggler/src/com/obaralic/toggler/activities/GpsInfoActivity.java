
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

package com.obaralic.toggler.activities;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.Window;

import com.obaralic.toggler.R;
import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.utilities.persistence.implementation.GpsEnablingInfoPersistance;
import com.obaralic.toggler.utilities.values.GpsUtility;

/**
 * Represents the dialog based screen used for GPS toggle purpose.
 */
public class GpsInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transparent_layout);        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        createDialog().show();
    }
    
    private Builder createDialog() {
        
        Builder builder = new Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(R.string.GpsInfoScreenTitle);         
        builder.setMessage(R.string.GpsInfoMessage);
        builder.setCancelable(true);
        
        builder.setPositiveButton(R.string.No, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                GpsEnablingInfoPersistance persistance = GpsEnablingInfoPersistance.getInstance();
                persistance.setGpsInfoDialog(GpsInfoActivity.this, false);
                GpsUtility.launchGpsSettingsPage(getBaseContext());
                finish();
            }
        });
        
        builder.setNegativeButton(R.string.Yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                GpsUtility.launchGpsSettingsPage(getBaseContext());
                finish();
            }
        });

        builder.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();

            }
        });       
        
        return builder;
    }
   
}