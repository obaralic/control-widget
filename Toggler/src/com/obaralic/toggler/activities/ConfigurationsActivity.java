
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

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obaralic.toggler.R;
import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory;
import com.obaralic.toggler.utilities.analytics.AnalyticsFactory.AnalyticsProviderType;
import com.obaralic.toggler.utilities.analytics.AnalyticsInterface;
import com.obaralic.toggler.utilities.analytics.events.FlurryCustomEvents;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.general.DrawContentBean;
import com.obaralic.toggler.utilities.gui.ContentTypeListAdapter;
import com.obaralic.toggler.utilities.gui.DrawContentListUtility;
import com.obaralic.toggler.utilities.gui.SkinThumbnailsSelector;
import com.obaralic.toggler.utilities.persistence.implementation.AppWidgetIdPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ClickedFieldTypePersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ContentStatesPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.GpsEnablingInfoPersistance;
import com.obaralic.toggler.utilities.persistence.implementation.LedFlashEnablingInfoPersistence;
import com.obaralic.toggler.utilities.persistence.implementation.ShowingActivityPersistance;
import com.obaralic.toggler.utilities.resolvers.DrawablesResolver;
import com.obaralic.toggler.utilities.resolvers.StringsResolver;

/**
 * Represents configurations screen displayed when the user enters into widget's configuration mode.
 */
public class ConfigurationsActivity extends ListActivity {

    private static final String TAG = LogUtil.getTag(ConfigurationsActivity.class);

    private RelativeLayout mBackgroundSkinsRelativeLayout;

    private RelativeLayout mGlassSkinRelativeLayout;

    private RelativeLayout mBlueSkinRelativeLayout;

    private RelativeLayout mGradSkinRelativeLayout;

    private RelativeLayout mWoodSkinRelativeLayout;

    private RelativeLayout mContentInfoRelativeLayout;

    private LinearLayout mGpsInfoSeparatorLinearLayout;

    private ListView mContentListView;

    private ContentTypeListAdapter mContentTypeListAdapter;

    private TextView mLicenseAgreementTextView;

    private TextView mContentInfoLabel;

    private CheckBox mContentInfoCheckBox;

    private SkinThumbnailsSelector mSkinThumbnailsSelector;

    private FieldType mFieldType;

    private InstanceInfoBean mInstanceInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        initializeInstanceInfo();

        initializeViews();

        initializeListView();

        initializeOnClickListeners();

