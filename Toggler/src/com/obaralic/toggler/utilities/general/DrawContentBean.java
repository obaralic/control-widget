
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

package com.obaralic.toggler.utilities.general;

import com.obaralic.toggler.database.dao.enums.ContentType;

public class DrawContentBean {

    private String mContentName;
    private boolean mIsContentEnabled;
    private int mIconDrawableId;
    private ContentType mContentType;

    public DrawContentBean() {
    }

    public String getContentName() {
        return mContentName;
    }

    public void setContentName(String contnentName) {
        this.mContentName = contnentName;
    }

    public boolean isContentEnabled() {
        return mIsContentEnabled;
    }

    public void setContentEnabled(boolean isContentEnabled) {
        this.mIsContentEnabled = isContentEnabled;
    }

    public int getIconDrawableId() {
        return mIconDrawableId;
    }

    public void setIconDrawableId(int iconDrawableId) {
        this.mIconDrawableId = iconDrawableId;
    }

    public ContentType getContentType() {
        return mContentType;
    }

    public void setContentType(ContentType contentType) {
        this.mContentType = contentType;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: ").append(mContentName);
        buffer.append(", Selected: ").append(mIsContentEnabled);
        return buffer.toString();
    }

}
