package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.cimr.api.statistics.model.FaultLog;

public interface FaultLogGen {
	
	
	public String getTerId(Map<String, Object> map);
	
	public String getCode(Map<String, Object> map);
	
	public Date getTime(Map<String, Object> map);
	
	public ObjectId  getOrgId(Map<String, Object> map);

	/**
	 * 获取此次统计的开始时间 结束时间 数组0为开始 1为结束
	 * @param type
	 * @return
	 */
	public  Date[] getTimeRange(Integer type);
	

	
	/**
	 * 获取未结束的任务
	 * @param type
	 * @return
	 */
	public  List<Map<String,Object>>  getUnfinished(Integer type);
	
	/**
	 * 处理单个原始数据
	 * @param map
	 * @param terMap
	 * @param finalResult
	 */
	public  void parseFalutMap(Map<String,Object> map,Map<String,Map<String,FaultLog>> terMap,List<FaultLog> finalResult);

	
	public  void doLastResult(List<FaultLog> finalResult,Map<String,Map<String,FaultLog>> terMap);
	
	/**
	 * 更新日期
	 * @param type
	 * @param listun
	 */
	public  void updateDate(Integer type, List<Map<String, Object>> listun);
	
	
	
	public  Map<String,Map<String,FaultLog>> getTerMap(List<Map<String,Object>> unfinishedList);
	
	/**
	 * 构造错误对象
	 * @return
	 */
	public FaultLog getNewFaultLog(ObjectId orgId,Date bTime,String code,String terId);
}
