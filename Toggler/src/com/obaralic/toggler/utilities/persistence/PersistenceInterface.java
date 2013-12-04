
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

import android.content.Context;

/**
 * Persistence interface that defines persistence access contract.
 */
public interface PersistenceInterface {

    /**
     * Puts a String into persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key for later retrieving of the value
     * @param value
     *            The String value to be kept in storage
     */
    void putString(Context context, String key, String value);

    /**
     * Retrieves a String from the persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key that matches the desired value we want to retrieve
     * @param defaultValueToReturnIfValueNotFound
     *            The value to return if no matched value for the specified key was found in persistent
     *            storage
     * @return The desired String value for the given key
     */
    String getString(Context context, String key, String defaultValueToReturnIfValueNotFound);

    /**
     * Puts an integer into persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key for later retrieving of the value
     * @param value
     *            The integer value to be kept in storage
     */
    void putInt(Context context, String key, int value);

    /**
     * Retrieves an integer from the persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key that matches the desired value we want to retrieve
     * @param defaultValueToReturnIfValueNotFound
     *            The value to return if no matched value for the specified key was found in persistent
     *            storage
     * @return The desired integer value for the given key
     */
    int getInt(Context context, String key, int defaultValueToReturnIfValueNotFound);

    /**
     * Removes a value that corresponds to the specified key in persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key whose matching value we're removing
     */
    void remove(Context context, String key);

    /**
     * Puts an long into persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key for later retrieving of the value
     * @param value
     *            The long value to be kept in storage
     */
    void putLong(Context context, String key, long value);

    /**
     * Retrieves an long from the persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key that matches the desired value we want to retrieve
     * @param defaultValueToReturnIfValueNotFound
     *            The value to return if no matched value for the specified key was found in persistent
     *            storage
     * @return The desired long value for the given key
     */
    long getLong(Context context, String key, long defaultValueToReturnIfValueNotFound);

    /**
     * Puts an boolean into persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key that matches the desired value we want to retrieve
     * @param value
     *            The boolean value to be kept in storage
     */
    void putBoolean(Context context, String key, boolean value);

    /**
     * Retrieves an boolean from the persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key that matches the desired value we want to retrieve
     * @param defaultValueToReturnIfValueNotFound
     *            The value to return if no matched value for the specified key was found in persistent
     *            storage
     * @return The desired boolean value for the given key
     */
    boolean getBoolean(Context context, String key, boolean defaultValueToReturnIfValueNotFound);

    /**
     * Puts an integer array into persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param valueKey
     *            The key that matches the desired value we want to retrieve
     * @param lengthKey
     *            The key that matches the desired length of the integer array
     * @param values
     *            The integer array value to be kept in storage
     */
    void putIntArray(Context context, String valueKey, String lengthKey, int[] values);

    /**
     * Retrieves an integer array from the persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param valueKey
     *            The key that matches the desired value we want to retrieve
     * @param lengthKey
     *            The key that matches the desired length of the integer array
     * @param defaultValueToReturnIfValueNotFound
     *            The value to return if no matched value for the specified key was found in persistent
     *            storage
     * @return The desired integer array value for the given key
     */
    int[] getIntArray(Context context, String valueKey, String lengthKey,
            int[] defaultValueToReturnIfValueNotFound);

    /**
     * Tests if a value that corresponds to the specified key is present in persistent storage
     * 
     * @param context
     *            The surrounding Context
     * @param key
     *            The key for later retrieving of the value
     * @return The boolean value that is outcome of test operation
     */
    boolean contains(Context context, String key);

}
