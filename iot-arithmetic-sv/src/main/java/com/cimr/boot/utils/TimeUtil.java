package com.cimr.boot.utils;

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
	 * 1个月的毫秒数
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
		return year1-year2;
		
	}

	
	
}
