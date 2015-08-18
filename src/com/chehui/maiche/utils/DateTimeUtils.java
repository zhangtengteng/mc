package com.chehui.maiche.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @author ztt
 *         <P>
 *         时间工具,没见用到
 *         <P>
 * 
 */
public class DateTimeUtils {
	/**
	 * 时分秒�?�率
	 */
	public static final int TIME_MAGNIFICATION = 60;

	/**
	 * �?小时的秒�?
	 */
	public static final int TIME_HOUR_SECONDS = 3600;

	/**
	 * �?秒的毫秒�?
	 */
	public static final long SECOND_MILLSECONDS = 1000;

	public static final String STR_TIME_FORMAT = "yyyyMMddHHmmss";

	/**
	 * 取得两个时间的跨�?
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static long timeLong(String beginTime, String endTime) {
		long recordTime = 0;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
					Locale.CHINA);
			Date beginDate = sdf.parse(beginTime);
			Date endDate = sdf.parse(endTime);

			long recordSeconds = Math.abs(endDate.getTime()
					- beginDate.getTime());
			recordTime = recordSeconds / 1000;
		} catch (ParseException e) {
			LogN.e(DateTimeUtils.class, "timeLong | " + e.getMessage());
		}

		return recordTime;
	}

	public static String transSecondsToFormatString(int seconds) {
		String time = "00:00";

		int s = seconds % TIME_MAGNIFICATION;

		int m = (seconds / TIME_MAGNIFICATION) % TIME_MAGNIFICATION;

		int h = seconds / TIME_MAGNIFICATION / TIME_MAGNIFICATION;

		if (0 == h) {
			time = String.format(Locale.CHINA, "%02d:%02d", m, s);
		} else {
			time = String.format(Locale.CHINA, "%02d:%02d:%02d", h, m, s);
		}

		return time;
	}

	/**
	 * 将字符串数据转化为毫秒数
	 * 
	 * @param beginTime
	 * @return
	 */
	public static long transTimeToMills(String beginTime) {
		long millis = 0;

		Calendar c = Calendar.getInstance();

		try {
			c.setTime(new SimpleDateFormat(STR_TIME_FORMAT, Locale.CHINA)
					.parse(beginTime));
			millis = c.getTimeInMillis();
		} catch (ParseException e) {
			millis = 0;
			LogN.e(DateTimeUtils.class, "transTimeToMills | Parse error");
		}

		return millis;
	}

	/**
	 * 获得时间格式化字符串
	 * 
	 * @param date
	 *            要格式化的时�?
	 * @return 格式化后时间字符�?(YYYYMMDD),异常则返回空字符�?
	 */
	public static String getFormatTime(Date date) {
		return getFormatTime(date, STR_TIME_FORMAT);
	}

	/**
	 * 获得时间格式化字符串
	 * 
	 * @param date
	 *            要格式化的时�?
	 * @return 格式化后时间字符�?(YYYY-MM-DD),异常则返回空字符�?
	 */
	public static String getFormatTime(Date date, String formatter) {
		if (null == formatter || null == date) {
			LogN.e(DateTimeUtils.class, "param is null");
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.CHINA);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return sdf.format(date);
	}

