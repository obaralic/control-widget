
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

package com.obaralic.toggler.utilities.persistence.implementation;

import android.content.Context;

import com.obaralic.toggler.database.dao.enums.FieldType;
import com.obaralic.toggler.service.WidgetUiService;
import com.obaralic.toggler.utilities.persistence.PersistenceFactory;
import com.obaralic.toggler.utilities.persistence.PersistenceInterface;

/**
 * Used by {@link WidgetUiService} to store {@link FieldType} on which on click was performed.
 */
public class ClickedFieldTypePersistence {

    private static class PersistenceKeys {
		private static final String SELECTED_WIDGET_FIELD = "SELECTED_WIDGET_FIELD";
	}

	private static class PersistenceDefaultValues {
		private static final int SELECTED_WIDGET_FIELD = FieldType.ONE.getFieldPosition();
	}

	private static ClickedFieldTypePersistence sInstance;

	private ClickedFieldTypePersistence() {
	}

	public static ClickedFieldTypePersistence getInstance() {
		if (sInstance == null) {
			sInstance = new ClickedFieldTypePersistence();
		}
		return sInstance;
	}

	/**
	 * Retrieves the currently selected widget field.
	 * 
	 * @param context
	 *            The Context of the caller
	 * @return The current selected widget field (one of the constants in {@link WidgetFieldType})
	 */
	public int getWidgetFieldOrder(Context context) {
		PersistenceInterface persistenceInterface = PersistenceFactory.get();
		return persistenceInterface.getInt(context, PersistenceKeys.SELECTED_WIDGET_FIELD, PersistenceDefaultValues.SELECTED_WIDGET_FIELD);
	}

	/**
	 * Sets the selected widget field.
	 * 
	 * @param context
	 *            The Context of the caller
	 * @param layoutType
	 *            The widget field type (one of the constants in {@link WidgetFieldType})
	 */
	public void setWidgetFieldOrder(Context context, int widgetSize) {
		PersistenceInterface persistenceInterface = PersistenceFactory.get();
		persistenceInterface.putInt(context, PersistenceKeys.SELECTED_WIDGET_FIELD, widgetSize);
	}

}
