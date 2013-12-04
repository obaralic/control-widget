
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

package com.obaralic.toggler.service.observers;

import android.database.ContentObserver;
import android.os.Handler;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Class that extends {@link ContentObserver} in order to register change of rotation settings.
 */
public class AutoRotateContentObserver extends ContentObserver {

    private static final String TAG = LogUtil.getTag(AutoRotateContentObserver.class);

    /**
     * Interface that defines call back method for notifying that observed parameter is was changed.
     */
    public static interface AutoRotateStateChangedListener {

        void autoRotateStateChanged();
    }

    private AutoRotateStateChangedListener mListener;

    public AutoRotateContentObserver(Handler handler, AutoRotateStateChangedListener listener) {
        super(handler);
        mListener = listener;
    }

    @Override
    public void onChange(boolean selfChange) {
        LogUtil.d(TAG, "Called onChange");

        mListener.autoRotateStateChanged();

        super.onChange(selfChange);
    }

}