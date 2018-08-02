package com.cloud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	/**
	 * 根据字符串时间 判断天数
	 * @param startTime
	 * @param currentDate
	 * @return
	 */
	public static long getDayNum(String startTime, String currentDate) {
		Date end = null, start = null;
		try {
			end = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
			start = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
		} catch (ParseException e) {
			logger.error("异常类型：" + e.fillInStackTrace());
			e.printStackTrace();
		}
		// 获取相减后天数
		long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}
	/**
	 * 获取两个时间的小时差
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getHourNum(String startTime, String endTime) {
		Date end = null, start = null;
		try {
			end = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(endTime);
			start = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(startTime);
		} catch (ParseException e) {
			logger.error("异常类型：" + e.fillInStackTrace());
			e.printStackTrace();
		}
		    // long ns = 1000;
		    // 获得两个时间的毫秒时间差异
		    long diff = end.getTime() - start.getTime();
		    return diff/3600000;
	}
	
	/**
	 * 获取两个时间的天数差
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getDaysNum(String startTime, String endTime) {
		Date end = null, start = null;
		try {
			end = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(endTime);
			start = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(startTime);
		} catch (ParseException e) {
			logger.error("异常类型：" + e.fillInStackTrace());
			e.printStackTrace();
		}
		    // long ns = 1000;
		    // 获得两个时间的毫秒时间差异
		    long diff = end.getTime() - start.getTime();
		    return diff/3600000/24;
	}
	
	/*   public static void main(String[] args) {
		  long rs =  getDaysNum("2017-03-15 17:39:00",DateUtil.getFormatDate("yyyy-MM-dd HH:mm:SS"));
		  System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:SS"));
		  System.out.println(rs);
	}*/

	/**
	 * 获取当前格式化时间字符串
	 * @return
	 */
	public static String getFormatDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	
	/**
	 * 将字符串转时间
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByStr(String str) throws ParseException{
		return new SimpleDateFormat("yyyy-MM-dd").parse(str);
	}
}
