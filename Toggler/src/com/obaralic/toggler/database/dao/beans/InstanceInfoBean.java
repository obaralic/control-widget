
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

package com.obaralic.toggler.database.dao.beans;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.obaralic.toggler.database.dao.enums.ContentType;
import com.obaralic.toggler.database.dao.enums.SkinType;
import com.obaralic.toggler.database.dao.enums.WidgetSizeType;

/**
 * The transport class used for wrapping content related to generalized widget instance.
 */
public class InstanceInfoBean implements Parcelable{
    
    public long mId;
    
    public int mAppWidgetId;
    
    public SkinType mSkinType;
    
    public WidgetSizeType mWidgetSizeType;
    
    public ArrayList<ContentType> mWidgetContents;

    public InstanceInfoBean() {
        this.mWidgetContents = new ArrayList<ContentType>();
    }
    
    public InstanceInfoBean(int id, int appWidgetId, SkinType skinType, WidgetSizeType widgetSizeType) {
        this.mId = id;
        this.mAppWidgetId = appWidgetId;
        this.mSkinType = skinType;
        this.mWidgetSizeType = widgetSizeType;
        this.mWidgetContents = new ArrayList<ContentType>(widgetSizeType.getWidgetSizeId());
    }
    
    public InstanceInfoBean(int id, int appWidgetId, WidgetSizeType widgetSizeType) {
        this(id, appWidgetId, SkinType.GREY, widgetSizeType);
    }

    public InstanceInfoBean(int appWidgetId, WidgetSizeType widgetSizeType) {
        this(0, appWidgetId, widgetSizeType);
    }
    
    public InstanceInfoBean(Parcel in) {
        readFromParcel(in);
    }
            
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Instance Info [ Id=");
        buffer.append(mId);
        buffer.append(", WidgetId=");
        buffer.append(mAppWidgetId);
        buffer.append(", Skin=");
        buffer.append(mSkinType.toString());
        buffer.append(", Size=");
        buffer.append(mWidgetSizeType.toString());
        buffer.append(" ");
        for (int i = 0; i < mWidgetContents.size(); i++) {
            buffer.append(mWidgetContents.get(i).toString());
            if (i + 1 < mWidgetContents.size()) {
                buffer.append(" ,");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
    
    public static InstanceInfoBean getDefault1x1Instance() {
        InstanceInfoBean defaultInstance = new InstanceInfoBean();         
        defaultInstance.mSkinType = SkinType.GREY;        
        defaultInstance.mWidgetSizeType = WidgetSizeType.ONE;
        defaultInstance.mWidgetContents.add(ContentType.WIFI);
        defaultInstance.mWidgetContents.add(null);
        defaultInstance.mWidgetContents.add(null);
        defaultInstance.mWidgetContents.add(null);
        defaultInstance.mWidgetContents.add(null);
        return defaultInstance;
    }
    
    public static InstanceInfoBean getDefault5x1Instance() {
        InstanceInfoBean defaultInstance = new InstanceInfoBean(); 
        defaultInstance.mSkinType = SkinType.GREY;
        defaultInstance.mWidgetSizeType = WidgetSizeType.FIVE;
        defaultInstance.mWidgetContents.add(ContentType.MOBILE_DATA);
        defaultInstance.mWidgetContents.add(ContentType.WIFI);
        defaultInstance.mWidgetContents.add(ContentType.WIFI_HOTSPOT);
        defaultInstance.mWidgetContents.add(ContentType.AUTO_SYNC);
        defaultInstance.mWidgetContents.add(ContentType.SETTINGS);
        return defaultInstance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mId);
        out.writeInt(mAppWidgetId);
        out.writeString((mSkinType == null) ? "" : mSkinType.name());
        out.writeString((mWidgetSizeType == null) ? "" : mWidgetSizeType.name());
//        out.writeInt(mWidgetContents.size());
//        for (ContentType contentType : mWidgetContents) {
//            out.writeString((contentType == null) ? "" : contentType.name());
//        }
    }
    
    public void readFromParcel(Parcel in){
        mId = in.readLong();
        mAppWidgetId = in.readInt();
        
        try {
            mSkinType = SkinType.valueOf(in.readString());
        } catch (IllegalArgumentException x) {
            mSkinType = null;
        }
        
        try {
            mWidgetSizeType = WidgetSizeType.valueOf(in.readString());
        } catch (IllegalArgumentException x) {
            mWidgetSizeType = null;
        }
        
//        mWidgetContents = new ArrayList<ContentType>();
//        int size = in.readInt();
//        for (int i = 0; i < size; i++) {
//            try {
//                mWidgetContents.add(ContentType.valueOf(in.readString()));
//            } catch (IllegalArgumentException x) {
//                mSkinType = null;
//            }            
//        }
    }

    /**
     * This field is needed for Android to be able to create new objects, individually or as arrays. This also
     * means that you can use use the default constructor to create the object and use another method to
     * hyrdate it as necessary. I just find it easier to use the constructor. It makes sense for the way my
     * brain thinks ;-)
     */
    public static final Parcelable.Creator<InstanceInfoBean> CREATOR = new Parcelable.Creator<InstanceInfoBean>() {

        public InstanceInfoBean createFromParcel(Parcel in) {
            return new InstanceInfoBean(in);
        }

        public InstanceInfoBean[] newArray(int size) {
            return new InstanceInfoBean[size];
        }
    };

}

    