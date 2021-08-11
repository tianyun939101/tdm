package com.demxs.tdm.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		if (date == null) {
			return  "";
		}
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		//long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		//long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+"天":"")+hour+"小时"+min+"分钟";//+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取两个日期之间的小时
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceHoursOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60.0 * 60);
	}

	/**
	 *
	 * @Description:比较两个时间点
	 * 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值；
	 * 如果此 firstDate 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值；
	 * 如果此 firstDate 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
	 * @param firstDate
	 * @param secondDate
	 * @ReturnType int
	 * @author:
	 * @Created 2012 2012-9-20上午08:34:33
	 */
	public static int compare(Date firstDate, Date secondDate) {

		Calendar firstCalendar = null;
		/**使用给定的 Date 设置此 Calendar 的时间。**/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/**使用给定的 Date 设置此 Calendar 的时间。**/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try {
			/**
			 * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。
			 * 如果参数表示的时间等于此 Calendar 表示的时间，则返回 0 值；
			 * 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值；
			 * 如果此 Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
			 * **/
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}


    /**
     * 转换毫秒数 用于页面展示
     * @param object
     * @return
     */
	public static String getLongByDate(Object object){
		if(object!=null){
			if(object instanceof Date){
				Date d = (Date) object;
				return String.valueOf(d.getTime());
			}else{
				return String.valueOf(parseDate(object).getTime());
			}
		}else{
			return "";
		}

	}


	public static Date getFirstDayByMonth(){
		Calendar cale = null;
		cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		return parseDate(firstday);

	}

	public static Date getLastDayByMonth(){
		Calendar cale = null;
		cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		return parseDate(lastday);

	}

	public static String formatDateByPattern(Date date,String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	public static String getCron(java.util.Date  date){
		String dateFormat="ss mm HH dd MM ? yyyy";
		return formatDateByPattern(date, dateFormat);
	}


	public static Date getBeforeHourTime(Date date,int ihour){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);
		return calendar.getTime();
	}

	public static Date getAfterHDayTime(Date date,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
		return calendar.getTime();
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//		System.out.println(getFirstDayByMonth());
//		System.out.println(getLastDayByMonth());


		String c = "2018-3-22 14:31:17";

		String a = "2018-3-22 14:31:17";

		String b = "2018-3-27 10:51:00";
		System.out.println(DateUtils.getDistanceHoursOfTwoDate(parseDate(a),parseDate(b)));

		System.out.println(Math.round(DateUtils.getDistanceHoursOfTwoDate(parseDate(a),parseDate(b))));


		System.out.println(Math.round(DateUtils.getDistanceHoursOfTwoDate(parseDate(a),parseDate(b))*12));





	}
}
