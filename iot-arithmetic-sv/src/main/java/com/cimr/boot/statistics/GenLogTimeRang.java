package com.cimr.boot.statistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cimr.api.statistics.model.FaultLog;

public interface GenLogTimeRang {
		
	/**
	 * 获取此次统计的开始时间 结束时间 数组0为开始 1为结束
	 * @param type
	 * @return
	 */
	public  Date[] getTimeRange();
	
	/**
	 * 获取时间范围内的原始数据
	 * @param bTime
	 * @param eTime
	 * @return
	 */
	public  List<Map<String,Object>> getDateFromSource(Date bTime,Date eTime);
	
	/**
	 * 获取未结束的任务
	 * @param type
	 * @return
	 */
	public  List<Map<String,Object>>  getUnfinished();
	
	/**
	 * 处理原始数据列表
	 * @param map
	 * @param terMap
	 * @param finalResult
	 */
	public  void parseFalutList(List<Map<String,Object>> list,List<Map<String,Object>> listun,List<FaultLog> finalResult);

	
	
	
	public  void updateDate(List<Map<String, Object>> listun);
	
	public  void update(List<FaultLog> finalResult);
	
	

}
