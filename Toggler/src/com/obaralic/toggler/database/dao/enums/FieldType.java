
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

package com.obaralic.toggler.database.dao.enums;


/**
 * Enum that contains constant values for every widget field position.
 */
public enum FieldType {

    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private int mFieldPosition;

    private FieldType(int fieldPosition) {
        this.mFieldPosition = fieldPosition;
    }

    /**
     * Retrieves constant that represents one of supported widget field positions.
     * 
     * @return The {@link Integer} representation of the specified field position.
     */
    public int getFieldPosition() {
        return mFieldPosition;
    }
    
    public int getFieldIndex() {
        return mFieldPosition - 1;
    }
    
    public static FieldType getFieldType(int fieldPosition) {
        switch (fieldPosition) {
        case 1:
            return FieldType.ONE;
        case 2:
            return FieldType.TWO;
        case 3:
            return FieldType.THREE;
        case 4:
            return FieldType.FOUR;
        case 5:
            return FieldType.FIVE;        
        default:
            return FieldType.FIVE;
        }
    }
    
    @Override
    public String toString() {
        switch (mFieldPosition) {
        case 1:
            return "[FIELD ONE]";
        case 2:
            return "[FIELD TWO]";
        case 3:
            return "[FIELD THREE]";
        case 4:
            return "[FIELD FOUR]";
        case 5:
            return "[FIELD FIVE]";
        default:
            return super.toString();
        }            
    }
    
}

    