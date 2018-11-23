package com.skyworth.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.skyworth.core.exception.FormatException;

/**
 * 日期工具类
 * 
 * @author 魏诚
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String DAYTIME_START = "00:00:00";
	public static final String DAYTIME_END = "23:59:59";
	
	@SuppressWarnings("unused")
	private static final String[] FORMATS = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};
	
	/*public static Date convert(String str) {
        if (str != null && str.length() > 0) {
            if (str.length() > 10 && str.charAt(10) == 'T')
                str = str.replace('T', ' '); // 去掉json-lib加的T字母
            for (String format : FORMATS) {
                if (str.length() == format.length()) {
                    try {
                        Date date = new SimpleDateFormat(format).parse(str);
                        return date;
                    } catch (ParseException e) {
                    }
                }
            }
        }
        return null;
    }*/
	
	public static Date convert(String str) {
		if(str != null && str.length() > 0) {
			int index1 = str.indexOf("-");
			int index2 = str.indexOf("/");
			if(index1 < 0 && index2 <0){
				return null;
			}
            if(str.length() > 10 && str.charAt(10) == 'T'){
            	str = str.replace('T', ' '); // 去掉json-lib加的T字母
            }
            
    		String parse = str;
            parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
            parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
            parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1mm$2");
            parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");
            DateFormat format = new SimpleDateFormat(parse);
    		try {
    			Date date = format.parse(str);
    	        return date;
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        }
        return null;
    }

	public static String formatDate(Date date) {
		return format(date, DATE_FORMAT);
	}

	public static String formatTime(Date date) {
		return format(date, DATE_TIME_FORMAT);
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(Date date, String format, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}

	public static Date parseDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new FormatException(e);
		}
	}
	
	public static Date parseTime(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new FormatException(e);
		}
	}
	
	public static Date parse(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new FormatException(e);
		}
	}

	public static Date parse(String date, String format, Locale locale) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new FormatException(e);
		}
	}

}
