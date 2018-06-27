package com.fanteng.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

	/**
	 * date转换String
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * String转Date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String date, String pattern) throws ParseException {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		return parseDate(date, pattern);
	}

}