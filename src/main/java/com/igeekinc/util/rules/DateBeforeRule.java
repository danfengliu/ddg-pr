/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.igeekinc.util.rules;

import static com.igeekinc.util.rules.Internationalization._;
import java.text.MessageFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateBeforeRule extends DateRule
{
	static final long serialVersionUID = -3192459875373522283L;
	static MessageFormat modDateFormatter = new MessageFormat(_("Files whose last modified time is before {0, date,MMMM d, yyyy}")); //$NON-NLS-1$
	static MessageFormat createDateFormatter = new MessageFormat(_("Files whose creation time is before {0, date,MMMM d, yyyy}")); //$NON-NLS-1$
	
	public DateBeforeRule(Date beforeDate, int dateField)
	{
		long startTime, endTime;
		startTime = Long.MIN_VALUE;
		GregorianCalendar workCalendar = new GregorianCalendar();

		workCalendar.setTime(beforeDate);
		
		workCalendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
		workCalendar.set(GregorianCalendar.MINUTE, 59);
		workCalendar.set(GregorianCalendar.SECOND, 59);
		workCalendar.set(GregorianCalendar.MILLISECOND, 999);
		endTime = workCalendar.getTime().getTime();

		init(startTime, endTime, dateField);
	}
	
	public String toString()
	{
		switch(getDateField())
		{
		case kModifiedTime:
			return(modDateFormatter.format(new Object[]{new Date(getEndTime())}));
		case kCreatedTime:
			return(createDateFormatter.format(new Object[]{new Date(getEndTime())}));
		}
		throw new InternalError("Unexpected DateField "+getDateField()); //$NON-NLS-1$
	}
}
