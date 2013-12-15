
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

package com.obaralic.toggler.database.dao;

import com.obaralic.toggler.database.dao.implementation.SqliteInstanceInfoDao;

/**
 * Class for retrieving an instance of a DAO implementation
 */
public class InstanceInfoDaoFactory {

    private static InstanceInfoDao sInstance = null;
    
    // This class can not be instantiated.
    private InstanceInfoDaoFactory() {
        
    }
    
    /**
     * Retrieves singleton instance of the data access object.
     * @return
     */
    public static InstanceInfoDao getInstance() {
        if (sInstance == null) {
            sInstance = new SqliteInstanceInfoDao();
        }
        return sInstance;
    }
    
}

    