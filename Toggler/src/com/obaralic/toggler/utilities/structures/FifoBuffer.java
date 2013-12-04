
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

public interface FifoBuffer<Type> {

    /** Removes an object from the head of the queue and returns it */
    public Type get() throws InterruptedException;

    /** Puts an object at the last position in the queue */
    public void put(Type data);

    /**
     * Puts an object at the last position in the queue and yields execution to the next thread in
     * system.
     */
    public void putAndYield(Type value);

    public int size();

    public int getCapacity();

    public boolean isFull();

    public boolean isEmpty();

}
