
/*
 * Copyright 2014 oBaralic, Inc. (owner Zivorad Baralic)
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

package com.obaralic.toggler.activities.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obaralic.toggler.utilities.debug.LogUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GpsInfoFragment extends DialogFragment {

    public static final String TAG = LogUtil.getTag(GpsInfoFragment.class);

    private static final String ARG_DIALOG_ICON_ID = "iconId";

    private static final String ARG_DIALOG_TITLE_ID = "titleId";

    private static final String ARG_DIALOG_MESSAGE_ID = "messageId";

    public interface OnButtonClickListener {

        void doPositiveClick();

        void doNegativeClick();
    }

    private int mIconId;

    private int mTitleId;

    private int mMessageId;

    private OnButtonClickListener mOnButtonClickListener;

    public static GpsInfoFragment newInstance(int iconId, int titleId, int messageId) {
        GpsInfoFragment fragment = new GpsInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DIALOG_ICON_ID, iconId);
        args.putInt(ARG_DIALOG_TITLE_ID, titleId);
        args.putInt(ARG_DIALOG_MESSAGE_ID, messageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnButtonClickListener)) {
            throw new IllegalArgumentException("Parent activity must implement "
                    + OnButtonClickListener.class.getSimpleName());
        }
        mOnButtonClickListener = (OnButtonClickListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove background of the dialog fragment.
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        extractArguments(getArguments());

        Builder builder = new Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
        builder.setIcon(mIconId);
        builder.setTitle(mTitleId);
        builder.setMessage(mMessageId);
        builder.setCancelable(true);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mOnButtonClickListener.doPositiveClick();
            }
        });

        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                mOnButtonClickListener.doNegativeClick();
            }
        });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        getActivity().finish();
    }

    private void extractArguments(Bundle bundle) {
        mIconId = bundle.getInt(ARG_DIALOG_ICON_ID);
        mTitleId = bundle.getInt(ARG_DIALOG_TITLE_ID);
        mMessageId = bundle.getInt(ARG_DIALOG_MESSAGE_ID);
    }
}
