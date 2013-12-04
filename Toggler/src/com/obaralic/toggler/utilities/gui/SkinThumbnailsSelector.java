
/*            ____                   ___
 *     ____  / __ )____ __________ _/ (_)____
 *    / __ \/ __  / __ `/ ___/ __ `/ / / ___/
 *   / /_/ / /_/ / /_/ / /  / /_/ / / / /__
 *   \____/_____/\__,_/_/   \__,_/_/_/\___/
 *
 * Copyright 2013 oBaralic, Inc.  All rights reserved.
 * This code is confidential and proprietary information belonging
 * to oBaralic, Inc. and may not be copied, modified or distributed
 * without the express written consent of oBaralic, Inc.
 */
package com.obaralic.toggler.utilities.gui;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.obaralic.toggler.R;
import com.obaralic.toggler.database.dao.InstanceInfoDao;
import com.obaralic.toggler.database.dao.InstanceInfoDaoFactory;
import com.obaralic.toggler.database.dao.beans.InstanceInfoBean;
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.service.WidgetUiServiceFacade;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Utility class used for resolving click event on one of the supported widget skin thumbnails. It acts as
 * check radio group, by deselecting previously selected thumbnail.
 */
public class SkinThumbnailsSelector implements View.OnClickListener {

    private static final String TAG = LogUtil.getTag(SkinThumbnailsSelector.class);

    protected RelativeLayout mParentRelativeLayout;

    protected InstanceInfoBean mInstanceInfoBean;

    public SkinThumbnailsSelector(View parentView, InstanceInfoBean instanceInfoBean) {
        this.mParentRelativeLayout = validateView(parentView);
        this.mInstanceInfoBean = instanceInfoBean;
    }

    /**
     * Method used for validation of the passed {@link View}.
     * 
     * @throws IllegalArgumentException
     *             Exception in case that passed {@link View} is not {@link RelativeLayout}.
     */
    private RelativeLayout validateView(View view) throws IllegalArgumentException {
        return (RelativeLayout) view;
    }

    /**
     * Select currently used skin type.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param skinType
     *            The currently used {@link SkinType}.
     */
    public void invalidateViews(Context context, SkinType skinType) {
        setSelectedSkinType(context, skinType);
    }

    @Override
    public void onClick(View view) {

        int clickedSkinThumbnailId = view.getId();
        Context context = view.getContext();

        // Deselect currently selected skin.
        View frameView;
        int currentlySelectedSkinId = getSelectedSkinThumbnailId(context, mInstanceInfoBean.mSkinType);
        if (currentlySelectedSkinId == SkinType.UNKNOWN.getSkinId()) {
            return;
        }

        RelativeLayout skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                .findViewById(currentlySelectedSkinId);
        frameView = skinRelativeLayout.findViewById(R.id.frame_ImageView);
        frameView.setVisibility(View.GONE);

        // Select currently used skin
        switch (clickedSkinThumbnailId) {

        case R.id.skinGrey_RelativeLayout:
            mInstanceInfoBean.mSkinType = SkinType.GREY;
            break;

        case R.id.skinBlue_RelativeLayout:
            mInstanceInfoBean.mSkinType = SkinType.BLUE;
            break;

        case R.id.skinTransparent_RelativeLayout:
            mInstanceInfoBean.mSkinType = SkinType.TRANSPARENT;
            break;

        case R.id.skinWood_RelativeLayout:
            mInstanceInfoBean.mSkinType = SkinType.WOOD;
            break;

        default:
            LogUtil.w(TAG, "Unknown thumbnail ID selected.");
            return;
        }

        int appWidgetId = mInstanceInfoBean.mAppWidgetId;
        SkinType skinType = mInstanceInfoBean.mSkinType;

        InstanceInfoDao dao = InstanceInfoDaoFactory.getInstance();
        dao.updateInstanceInfoSkinType(context, appWidgetId, skinType);

        frameView = view.findViewById(R.id.frame_ImageView);
        frameView.setVisibility(View.VISIBLE);

        // Notify the Service of the skin change
        WidgetUiServiceFacade widgetUiServiceFacade = WidgetUiServiceFacade.get();
        widgetUiServiceFacade.startOnChangeSkinRequest(context);
    }

    /**
     * Retrieves {@link RelativeLayout}'s id for the currently selected widget skin.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param skinType
     *            The currently used {@link SkinType}.
     * @return returns the {@link RelativeLayout} that is currently selected or -1 if error acquired.
     */
    public int getSelectedSkinThumbnailId(Context context, SkinType skinType) {

        int selectedThumbnailId = SkinType.UNKNOWN.getSkinId();
        switch (skinType) {
        case TRANSPARENT:
            selectedThumbnailId = R.id.skinTransparent_RelativeLayout;
            break;

        case GREY:
            selectedThumbnailId = R.id.skinGrey_RelativeLayout;
            break;

        case BLUE:
            selectedThumbnailId = R.id.skinBlue_RelativeLayout;
            break;

        case WOOD:
            selectedThumbnailId = R.id.skinWood_RelativeLayout;
            break;

        case UNKNOWN:
        default:
            LogUtil.w(TAG, "Selected unknown skin type.");
        }

        return selectedThumbnailId;
    }

    /**
     * Use the given {@link SkinType} as the widget's new skin, and deselect currently selected skin.
     * 
     * @param context
     *            The surrounding {@link Context}.
     * @param skinType
     *            The currently used {@link SkinType}.
     */
    public void setSelectedSkinType(Context context, SkinType skinType) {

        int currentlySelectedSkinId = getSelectedSkinThumbnailId(context, skinType);
        if (currentlySelectedSkinId == SkinType.UNKNOWN.getSkinId()) {
            return;
        }

        RelativeLayout skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                .findViewById(currentlySelectedSkinId);

        View frameView = skinRelativeLayout.findViewById(R.id.frame_ImageView);
        frameView.setVisibility(View.GONE);

        switch (skinType) {

        case TRANSPARENT:
            skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                    .findViewById(R.id.skinTransparent_RelativeLayout);
            break;

        case GREY:
            skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                    .findViewById(R.id.skinGrey_RelativeLayout);
            break;

        case BLUE:
            skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                    .findViewById(R.id.skinBlue_RelativeLayout);
            break;

        case WOOD:
            skinRelativeLayout = (RelativeLayout) mParentRelativeLayout
                    .findViewById(R.id.skinWood_RelativeLayout);
            break;

        case UNKNOWN:
        default:
            LogUtil.w(TAG, "Selected unknown skin type.");
            return;
        }

        frameView = skinRelativeLayout.findViewById(R.id.frame_ImageView);
        frameView.setVisibility(View.VISIBLE);
    }

}
