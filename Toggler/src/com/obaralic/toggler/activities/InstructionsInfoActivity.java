
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

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.obaralic.toggler.R;
import com.obaralic.toggler.activities.base.BaseActivity;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Represents the widgets instructions screen.
 */
public class InstructionsInfoActivity extends BaseActivity {

    private static final String TAG = LogUtil.getTag(InstructionsInfoActivity.class);

    /**
     * The {@link TextView} that represents hyperlink for launching license agreement online page.
     */
    private TextView mHyperlinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.instructions_info_layout);

        initializeViews();

        initializeOnClickListeners();

        // Analytics
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_INSTRUCTIONS_ACTIVITY_OPENED, true);
    }

    /**
     * Method used to initialize all of the components used in this activity, and to set current
     * application version code stored in the manifest resource file.
     */
    private void initializeViews() {
        this.mHyperlinkTextView = (TextView) findViewById(R.id.toggler_widget_license_TextView);        
        TextView widgetNameTextView = (TextView) findViewById(R.id.toggler_widget_name_TextView);

        // Writing application version stored in manifest
        try {
            String fullWidgetName = getString(R.string.WidgetName);
            fullWidgetName += " "
                    + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            widgetNameTextView.setText(fullWidgetName);

        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.w(TAG, "No package name found.");

        }
    }

    /**
     * Method used to initialize {@link OnClickListener} for every clickable {@link View}.
     */
    private void initializeOnClickListeners() {

        // ========================================================================================
        // OnClickListener on hyper link for redirecting to license agreement page.
        // ========================================================================================
        mHyperlinkTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = (String) mHyperlinkTextView.getTag();
                String cleanedUpUrl = URLUtil.guessUrl(url);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cleanedUpUrl)));
            }
        });

    }
}
