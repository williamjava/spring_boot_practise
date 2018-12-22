package com.gui.star.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程安全的日期工具类
 * 
 * @author wuhoujian
 * 
 */
@Slf4j
public class DateUtil {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_NO_SEPARTOR = "yyyyMMdd";
	public static final String DATE_FORMAT_ORIGINAL = "EEE MMM dd HH:mm:ss zzz yyyy";
	public static final String DATE_FORMAT_CHINESE_MM_DD = "MM月dd日";
	public static final String FMT_Y_M = "yyyy-MM";
	public static final String FMT_M_D = "MM/dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT_YY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String DATETIME_FORMAT_YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DAYTIME_FORMAT = "dd HH:mm:ss";
	public static final String DATETIME_VALUE_BEGIN = " 00:00:00";
	public static final String DATETIME_VALUE_END = " 23:59:59";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmssSSS";

	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMM");
		}
	};

	private static ThreadLocal<DateFormat> DATE_LOCAL = new ThreadLocal<>();

	/**
	 * 获取今天的0点0时0分
	 * 
	 * @return date
	 */
	public static Date getTodayStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);

		return todayStart.getTime();
	}

	/**
	 * 获取今天的23点59时59分59秒
	 * 
	 * @return date
	 */
	public static Date getTodayEndTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		todayStart.set(Calendar.MILLISECOND, 0);

		return todayStart.getTime();
	}

	/**
	 * 获取今天的0点0时0分
	 * 
	 * @return long
	 */
	public static long getTodayStartTimeInMillis() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);

		return todayStart.getTime().getTime();
	}

	/**
	 * 只能格式化为yyyyMM的格式
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return threadLocal.get().format(date);
	}

	/**
	 * 按照指定的形式格式化化日期
	 * 
	 * @param date
	 *            日期对象
	 * @param formatPattern
	 *            指定的日期格式化形式
	 * @return
	 */
	public static String format(Date date, String formatPattern) {
		DATE_LOCAL.set(new SimpleDateFormat(formatPattern));

		return DATE_LOCAL.get().format(date);
	}

	/**
	 * Vue组件时间格式特殊处理方法
	 * 
	 * 2015-12-7T16:00:00.000Z
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date toDate(String dateStr, String formatStr) {
		Date d = null;

		dateStr = dateStr.replace("Z", " UTC");// 注意是空格+UTC
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			d = format.parse(dateStr);
		} catch (ParseException e) {
			log.error("时间转化异常，异常信息为：", e);
		}

		return d;
	}

	/**
	 * 按照"yyyy-MM-dd"的格式转换日期字符串为Date类型
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return
	 */
	public static Date toDate(String dateStr) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			date = dateFormat.parse(dateStr);
		} catch (java.text.ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 转换原始日期字符串为Date类型
	 * 
	 * @param dateStr
	 *            原始日期字符串
	 * @return
	 */
	public static Date parseOriginal2Date(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		
		Date date = null;

		SimpleDateFormat sfStart = new SimpleDateFormat(DATE_FORMAT_ORIGINAL, Locale.ENGLISH);
		try {
			date = sfStart.parse(dateStr);
		} catch (ParseException e) {
			log.info("转换原始日期字符串为Date类型出现异常：{}", e.getMessage());
		}

		return date;
	}

	/**
	 * 按照"yyyy-MM-dd HH:mm:ss"的格式转换日期时间字符串为Date类型
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串
	 * @return
	 */
	public static Date toDateTime(String dateTimeStr) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			date = dateFormat.parse(dateTimeStr);
		} catch (java.text.ParseException e) {
			date = null;
		}

		if (date == null) {
			date = toTime(dateTimeStr);
		}
		return date;
	}

	/**
	 * 按照"HH:mm:ss"的格式转换日期时间字符串为Date类型
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串
	 * @return
	 */
	public static Date toTime(String timeStr) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		try {
			date = dateFormat.parse(timeStr);
		} catch (java.text.ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 按照"dd HH:mm:ss"的格式转换日期时间字符串为Date类型
	 * 
	 * @param dateTimeStr
	 *            日期时间字符串
	 * @return
	 */
	public static Date toDayTime(String dayTimeStr) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(DAYTIME_FORMAT);
		try {
			date = dateFormat.parse(dayTimeStr);
		} catch (java.text.ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 获取某段日期的所有日期列表
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String> allDateForX(String startTime, String endTime) {
		List<String> result = new ArrayList<String>();
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		// Calendar的月从0-11
		c_begin.set(getYear(toDate(startTime, DATE_FORMAT)), getMonth(toDate(startTime, DATE_FORMAT)),
				getDay(toDate(startTime, DATE_FORMAT)));
		c_end.set(getYear(toDate(endTime, DATE_FORMAT)), getMonth(toDate(endTime, DATE_FORMAT)),
				getDay(toDate(endTime, DATE_FORMAT)));
		// 结束日期下滚一天是为了包含最后一天
		c_end.add(Calendar.DAY_OF_YEAR, 1);
		while (c_begin.before(c_end)) {
			result.add(format(new java.sql.Date(c_begin.getTime().getTime()), DATE_FORMAT));
			c_begin.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * 返回指定日期是哪一年 如main方法： System.out.println(getYear(new Date())); 结果： 2014
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(1);
	}

	/**
	 * 返回指定日期是哪一天 如main方法： System.out.println(getDay(new Date())); 结果： 25
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(5);
	}

	/**
	 * 求两个日期之间的滞留时长
	 */
	public static double daysBetween(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double seconds = 0l;
		try {
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			// 秒数
			seconds = (endDate.getTime() - startDate.getTime()) / 1000;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return seconds;
	}

	/**
	 * 获取指定日期上一周的星期一
	 * 
	 * @param dateStr
	 *            YYYY-MM-DD
	 */
	public static String lastMonday(String dateStr) {
		return format(addDay(toDate(lastSunday(dateStr), DATE_FORMAT), -6), DATE_FORMAT);
	}

	/**
	 * 获取指定日期上一周的星期一
	 * 
	 * @param dateStr
	 *            Date
	 */
	public static String lastMonday(Date dateStr) {
		return format(addDay(toDate(lastSunday(dateStr), DATE_FORMAT), -6), DATE_FORMAT);
	}

	/**
	 * 获取指定日期上一周的星期天
	 * 
	 * @param date
	 *            YYYY-MM-DD
	 */
	public static String lastSunday(String dateStr) {
		return format(addDay(toDate(dateStr, DATE_FORMAT), -getWeek(toDate(dateStr, DATE_FORMAT))), DATE_FORMAT);
	}

	/**
	 * 获取指定日期上一周的星期天
	 * 
	 * @param date
	 *            Date
	 */
	public static String lastSunday(Date dateStr) {
		return format(addDay(dateStr, -getWeek(dateStr)), DATE_FORMAT);
	}

	/**
	 * 获取指定日期下一周的星期一
	 * 
	 * @param dateStr
	 *            YYYY-MM-DD
	 */
	public static String nextMonday(String dateStr) {
		return format(addDay(toDate(dateStr, DATE_FORMAT), 8 - getWeek(toDate(dateStr, DATE_FORMAT))), DATE_FORMAT);
	}

	/**
	 * 获取指定日期下一周的星期一
	 * 
	 * @param dateStr
	 *            Date
	 */
	public static String nextMonday(Date dateStr) {
		return format(addDay(dateStr, 8 - getWeek(dateStr)), DATE_FORMAT);
	}

	/**
	 * 获取指定日期下一周的星期天
	 * 
	 * @param dateStr
	 *            YYYY-MM-DD
	 */
	public static String nextSunday(String dateStr) {
		return format(addDay(toDate(nextMonday(dateStr), DATE_FORMAT), 6), DATE_FORMAT);
	}

	/**
	 * 获取指定日期下一周的星期天
	 * 
	 * @param dateStr
	 *            Date
	 */
	public static String nextSunday(Date dateStr) {
		return format(addDay(toDate(nextMonday(dateStr), DATE_FORMAT), 6), DATE_FORMAT);
	}

	/**
	 * 获取当前日期的周一
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMondayNow(Date dateStr) {
		String result = "";
		// 获取当前日期的星期
		int weekNow = getWeek(dateStr);
		if (weekNow == 1) {
			result = format(dateStr, DATE_FORMAT);
		} else {
			result = format(addDay(dateStr, -(weekNow - 1)), DATE_FORMAT);
		}
		return result;
	}

	/**
	 * 获取当前日期的星期日
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getSundayNow(Date dateStr) {
		String result = "";
		// 获取当前日期的星期
		int weekNow = getWeek(dateStr);
		if (weekNow == 7) {
			result = format(dateStr, DATE_FORMAT);
		} else {
			result = format(addDay(dateStr, 7 - weekNow), DATE_FORMAT);
		}
		return result;
	}

	/**
	 * 获取当前日期的周一
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getMondayNow(String dateStr) {
		String result = "";
		// 获取当前日期的星期
		int weekNow = getWeek(toDate(dateStr, DATE_FORMAT));
		if (weekNow == 1) {
			result = format(toDate(dateStr, DATE_FORMAT), DATE_FORMAT);
		} else {
			result = format(addDay(toDate(dateStr, DATE_FORMAT), -(weekNow - 1)), DATE_FORMAT);
		}
		return result;
	}

	/**
	 * 获取当前日期的星期日
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getSundayNow(String dateStr) {
		String result = "";
		// 获取当前日期的星期
		int weekNow = getWeek(toDate(dateStr, DATE_FORMAT));
		if (weekNow == 7) {
			result = format(toDate(dateStr, DATE_FORMAT), DATE_FORMAT);
		} else {
			result = format(addDay(toDate(dateStr, DATE_FORMAT), 7 - weekNow), DATE_FORMAT);
		}
		return result;
	}

	/**
	 * 返回指定日期是周几 如main方法： System.out.println(getWeek(new Date())); 结果： 2
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);

		if (week == 1)
			return 7;
		else
			return week - 1;
	}

	/**
	 * 在日期上增加数个整天 如main方法： System.out.println(dateToString(new Date(),
	 * FMT_Y_M_D_H_M_S)); Date dateResult = addDay(new Date(),5);
	 * System.out.println(dateToString(dateResult, FMT_Y_M_D_H_M_S)); 结果：
	 * 2014-11-25 17:07:44 2014-11-30 17:07:44
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * startDate加上days是否小于endate，超过返回true，否则返回false
	 * 
	 * @param startDate
	 * @param endDate
	 * @param days
	 * @return
	 */
	public static boolean compareDate(Date startDate, Date endDate, int days) {
		if (startDate == null || endDate == null) {
			return false;
		}
		return addDays(startDate, days).before(endDate);
	}

	/**
	 * startDate加上days是否小于等于endDate，满足返回true，否则返回false
	 * 
	 * @param startDate
	 * @param endDate
	 * @param days
	 * @return
	 */
	public static boolean compareDateWithEqual(Date startDate, Date endDate, int days) {
		if (startDate == null || endDate == null) {
			return false;
		}
		Date d = addDays(startDate, days);
		int r = d.compareTo(endDate);
		if (r <= 0)
			return true;
		return false;
	}

	/**
	 * 比较两个日期是否相等，年月日十分
	 * 
	 * @param DATE1
	 *            日期1
	 * @param DATE2
	 *            日期2
	 * @return true相等，false不相等x
	 */
	public static boolean compare_date(Date dt1, Date dt2) {
		boolean isEqual = false;

		try {
			int result = dt1.compareTo(removeMillSeconds(dt2, getMillSeconds(dt2)));
			if (result == 0) {
				isEqual = true;
			}
		} catch (Exception e) {
			log.info("比较日期出现异常，异常信息为：", e);
		}

		return isEqual;
	}

	/**
	 * 比较两个日期是否相等，十分
	 * 
	 * @param DATE1
	 *            日期1
	 * @param DATE2
	 *            日期2
	 * @return true相等，false不相等x
	 */
	public static boolean compare_time(Date dt1, Date dt2) {
		boolean isEqual = false;

		try {
			int hour1 = getHour(dt1);
			int minute1 = getMinute(dt1);

			int hour2 = getHour(dt2);
			int minute2 = getMinute(dt2);

			if (hour1 == hour2 && minute1 == minute2) {
				isEqual = true;
			}

		} catch (Exception e) {
			log.info("比较时间出现异常，异常信息为：", e);
		}

		return isEqual;
	}

	/**
	 * 添加年
	 * 
	 * @param date
	 *            日期
	 * @param year
	 *            年
	 * @return
	 */
	public static Date addYearForDate(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	/**
	 * 获取所有的周信息 如果2015-01-01到当前日期小于一年，则获取2015-01-01至当前日期所在的周日时间段内所有周
	 * 如果2015-01-01到当前日期大于一年，则获取当前日期往前推一年之内的所有周
	 * 
	 * @return
	 */
	public static TreeMap<String, String> getAllWeeks() {
		TreeMap<String, String> result = new TreeMap<String, String>();
		// 当前时间
		Date dateNow = toDate(format(new Date(), DATE_FORMAT), DATE_FORMAT);
		// 临界时间
		Date boundaryDate = toDate("2016-01-04", DATE_FORMAT);
		// 比较当前时间和临界时间
		boolean beforeBoundaryDateOrNot = compareDateWithEqual(dateNow, boundaryDate, 0);
		if (beforeBoundaryDateOrNot) {
			// 当前时间在临界时间之前（获取2015-01-01至当前日期所在的星期日这个时间段内的所有星期）
			String startDate = "2015-01-01";
			// 当前日期的星期天
			String endDate = getSundayNow(new Date());
			// 第一个星期（因为2015-01-01不是周一，所以特殊处理）
			result.put(startDate, getSundayNow(startDate));
			// 2015-01-01下一周的周一
			String nextMonday = nextMonday(startDate);
			// 2015-01-01下一周的周日
			String nextSunday = nextSunday(startDate);
			while (compareDate(toDate(nextMonday, DATE_FORMAT), toDate(endDate, DATE_FORMAT), 0)) {
				result.put(nextMonday, nextSunday);
				String mondayNow = nextMonday;
				nextMonday = nextMonday(mondayNow);
				nextSunday = nextSunday(mondayNow);
			}
		} else {
			// 当前时间在临界时间之后
			String startDate = getMondayNow(addYearForDate(new Date(), -1));
			String endDate = getSundayNow(new Date());
			result.put(startDate, getSundayNow(startDate));
			String nextMonday = nextMonday(startDate);
			String nextSunday = nextSunday(startDate);
			while (compareDate(toDate(nextMonday, DATE_FORMAT), toDate(endDate, DATE_FORMAT), 0)) {
				result.put(nextMonday, nextSunday);
				String mondayNow = nextMonday;
				nextMonday = nextMonday(mondayNow);
				nextSunday = nextSunday(mondayNow);
			}
		}
		return result;
	}

	/**
	 * 获取历史13周的周一和周日，当前周是获取周一和当前日期
	 * 
	 * @return
	 */
	public static TreeMap<String, String> thirteenWeeks() {
		TreeMap<String, String> result = new TreeMap<String, String>();
		String monday = lastMonday(new Date());
		for (int i = 1; i < 12; i++) {
			monday = lastMonday(monday);
		}
		// 第一周
		String sunday = getSundayNow(monday);
		result.put(monday, sunday);
		for (int i = 1; i <= 12; i++) {
			monday = nextMonday(monday);
			sunday = getSundayNow(monday);
			result.put(monday, sunday);
		}
		result.put(getMondayNow(new Date()), format(new Date(), DATE_FORMAT));
		return result;
	}

	/**
	 * 获取本年度所有月份的第一天和最后一天日期 最后一个月是月初到当前日期
	 * 
	 * @return
	 */
	public static TreeMap<String, String> getMonthOfThisYear() {
		TreeMap<String, String> result = new TreeMap<String, String>();
		// 获取当前日期所在的年
		int year = getYear(new Date());
		// 获取当前日期所在的月份
		int month = getMonth(new Date()) + 1;
		if (month == 1) {
			result.put(year + "-01-01", format(new Date(), DATE_FORMAT));
		} else {
			for (int i = 0; i < month - 1; i++) {
				if (i == 0) {
					result.put(year + "-01-01", year + "-01-31");
				} else {
					String firstDay = format(addMonthFroDate(toDate(year + "-01-01", DATE_FORMAT), i), DATE_FORMAT);
					String endDay = format(getLastDateForCurrentMonth(toDate(firstDay, DATE_FORMAT)), DATE_FORMAT);
					result.put(firstDay, endDay);
				}
			}
			result.put(format(getFristDateForCurrentMonth(new Date()), DATE_FORMAT), format(new Date(), DATE_FORMAT));
		}
		return result;
	}

	/**
	 * 获取一天的最后一个时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastTimeDate(Date date) {
		if (null == date) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 获取一天的第一个时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstTimeDate(Date date) {
		if (null == date) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 * @throws Exception
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (java.text.ParseException e) {
			log.info("时间转化出现异常，异常信息为：", e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();

		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return new BigDecimal(String.valueOf(between_days)).abs().intValue();
	}

	/**
	 * 计算两个日期之间相差的天数 (包含首尾两端)
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	public static int daysBetween2(Date smdate, Date bdate) throws ParseException, java.text.ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 获取传入月的月初时间 exam（ 参数为2015-03，返回2015-03-01 ）
	 * 
	 * @param date
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String getMinMonthDate(String date) throws java.text.ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat2.format(calendar.getTime());
	}

	/**
	 * 获取传入月的月末时间 exam（ 参数为2015-03，返回2015-03-31 ）
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	public static String getMaxMonthDate(String date) throws java.text.ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat2.format(calendar.getTime());
	}

	/**
	 * 获取传入月的月末时间 exam（ 参数为2015-03，返回2015-03-31 ）
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getMaxMonthDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取传入两个时间的之间所有的月份（ 参数为2015-03-05，2015-09-01 返回
	 * 2015-03，2015-04。。。。。。2015-09 ）
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween(Date minDate, Date maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(minDate);
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.setTime(maxDate);
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 获取传入两个时间的之间所有的月份（ 参数为2015-03-05，2015-09-01 返回
	 * 2015-03，2015-04。。。。。。2015-09 ）
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween2(String minDate, String maxDate)
			throws ParseException, java.text.ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * @description 将年月日转化为日期
	 * @author wuhoujian
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Date toDate(int year, int month, int day) throws java.text.ParseException {
		Date date = null;
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DATE, day);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		date = calender.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(sdf.format(date));
		return date;
	}

	/**
	 * @description 结束日期属于开始日期后的第几个月的日期
	 * @author wuhoujian
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static int monthsFromStartDate(Date startDate, Date endDate) {
		int result = 0;
		Date temp = null;
		startDate = toDate(format(startDate, DATE_FORMAT), DATE_FORMAT);
		endDate = toDate(format(endDate, DATE_FORMAT), DATE_FORMAT);
		// 开始日期 大于 结束日期 两个日期互换 例如： startDate 2013-05-21 endDate = 2013-04-20
		if (startDate.after(endDate)) {
			temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		Date tempEndDate1 = null;
		Date tempEndDate2 = null;
		int a = getDayOfMonth(startDate);
		int b = getDayOfMonth(endDate);
		int c = a - b;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		c2.set(Calendar.DAY_OF_MONTH, a);
		tempEndDate2 = c2.getTime();
		int i = 0;
		while (true) {
			tempEndDate1 = addToMonth(startDate, i);
			if (tempEndDate1.compareTo(tempEndDate2) == 0) {
				result = i;
				break;
			}
			i++;
			if (i == 999999999) {// 防止死循环
				break;
			}
		}
		if (c < 0) {
			result = result + 1;
		}
		return result;
	}

	/**
	 * 获取开始时间与结束时间之间间隔的月数
	 * 
	 * @author wuhoujian
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int monthsBetween(Date startDate, Date endDate) {
		int iMonth = 0;
		try {
			Calendar objCalendarDateStart = Calendar.getInstance();
			objCalendarDateStart.setTime(startDate);
			Calendar objCalendarDateEnd = Calendar.getInstance();
			objCalendarDateEnd.setTime(endDate);
			if (objCalendarDateEnd.equals(objCalendarDateStart) || objCalendarDateStart.after(objCalendarDateEnd)) {
				return 0;
			} else {
				if (objCalendarDateEnd.get(Calendar.YEAR) > objCalendarDateStart.get(Calendar.YEAR)) {
					iMonth = (objCalendarDateEnd.get(Calendar.YEAR) - objCalendarDateStart.get(Calendar.YEAR)) * 12
							+ objCalendarDateEnd.get(Calendar.MONTH) - objCalendarDateStart.get(Calendar.MONTH);
				} else {
					iMonth = objCalendarDateEnd.get(Calendar.MONTH) - objCalendarDateStart.get(Calendar.MONTH);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * 获取输入日期所在月份的第一天
	 * 
	 * @author wuhoujian
	 * @param date
	 * @return
	 */
	public static Date getFristDateForCurrentMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 获取输入日期所在月份的最后一天
	 * 
	 * @author wuhoujian
	 * @param date
	 * @return
	 */
	public static Date getLastDateForCurrentMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * @description 获取某年某月的第一天
	 * @author wuhoujian
	 * @param year
	 *            某年
	 * @param month
	 *            某月
	 * @return
	 */
	public static Date getMonthBegin(int year, int month) {
		Date _month_begin = null;
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		_month_begin = calender.getTime();
		return _month_begin;
	}

	/**
	 * @description 获取某年某月的最后一天
	 * @author wuhoujian
	 * @param year
	 *            某年
	 * @param month
	 *            某月
	 * @return
	 */
	public static Date getMonthEnd(int year, int month) {
		Date month_end = null;
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		month_end = calender.getTime();
		return month_end;
	}

	/**
	 * @description 得到指定月的天数
	 * @author wuhoujian
	 * @param year
	 *            某年
	 * @param month
	 *            某月
	 * @return
	 */
	public static int getMonthLastDay(int year, int month) {
		Calendar calender = Calendar.getInstance();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = calender.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * @description 得到当前日期月的天数
	 * @author wuhoujian
	 * @param date
	 * @return
	 */
	public static int getMonthLastDay(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calender.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = calender.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * @description 得到日期中的月份
	 * @author wuhoujian
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * @description 当月的第几天
	 * @author wuhoujian
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @description 获得当前日期 + N个月 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 * @return
	 */
	public static Date addToMonth(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int month = calOld.get(Calendar.MONTH);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.MONTH, n + month);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * 添加或减少月 wb-tengjingshan
	 * 比如日期为“2015-05-29”，使用此方法往前推迟一个月是“2015-02-28”，使用上面的方法获取的是“2015-03-01”
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonthFroDate(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * @description 获得当前日期 减去 N月 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 * @return
	 */
	public static Date removeMonths(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int month = calOld.get(Calendar.MONTH);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.MONTH, month - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 重置当前日期的月份
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            需要重置的月份
	 * @return 新的Date
	 */
	public static Date resetMonth(Date oldDate, int n) {
		Date newDate = null;

		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.MONTH, n);

		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 重置当前日期的天数
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            需要重置的天
	 * @return 新的Date
	 */
	public static Date resetDay(Date oldDate, int n) {
		Date newDate = null;

		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.DAY_OF_MONTH, n);

		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N周 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 */
	public static Date removeWeek(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int week = calOld.get(Calendar.WEEK_OF_YEAR);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.WEEK_OF_YEAR, week - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N天 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 * @return
	 */
	public static Date removeDays(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int day = calOld.get(Calendar.DAY_OF_YEAR);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.DAY_OF_YEAR, day - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N天分钟 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            多少分钟
	 * @return
	 */
	public static Date removeMinutes(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int minute = calOld.get(Calendar.MINUTE);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.MINUTE, minute - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N秒 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            多少秒
	 * @return
	 */
	public static Date removeSeconds(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int second = calOld.get(Calendar.SECOND);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.SECOND, second - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N毫秒 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            多少毫秒
	 * @return
	 */
	public static Date removeMillSeconds(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int millSecond = calOld.get(Calendar.MILLISECOND);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.MILLISECOND, millSecond - n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获得当前日期 减去 N秒 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 *            多少秒
	 * @return
	 */
	public static int getSeconds(Date oldDate) {
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);

		int second = calOld.get(Calendar.SECOND);

		return second;
	}

	/**
	 * @description 获得当前日期对应的小时
	 * @author wuhoujian
	 * @param oldDate
	 * @return 小时
	 */
	public static int getHour(Date oldDate) {
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);

		int hour = calOld.get(Calendar.HOUR_OF_DAY);

		return hour;
	}

	/**
	 * @description 获得当前日期对应的分钟数
	 * @author wuhoujian
	 * @param oldDate
	 * @return 分钟数
	 */
	public static int getMinute(Date oldDate) {
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);

		int minute = calOld.get(Calendar.MINUTE);

		return minute;
	}

	/**
	 * @description 获得当前日期对应的毫秒数
	 * @author wuhoujian
	 * @param oldDate
	 * @return 毫秒数
	 */
	public static int getMillSeconds(Date oldDate) {
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);

		int millSecond = calOld.get(Calendar.MILLISECOND);

		return millSecond;
	}

	/**
	 * @description 获得当前日期 加上 N天 之后的日期
	 * @author wuhoujian
	 * @param oldDate
	 * @param n
	 * @return
	 */
	public static Date addDays(Date oldDate, int n) {
		Date newDate = null;
		Calendar calOld = Calendar.getInstance();
		calOld.setTime(oldDate);
		int day = calOld.get(Calendar.DAY_OF_YEAR);
		Calendar calNew = Calendar.getInstance();
		calNew.setTime(oldDate);
		calNew.set(Calendar.DAY_OF_YEAR, day + n);
		newDate = calNew.getTime();
		return newDate;
	}

	/**
	 * @description 获取两个年份之间的差值
	 * @author wuhoujian
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int yearsBetween(Date startDate, Date endDate) {
		int iYears = 0;
		Calendar calS = Calendar.getInstance();
		calS.setTime(startDate);
		Calendar calE = Calendar.getInstance();
		calE.setTime(endDate);
		int i = startDate.compareTo(endDate);
		if (i == 1) {
			iYears = calS.get(Calendar.YEAR) - calE.get(Calendar.YEAR);
		} else if (i == -1) {
			iYears = calE.get(Calendar.YEAR) - calS.get(Calendar.YEAR);
		}
		return iYears;
	}

	/**
	 * @param date
	 *            日期
	 * @param offset
	 *            偏移量，0为周日 单位为日
	 * @return WeekOfYear
	 */
	public static int getWeekOfYear(Date date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime() - offset * 24 * 3600 * 1000L);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		return today;
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Long date1, Long date2) {
		int days = (int) ((date2 - date1) / (1000 * 3600 * 24));
		return days;
	}
	
	/**
	 * 获取唯一序列
	 * @return
	 */
	public static String getDateSequence() {
        return new SimpleDateFormat(YYYYMMDDHHMMSS).format(new Date());
    }
	
	/**
	 * 获取一个时间段的所有日期
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<String> findDates(Date dBegin, Date dEnd){
		  List<String> lDate = new ArrayList<String>();
		  SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		  lDate.add(sd.format(dBegin));
		  
		  Calendar calBegin = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calBegin.setTime(dBegin);
		  
		  Calendar calEnd = Calendar.getInstance();
		  // 使用给定的 Date 设置此 Calendar 的时间
		  calEnd.setTime(dEnd);
		  
		  // 测试此日期是否在指定日期之后
		  while (dEnd.after(calBegin.getTime()))
		  {
			   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			   calBegin.add(Calendar.DAY_OF_MONTH, 1);
			   lDate.add(sd.format(calBegin.getTime()));
		  }
		  
		  return lDate;
	 }
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
		System.out.println(addDay(new Date(), 3).getTime());
	}
}
