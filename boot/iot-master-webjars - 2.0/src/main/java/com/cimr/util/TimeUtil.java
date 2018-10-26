package com.cimr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具类
 * @author Administrator
 *
 */
public class TimeUtil {
	
	/**
	 * 1天的毫秒数
	 */
	public static Long DAY_1=86400000L;
	

	/**
	 * 获取某天的年份
	 * @param date
	 * @return
	 */
	public static String getYear(Date date){
		DateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}
	
	public static String getYearAndMonth(Date date){
		DateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}
	
	public static String getMonth(Date date){
		DateFormat format = new SimpleDateFormat("MM");
		return format.format(date);
	}
	
	public static String getDay(Date date){
		DateFormat format = new SimpleDateFormat("dd");
		return format.format(date);
	}
	
	public static Long dateToLong(Date date) {
		if(date==null) {
			return null;
		}
		return date.getTime();
	}
	
	/**
	 * 获取一天开始时间
	 * @return
	 */
	public static Date getStartTime(Date date) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 000);
		return todayStart.getTime();
	}
 
	/**
	 * 获取一天结束时间
	 * @return
	 */
	public static Date getEndTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
	
	/**
	 * 判断 2个日期相差的年份 2017-12-23 与 2018-01-03 返回1 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getYearSpan(Date date1,Date date2) {
		int year1 = Integer.parseInt(getYear(date1));
		int year2 = Integer.parseInt(getYear(date2));
		return year2-year1;
		
	}
	
	public static int getMonthSpan(Date date1,Date date2) {
		int year1 = Integer.parseInt(getYear(date1));
		int year2 = Integer.parseInt(getYear(date2));
		int year = year2-year1;
		int month1 = Integer.parseInt(getMonth(date1));
		int month2 = Integer.parseInt(getMonth(date2));
		return year*12+month2-month1;
	}
	
	/**
	 * 获取当年的最后一天
	 * @param date
	 * @return
	 */
	public static Date getTheLastDayOfYear(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		date = getTheNextYear(date);
		todayEnd.setTime(date);
		todayEnd.set(Calendar.DAY_OF_YEAR,0);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
	
	public static Date getTheLastDayOfMonth(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.MONTH, 1);
		todayEnd.set(Calendar.DAY_OF_MONTH,0);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
	
	public static Date getTheFirstDayOfMonth(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
//		todayEnd.add(Calendar.MONTH, 1);
		todayEnd.set(Calendar.DAY_OF_MONTH,1);
		todayEnd.set(Calendar.HOUR_OF_DAY, 0);
		todayEnd.set(Calendar.MINUTE, 0);
		todayEnd.set(Calendar.SECOND, 0);
		todayEnd.set(Calendar.MILLISECOND, 0);
		return todayEnd.getTime();
	}
	
	public static Date getTheFirstDayOfYear(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
//		todayEnd.add(Calendar.MONTH, 1);
		todayEnd.set(Calendar.DAY_OF_YEAR,1);
		todayEnd.set(Calendar.HOUR_OF_DAY, 0);
		todayEnd.set(Calendar.MINUTE, 0);
		todayEnd.set(Calendar.SECOND, 0);
		todayEnd.set(Calendar.MILLISECOND, 0);
		return todayEnd.getTime();
	}
	
	public static Date getDay(int day) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, day);
		return now.getTime();
	}
	
	public static Date getHour(Date date,int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.HOUR_OF_DAY, hour);
		return now.getTime();
	}
	
	public static Date getMinute(Date date,int minutre) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, minutre);
		return now.getTime();
	}
	
	public static Date getSecond(Date date,int second) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.SECOND, second);
		return now.getTime();
	}
	
	/**
	 * 获取一年前
	 * @param date
	 * @return
	 */
	public static Date getTheLastYear(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.YEAR, -1);
		return todayEnd.getTime();
	}
	
	public static Date getTheLastMonth(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.MONTH, -1);
		return todayEnd.getTime();
	}

	/**
	 * 获取下一年
	 * @param date
	 * @return
	 */
	public static Date getTheNextYear(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.YEAR, 1);
		return todayEnd.getTime();
	}
	/**
	 * 获取下一秒
	 * @param date
	 * @return
	 */
	public static Date getNextSecord(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.SECOND, 1);
		return todayEnd.getTime();
	}
	
	public static Date getNextDay(Date date) {
		if(date==null) {
			return null;
		}
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.DAY_OF_MONTH, 1);
		return todayEnd.getTime();
	}
	
	/**
	 * 日期是否是昨天
	 * @param date
	 * @return
	 */
	public static boolean isYesterday(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.add(Calendar.DAY_OF_MONTH, -1);
		return isInDay(date,todayEnd.getTime());
		
	}
	
	/**
	 * 日期是否是今天
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		
			return isInDay(date,new Date());
		
	}
	
	/**
	 * 判断时间是否在给定的日期当天
	 * @param date
	 * @param range
	 * @return
	 */
	public static boolean isInDay(Date date,Date range) {
		Long t1 = getStartTime(range).getTime();
		Long t2 = getEndTime(range).getTime();
		if(date.getTime()>t1 && date.getTime()<t2) {
			return true;
		}
		return false;
	}
	
	
}
