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

    private final List<DrawContentBean> mItems;

    private final Context mContext;

    private final int mRowLayoutId;

    public ContentTypeListAdapter(Context context, int rowLayoutId, List<DrawContentBean> items) {
        super(context, rowLayoutId, items);
        this.mItems = items;
        this.mRowLayoutId = rowLayoutId;
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
        View view = inflater.inflate(mRowLayoutId, null);
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
            holder.featureTextView.setText(drawContentBean.getContentName());
            holder.featureTextView.setTextColor(context.getResources().getColor(R.color.white));
            holder.featureIconImageButton.setImageResource(id);
        }
        
        final int visibility = (position == getCount() - 1) ? View.INVISIBLE : View.VISIBLE;
        holder.dividerView.setVisibility(visibility);
    }
}
