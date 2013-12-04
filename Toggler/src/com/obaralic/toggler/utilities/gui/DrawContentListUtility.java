
/*            ____                   ___
 *     ____  / __ )____ __________ _/ (_)____
 *    / __ \/ __  / __ `/ ___/ __ `/ / / ___/
 *   / /_/ / /_/ / /_/ / /  / /_/ / / / /__
 *   \____/_____/\__,_/_/   \__,_/_/_/\___/
 *
 * Copyright 2013 oBaralic, Inc.  All rights reserved.
 * This code is confidential and proprietary information belonging
 * to oBaralic, Inc. and may not be copied, modified or distributed
 * without the express written consent of oBaralic, Inc.
 */

package com.obaralic.toggler.utilities.gui;

import java.util.LinkedList;
import java.util.List;

import com.obaralic.toggler.utilities.general.DrawContentBean;

/**
 * The singleton class used for storing supported content types in the configuration mode.
 */
public class DrawContentListUtility {

    private static List<DrawContentBean> sFeaturesListInstance = null;

    private DrawContentListUtility() {
    }

    /**
     * Retrieves singleton instance of this class.
     * 
     * @return The instance of the class.
     */
    public static List<DrawContentBean> getInstance() {
        if (sFeaturesListInstance == null) {
            sFeaturesListInstance = new LinkedList<DrawContentBean>();
        }
        return sFeaturesListInstance;
    }

}
