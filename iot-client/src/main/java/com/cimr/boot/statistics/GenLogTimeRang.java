package com.cimr.boot.statistics;

import java.util.Date;

public interface GenLogTimeRang {
		
	/**
	 * 生成历史记录
	 */
	public  void genLog();
	
	public  void genLog(Date bTime,Date eTime);
	
	
	/**
	 * 以天为周期生成日志
	 * @param date
	 */
	public void genLogDaily(Date date);

}