	/**
	 * 获得时间格式化字符串
	 * 
	 * @param date
	 *            要格式化的时�?
	 * @return 格式化后时间字符�?(YYYY-MM-DD),异常则返回空字符�?
	 */
	public static String transTimeToString(Date date, String formatter) {
		if (null == formatter || null == date) {
			LogN.e(DateTimeUtils.class, "param is null");
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 将字符串时间格式化为相应的时间串
	 * 
	 * @param time
	 * @param inFormat
	 *            time的输入格式化
	 * @param outFormat
	 *            time的输出格式化
	 * @throws ParseException
	 * @since 1.0.0
	 */
	public static String transTimeToString(String time, String inFormat,
			String outFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(inFormat, Locale.CHINA);
		Date date = sdf.parse(time);

		SimpleDateFormat outSdf = new SimpleDateFormat(outFormat, Locale.CHINA);

		return outSdf.format(date);
	}

	/**
	 * 获取指定跨度的时�?
	 * 
	 * @param time
	 *            原始时间
	 * @param timeSpan
	 *            时间跨度，小�?0表示跨度向前，大�?0表示跨度向后
	 * @return 跨度后时�?
	 */
	public static String getTimeWithSpan(String time, int timeSpan) {
		if (null == time || 0 == timeSpan) {
			return time;
		}

		long timeResult = transTimeToMills(time) + timeSpan;

		return transTimeToString(new Date(timeResult), STR_TIME_FORMAT);
	}

	/**
	 * * 获得count月前的第�?�?,count如果�?0就获得当前月份的第一�?
	 * 
	 * @param endtime
	 * @param count
	 * @return
	 */
	public static String getPreMonFirstDay(String endtime, int count) {
		String beginTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
				Locale.CHINA);

		try {
			Date date = sdf.parse(endtime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);

			int beginYear = 0;
			int beginMonth = 0;
			int beginDay = 1;
			int beginHour = 0;
			int beginMinute = 0;
			int beginSecond = 0;
			if (month > count && count != 0) {
				beginYear = year;
				beginMonth = month - count;
			} else {
				if (count == 0) {
					beginYear = year;
					beginMonth = month;
				} else {
					beginYear = year - 1;
					beginMonth = month + 12 - count;
				}

			}

			calendar.set(beginYear, beginMonth, beginDay, beginHour,
					beginMinute, beginSecond);
			beginTime = sdf.format(calendar.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return beginTime;
	}

	/**
	 * * 获得本月的最后一�?
	 * 
	 * @param endtime
	 * @param count
	 * @return
	 */
	public static String getCurMonLastDay(String time) {
		String beginTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
				Locale.CHINA);

		try {
			Date date = sdf.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);

			int lastDay = getMaxDay(year - 1900, month + 1);

			int endYear = year;
			int endMonth = month;
			int endDay = lastDay;
			int endHour = 23;
			int endMinute = 59;
			int beginSecond = 59;

			calendar.set(endYear, endMonth, endDay, endHour, endMinute,
					beginSecond);
			beginTime = sdf.format(calendar.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return beginTime;
	}

	/**
	 * * 判断闰年还是平年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 400 == 0 || (year % 4 == 0) && (year % 100 != 0));
	}

	/**
	 * * 获得某年某月的最大天�?
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDay(int year, int month) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			if (isLeapYear(year)) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		default:
			break;
		}

		return day;
	}

	/**
	 * 
	 * 按照flag参数来截取字符串得到时间
	 * 
	 * @param time
	 *            yyyyMMddHHmmss格式时间
	 * @param flag
	 *            Calendar时间常量标示：Calendar.DATE,Calendar.MONTH,Calendar.YEAR
	 * @return
	 */
	public static String getDateTimeFrom(String time, int flag) {
		if (null == time || STR_TIME_FORMAT.length() != time.length()) {
			return time;
		}

		switch (flag) {
		case Calendar.DAY_OF_YEAR:
			time = time.substring(0, 10);
			break;

		case Calendar.MONTH:
			time = time.substring(0, 7);
			break;

		case Calendar.YEAR:
			time = time.substring(0, 4);
			break;

		case Calendar.DAY_OF_MONTH:
			time = time.substring(5, 10);
			break;

		case Calendar.HOUR:
			time = time.substring(11, 13);
			break;

		case Calendar.MINUTE:
			time = time.substring(11, 16);
			break;

		case Calendar.SECOND:
			time = time.substring(11, 19);
			break;

		default:
			time = time.substring(5, 7);
			break;
		}

		return time;
	}

	/**
	 * * 获得当天的最后一秒时�?
	 * 
	 * @param time
	 * @return
	 */
	public static String getTodayLastSecond(String time) {
		String rTemp = "";
		SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
				Locale.CHINA);
		try {
			Date date = sdf.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			int tempYear = year;
			int tempMonth = month;
			int tempDay = day;
			int tempHour = 23;
			int tempMinute = 59;
			int tempSecond = 59;

			calendar.set(tempYear, tempMonth, tempDay, tempHour, tempMinute,
					tempSecond);
			rTemp = sdf.format(calendar.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return rTemp;
	}

	/**
	 * * 获得当天的第�?秒时�?
	 * 
	 * @param time
	 * @return
	 */
	public static String getTodayFirstSecond(String time) {
		String rTemp = "";
		SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
				Locale.CHINA);
		try {
			Date date = sdf.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			int tempYear = year;
			int tempMonth = month;
			int tempDay = day;
			int tempHour = 0;
			int tempMinute = 0;
			int tempSecond = 0;

			calendar.set(tempYear, tempMonth, tempDay, tempHour, tempMinute,
					tempSecond);
			rTemp = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rTemp;
	}

	/**
	 * * 获得某一个时间的前一天的时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getPreDay(String time) {
		LogN.d(DateTimeUtils.class, "enter | getPreDay");
		String rTemp = "";
		SimpleDateFormat sdf = new SimpleDateFormat(STR_TIME_FORMAT,
				Locale.CHINA);
		try {
			Date date = sdf.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);

			int tempYear = 0;
			int tempMonth = 0;
			int tempDay = 0;
			if (day == 1) {
				if (month == 0) {
					tempYear = year - 1;
					tempMonth = 11;

					tempDay = 31;
				} else {
					tempYear = year;
					tempMonth = month - 1;
					// LogN.d(DataTimeUtils.class, "tempMonth:"+tempMonth);
					tempDay = getMaxDay(tempYear, tempMonth + 1);
				}
			} else {
				tempYear = year;
				tempMonth = month;
				tempDay = day - 1;
			}

			int tempHour = hour;
			int tempMinute = minute;
			int tempSecond = second;

			calendar.set(tempYear, tempMonth, tempDay, tempHour, tempMinute,
					tempSecond);
			rTemp = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return rTemp;
	}

}
