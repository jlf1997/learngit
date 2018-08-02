package com.cloud.message.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static long getDayNum(String startTime, String endTime) {
		Date end = null, start = null;

		try {
			end = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
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
	 * 将字符串转时间
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByStr(String str) throws ParseException{
		return new SimpleDateFormat("yyyy-MM-dd").parse(str);
	}
}
