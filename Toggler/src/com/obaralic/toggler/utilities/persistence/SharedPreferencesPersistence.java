
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

package com.obaralic.toggler.utilities.persistence;

import java.util.StringTokenizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.obaralic.toggler.utilities.debug.LogUtil;

/**
 * Concrete implementation of the {@link PersistenceInterface}.
 */
public class SharedPreferencesPersistence implements PersistenceInterface {

    private static final String TAG = LogUtil.getTag(SharedPreferencesPersistence.class);

    private static final String SHARED_PREFERENCES_FILE_NAME = "rs.hellenic.zet.widget.toggler.SharedPreferencesPersistence";

    private static final String EMPTY = "EMPTY_ARRAY";

    private StringBuffer mStringBuffer;

    public SharedPreferencesPersistence() {
        mStringBuffer = new StringBuffer();
    }

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor edit(Context context) {
        return get(context).edit();
    }

    @Override
    public void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = edit(context);
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getString(Context context, String key, String defaultValueToReturnIfValueNotFound) {
        return get(context).getString(key, defaultValueToReturnIfValueNotFound);
    }

    @Override
    public void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = edit(context);
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public int getInt(Context context, String key, int defaultValueToReturnIfValueNotFound) {
        return get(context).getInt(key, defaultValueToReturnIfValueNotFound);
    }

    @Override
    public void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = edit(context);
        editor.putLong(key, value);
        editor.commit();
    }

    @Override
    public long getLong(Context context, String key, long defaultValueToReturnIfValueNotFound) {
        return get(context).getLong(key, defaultValueToReturnIfValueNotFound);
    }

    @Override
    public void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = edit(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean getBoolean(Context context, String key, boolean defaultValueToReturnIfValueNotFound) {
        return get(context).getBoolean(key, defaultValueToReturnIfValueNotFound);
    }

    @Override
    public boolean contains(Context context, String key) {
        return get(context).contains(key);
    }

    @Override
    public void remove(Context context, String key) {
        SharedPreferences.Editor editor = edit(context);
        editor.remove(key);
        editor.commit();
    }

    @Override
    public void putIntArray(Context context, String valueKey, String lengthKey, int[] values) {

        if (mStringBuffer == null) {
            mStringBuffer = new StringBuffer();
        } else {
            mStringBuffer.setLength(0);
        }

        for (int value : values) {
            mStringBuffer.append(value);
            mStringBuffer.append(",");
        }

        SharedPreferences.Editor editor = edit(context);
        editor.putString(valueKey, mStringBuffer.toString());
        editor.putInt(lengthKey, values.length);
        editor.commit();
    }

    @Override
    public int[] getIntArray(Context context, String valueKey, String lengthKey,
            int[] defaultValueToReturnIfValueNotFound) {

        String savedString = get(context).getString(valueKey, EMPTY);

        if (EMPTY.equals(savedString)) {
            Log.w(TAG, "Empty CSV for " + valueKey + " (" + lengthKey + ")");
            return defaultValueToReturnIfValueNotFound;
        }

        int length = get(context).getInt(lengthKey, 0);
        StringTokenizer tokenizer = new StringTokenizer(savedString, ",");
        int[] intArray = new int[length];

        if (length == 5) {
            Log.w(TAG, "Length 5");
        } else if (length == 10) {
            Log.w(TAG, "Length 5");
        } else {
            Log.w(TAG, "Length else");
        }

        for (int i = 0; i < length; i++) {
            intArray[i] = Integer.parseInt(tokenizer.nextToken());
        }
        return intArray;
    }
}
