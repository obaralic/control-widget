
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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.obaralic.toggler.R;
import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.activities.fragments.GpsInfoFragment;
import com.obaralic.toggler.activities.fragments.GpsInfoFragment.OnButtonClickListener;
import com.obaralic.toggler.utilities.persistence.implementation.GpsEnablingInfoPersistance;
import com.obaralic.toggler.utilities.values.GpsUtility;

/**
 * Represents the dialog based screen used for GPS toggle purpose.
 */
public class GpsInfoActivity extends BaseActivity implements OnButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
    }

    @Override
    public void doPositiveClick() {
        GpsEnablingInfoPersistance persistance = GpsEnablingInfoPersistance.getInstance();
        persistance.setGpsInfoDialog(GpsInfoActivity.this, false);
        GpsUtility.launchGpsSettingsPage(getBaseContext());
        finish();
    }

    @Override
    public void doNegativeClick() {
        GpsUtility.launchGpsSettingsPage(getBaseContext());
        finish();
    }

    private void showDialog() {
        final int iconId = R.drawable.ic_launcher;
        final int titleId = R.string.GpsInfoScreenTitle;
        final int messageId = R.string.GpsInfoMessage;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            showFragmentDialog(iconId, titleId, messageId);
        } else {
            showAlertDialog(iconId, titleId, messageId);
        }
    }

    private void showAlertDialog(int iconId, int titleId, int messageId) {
        Builder builder = new Builder(this);
        builder.setIcon(iconId);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setCancelable(true);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                doPositiveClick();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                doNegativeClick();
            }
        });

        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        builder.show();
    }

    private void showFragmentDialog(int iconId, int titleId, int messageId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment prev = manager.findFragmentByTag(GpsInfoFragment.TAG);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);

        DialogFragment fragment = GpsInfoFragment.newInstance(iconId, titleId, messageId);
        fragment.show(transaction, GpsInfoFragment.TAG);
    }
}