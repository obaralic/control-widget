
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

package com.obaralic.toggler.utilities.debug;

/**
 * Utility class used for logging time passed between two points.
 */
public class TimeLogger {

    private long mStartTime;

    private long mEndTime;

    private boolean mTimingInProgress;

    /**
     * Start timer.
     * 
     * @return Success of starting timer.
     */
    public boolean startTiming() {
        if (mTimingInProgress) {
            return false;
        }
        mStartTime = System.currentTimeMillis();
        return (mTimingInProgress = true);
    }

    /**
     * End timer.
     * 
     * @return Time passed since calling {@link TimeLogger#startTiming()} in ms.
     */
    public long endTiming() {
        if (!mTimingInProgress) {
            return 0;
        }
        mTimingInProgress = false;
        mEndTime = System.currentTimeMillis();
        return (mEndTime - mStartTime);
    }

}

	