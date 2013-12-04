
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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.obaralic.toggler.R;
import com.obaralic.toggler.utilities.general.DrawContentBean;

/**
 * Custom list adapter for purpose of populating {@link ListView} in configuration screen.
 */
public class ContentTypeListAdapter extends ArrayAdapter<DrawContentBean> {

    private List<DrawContentBean> mItems;

    private Context mContext;

    public ContentTypeListAdapter(Context context, int textViewResourceId, List<DrawContentBean> items) {
        super(context, textViewResourceId, items);
        this.mItems = items;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = mContext;
        ContentTypeViewHolder holder;
        
        if (convertView == null) {
            convertView = newView(context, parent);
        }
        
        holder = (ContentTypeViewHolder) convertView.getTag();
        holder.mPosition = position;
        bindView(context, convertView);
        return convertView;
    }

    /**
     * Inflates new convert view from layout.
     * 
     * @param context
     *            The surrounding context
     * @param viewGroup
     *            The surrounding viewGroup
     * @return Inflated list row {@link View}
     */
    public View newView(Context context, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_toggle_item, null);
        ContentTypeViewHolder holder = new ContentTypeViewHolder(view);
        view.setTag(holder);
        return view;
    }

    private void bindView(Context context, View view) {
        ContentTypeViewHolder holder = (ContentTypeViewHolder) view.getTag();
        final int position = holder.mPosition;
        DrawContentBean drawContentBean = mItems.get(position);

        if (drawContentBean != null) {
            int visibility = drawContentBean.isContentEnabled() ? View.VISIBLE : View.GONE;
            int id = drawContentBean.getIconDrawableId();            
            holder.showFrame(visibility);
            holder.mFeatureTextView.setText(drawContentBean.getContentName());
            holder.mFeatureTextView.setTextColor(context.getResources().getColor(R.color.white));            
            holder.mFeatureIconImageButton.setImageResource(id);
        }
    }
}
