
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

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.obaralic.toggler.R;
import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Introduced the ViewHolder to make things faster
 * http://dl.google.com/googleio/2010/android-world-of-listview-android.pdf (Page 14)
 */
public class ContentTypeViewHolder {

    private static final String TAG = LogUtil.getTag(ContentTypeViewHolder.class);

    public ImageButton featureIconImageButton;

    public ImageButton featureSelectedImageButton;

    public TextView featureTextView;

    public View frameView;

    public View dividerView;

    /**
     * View's position in the list. Needed for memorizing which position is the selected one inside the
     * ArrayAdapter.
     */
    public int mPosition;

    public ContentTypeViewHolder(View view) {
        initializeFromView(view);
    }

    /**
     * Initialize {@link ContentTypeViewHolder} from its holding view.
     */
    private void initializeFromView(View view) {
        LogUtil.d(TAG, "Called initializeFromView");

        this.featureTextView = (TextView) view.findViewById(R.id.rowFeatureName_TextView);

        this.featureIconImageButton = (ImageButton) view.findViewById(R.id.rowFeatureIcon_ImageButton);
        this.frameView = view.findViewById(R.id.rowFrame_ImageView);

        this.featureSelectedImageButton = (ImageButton) view
                .findViewById(R.id.rowFeatureSelectedIcon_ImageButton);
        this.dividerView = view.findViewById(R.id.rowSeparator);

        this.featureIconImageButton.setFocusable(false);
        this.featureSelectedImageButton.setFocusable(false);
    }

    public void showFrame(int visibility) {
        frameView.setVisibility(visibility);
        featureSelectedImageButton.setVisibility(visibility);
    }

}
