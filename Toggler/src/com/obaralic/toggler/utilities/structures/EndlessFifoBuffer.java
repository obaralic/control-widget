
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

package com.obaralic.toggler.utilities.structures;

import java.util.ArrayList;

import com.obaralic.toggler.utilities.debug.LogUtil;

public class EndlessFifoBuffer<Type> implements FifoBuffer<Type> {

    private static final String TAG = LogUtil.getTag(EndlessFifoBuffer.class);

    public static final int RECOMMENDED_CAPACITY = 10;

    protected int mCapacity;
    protected ArrayList<Type> mArrayBuffer;

    public EndlessFifoBuffer() {
        mCapacity = RECOMMENDED_CAPACITY;
        mArrayBuffer = new ArrayList<Type>(mCapacity);
    }

    public EndlessFifoBuffer(int newCapacity) {
        if (newCapacity > 0 && newCapacity <= RECOMMENDED_CAPACITY) {
            mCapacity = newCapacity;
        } else
            mCapacity = RECOMMENDED_CAPACITY;
        mArrayBuffer = new ArrayList<Type>(mCapacity);
    }

    @Override
    public synchronized Type get() throws InterruptedException {
        while (mArrayBuffer.size() == 0) {
            wait();
        }
        Type object = mArrayBuffer.remove(0);
        notifyAll();
        return object;
    }

    @Override
    public synchronized int size() {
        return mArrayBuffer.size();
    }

    @Override
    public synchronized int getCapacity() {
        return mCapacity;
    }

    @Override
    public synchronized boolean isEmpty() {
        return mArrayBuffer.size() == 0;
    }

    @Override
    public synchronized boolean isFull() {
        return mArrayBuffer.size() == mCapacity;
    }

    @Override
    public void putAndYield(Type value) {
        put(value);
        Thread.yield(); // Delegated execution to next thread
    }

    @Override
    /**
     * Puts an object at the end of the Queue, but if the buffer has reached recommended capacity does not block,
     * only logs a warning of exceeding the recommended boundary capacity.
     */
    public synchronized void put(Type value) {
        if (mArrayBuffer.size() == mCapacity) {
            LogUtil.w(TAG, "Exceeding the recommended buffer size of: " + mCapacity);
        }
        mArrayBuffer.add(value);
        notifyAll();
    }
}
