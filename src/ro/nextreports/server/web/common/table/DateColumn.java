/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.server.web.common.table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Decebal Suiu
 */
public class DateColumn<T> extends PropertyColumn<T, String> {
	
	private static final long serialVersionUID = 6240590562722410222L;

//    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	public static DateFormat DATE_FORMAT= DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	public static DateFormat LOCALE_DATE_FORMAT = getAlignedFormat();
    
	public DateColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
		super(displayModel, sortProperty, propertyExpression);
	}
    
	@Override
	protected IModel<String> createLabelModel(IModel<T> rowModel) {
        Date date = (Date) PropertyResolver.getValue(getPropertyExpression(), rowModel.getObject());
        if (date == null) {
        	return new Model<String>("");
        }        
        return new Model<String>(LOCALE_DATE_FORMAT.format(date));
	}

	@Override
	public String getCssClass() {
		return "date";
	}
	
	// By default locale pattern may not contain two digits for months, days, hours
    // meaning in a column table the dates may look ugly (not aligned)
    // To show Locale specific short date with two digits for months, days, hours
    // we need to take the pattern and modify it
	public static DateFormat getAlignedFormat() {
		if (DATE_FORMAT instanceof SimpleDateFormat) {
			SimpleDateFormat sdf = (SimpleDateFormat) DATE_FORMAT;
			String pattern = sdf.toPattern()
					.replaceAll("M+", "MM")
					.replaceAll("d+", "dd")
					.replaceAll("h+", "hh")
					.replaceAll("H+", "HH");
			sdf.applyPattern(pattern);
			return sdf;
		} else {
			return DATE_FORMAT;
		}
	}

}