        // Send analytics event.
        AnalyticsInterface analytics = AnalyticsFactory.get(AnalyticsProviderType.FLURRY_ANALYTICS);
        analytics.sendEvent(FlurryCustomEvents.ON_CONFIGURATIONS_ACTIVITY_OPENED, true);
    }

    /**
     * Method used to initialize {@link InstanceInfoBean} for this widget.
     */
    private void initializeInstanceInfo() {
        int appWidgetId = AppWidgetIdPersistence.getInstance().getAppWidgetId(this);
        LogUtil.d(TAG, "Configuration for the widget ID: " + appWidgetId);

        InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
        mInstanceInfoBean = dao.getInstanceInfo(this, appWidgetId);

        int fieldPosition = ClickedFieldTypePersistence.getInstance().getWidgetFieldOrder(getBaseContext());
        mFieldType = FieldType.getFieldType(fieldPosition);
    }

    /**
     * Method used to initialize all of the components used in this activity, and to set current application
     * version code stored in the manifest resource file.
     */
    private void initializeViews() {
        mContentListView = (ListView) findViewById(android.R.id.list);
        mBackgroundSkinsRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundSkins_RelativeLayout);
        mBlueSkinRelativeLayout = (RelativeLayout) findViewById(R.id.skinBlue_RelativeLayout);
        mGlassSkinRelativeLayout = (RelativeLayout) findViewById(R.id.skinTransparent_RelativeLayout);
        mGradSkinRelativeLayout = (RelativeLayout) findViewById(R.id.skinGrey_RelativeLayout);
        mWoodSkinRelativeLayout = (RelativeLayout) findViewById(R.id.skinWood_RelativeLayout);
        mLicenseAgreementTextView = (TextView) findViewById(R.id.toggler_widget_license_TextView);
        mContentInfoRelativeLayout = (RelativeLayout) findViewById(R.id.contentInfo_RelativeLayout);
        mGpsInfoSeparatorLinearLayout = (LinearLayout) findViewById(R.id.gpsInfoSeparator);
        mContentInfoCheckBox = (CheckBox) findViewById(R.id.contentInfo_CheckBox);
        mContentInfoLabel = (TextView) findViewById(R.id.contentInfoLabel_TextView);

        mSkinThumbnailsSelector = new SkinThumbnailsSelector(mBackgroundSkinsRelativeLayout,
                mInstanceInfoBean);

        TextView batteryWidgetTextView = (TextView) findViewById(R.id.toggler_widget_name_TextView);

        // Writing application version stored in AndroidManifest.xml
        try {
            String batteryWidgetString = getString(R.string.WidgetName);
            batteryWidgetString += " " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            batteryWidgetTextView.setText(batteryWidgetString);

        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.w(TAG, "No package name found.");
        }
    }

    /**
     * Method used to initialize supported toggle features in the {@link ListView}.
     */
    private void initializeListView() {
        fillListView();
    }

    /**
     * Method used to initialize {@link OnClickListener} for every clickable {@link View}.
     */
    private void initializeOnClickListeners() {

        // ==================================================================================================
        // OnClickListener on each of three skin ImageViews for framing selected skin.
        // ==================================================================================================
        mGradSkinRelativeLayout.setOnClickListener(mSkinThumbnailsSelector);
        mBlueSkinRelativeLayout.setOnClickListener(mSkinThumbnailsSelector);
        mGlassSkinRelativeLayout.setOnClickListener(mSkinThumbnailsSelector);
        mWoodSkinRelativeLayout.setOnClickListener(mSkinThumbnailsSelector);

        // ==================================================================================================
        // OnClickListener on hyperlink for redirecting to license agreement page.
        // ==================================================================================================
        mLicenseAgreementTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = (String) mLicenseAgreementTextView.getTag();
                String cleanedUpUrl = URLUtil.guessUrl(url);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cleanedUpUrl)));
            }
        });

        // ==================================================================================================
        // OnCheckedChangeListener on gps info check box for showing info dialog.
        // ==================================================================================================
        mContentInfoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentType contentType = mInstanceInfoBean.mWidgetContents.get(mFieldType.getFieldIndex());

                if (ContentType.GPS == contentType) {
                    GpsEnablingInfoPersistance persistance = GpsEnablingInfoPersistance.getInstance();
                    persistance.setGpsInfoDialog(getBaseContext(), isChecked);

                } else if (ContentType.FLASH_TORCH == contentType) {
                    LedFlashEnablingInfoPersistence persistence = LedFlashEnablingInfoPersistence.getInstance();
                    persistence.setLedFlashFixInUse(getBaseContext(), isChecked);
                }
            }
        });

        // ==================================================================================================
        // OnItemClickListener on ListView for reconnecting paired headset device.
        // ==================================================================================================
        mContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                DrawContentBean selectedDrawContentBean = DrawContentListUtility.getInstance().get((int) id);

                if (selectedDrawContentBean != null) {

                    // Clear all features, and select pressed feature
                    for (DrawContentBean bean : DrawContentListUtility.getInstance()) {
                        bean.setContentEnabled(false);
                    }

                    selectedDrawContentBean.setContentEnabled(true);

                    // Update persistence so that widget shows pressed feature
                    ContentType selectedContentType = selectedDrawContentBean.getContentType();
                    mInstanceInfoBean.mWidgetContents.set(mFieldType.getFieldIndex(), selectedContentType);
                    InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();

                    int appWidgetId = mInstanceInfoBean.mAppWidgetId;
                    dao.updateInstanceInfo(getBaseContext(), appWidgetId, selectedContentType, mFieldType);
                    mContentListView.invalidateViews();

                } else {
                    LogUtil.e(TAG, "Selected feature item is null, this should never happen!");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Save in persistence that activity is currently displaying
        ShowingActivityPersistance.getInstance().setActivityShowing(this, true);

        // Show Gps Info Section if selected feature is GPS toggle
        showContentInfoLayout();

        // Restore internal state.
        mSkinThumbnailsSelector.invalidateViews(this, mInstanceInfoBean.mSkinType);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save in persistence that activity is no longer displayed
        ShowingActivityPersistance.getInstance().setActivityShowing(this, false);

        // Redraw widget's face
        WidgetUiServiceFacade.get().startForSimpleRedraw(getBaseContext());
    }

    /**
     * Populate list view with all supported toggle features
     */
    private void fillListView() {
        LogUtil.d(TAG, "Called fillListView");

        int[] contentStatesArray = ContentStatesPersistence.getInstance().getContentStates(getBaseContext());

        mContentTypeListAdapter = new ContentTypeListAdapter(ConfigurationsActivity.this,
                R.layout.row_toggle_item, getListContent(contentStatesArray));

        mContentListView.setAdapter(mContentTypeListAdapter);
    }

    /**
     * Retrieves exclusive content, so that widget can contains multiple contents of the same type.
     */
    private List<DrawContentBean> getListContent(int[] contentStatesArray) {

        DrawContentListUtility.getInstance().clear();
        List<DrawContentBean> contentTypeList = DrawContentListUtility.getInstance();

        ContentType selectedContent = mInstanceInfoBean.mWidgetContents.get(mFieldType.getFieldIndex());

        int contentCount = ContentType.getContentCount();

        for (int contentId = 0; contentId < contentCount; contentId++) {
            ContentType contentType = ContentType.getContentType(contentId);

            int drawableId = DrawablesResolver.getFaceIconDrawableId(contentType,
                    contentStatesArray[contentId]);
            int stringId = StringsResolver.getFieldTitleResourceId(contentType);

            DrawContentBean toggleBean = new DrawContentBean();
            toggleBean.setContentType(contentType);
            toggleBean.setContentName(getString(stringId));
            toggleBean.setContentEnabled(contentType == selectedContent);
            toggleBean.setIconDrawableId(drawableId);

            contentTypeList.add(toggleBean);
        }

        return contentTypeList;
    }

    /**
     * Used for displaying additional widget's content settings.
     */
    private void showContentInfoLayout() {
        ContentType contentType = mInstanceInfoBean.mWidgetContents.get(mFieldType.getFieldIndex());

        if (ContentType.GPS == contentType) {
            mContentInfoRelativeLayout.setVisibility(View.VISIBLE);
            mGpsInfoSeparatorLinearLayout.setVisibility(View.VISIBLE);
            GpsEnablingInfoPersistance gpsPersistance = GpsEnablingInfoPersistance.getInstance();
            boolean isChecked = gpsPersistance.shouldShowGpsInfoDialog(getBaseContext());
            mContentInfoCheckBox.setChecked(isChecked);
            mContentInfoLabel.setText(R.string.GpsInfo);

        } else if (ContentType.FLASH_TORCH == contentType) {
            mContentInfoRelativeLayout.setVisibility(View.VISIBLE);
            mGpsInfoSeparatorLinearLayout.setVisibility(View.VISIBLE);
            LedFlashEnablingInfoPersistence ledPersistence = LedFlashEnablingInfoPersistence.getInstance();
            boolean isChecked = ledPersistence.isLedFlashFixInUse(getBaseContext());
            mContentInfoCheckBox.setChecked(isChecked);
            mContentInfoLabel.setText(R.string.LedFlashInfo);

        } else {
            mContentInfoRelativeLayout.setVisibility(View.GONE);
            mGpsInfoSeparatorLinearLayout.setVisibility(View.GONE);
        }
    }

}
