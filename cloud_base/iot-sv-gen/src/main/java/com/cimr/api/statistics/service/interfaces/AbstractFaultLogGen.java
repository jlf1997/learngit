package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.api.statistics.model.FaultLog;
import com.cimr.util.AbstractGenLogTimeRang;

public abstract class AbstractFaultLogGen extends AbstractGenLogTimeRang{
	


	/**
	 * 获取此次统计的开始时间 结束时间 数组0为开始 1为结束
	 * @param type
	 * @return
	 */
	protected abstract  Date[] getTimeRange(Integer type);
	

	
	/**
	 * 获取未结束的任务
	 * @param type
	 * @return
	 */
	protected abstract List<Map<String,Object>>  getUnfinished(Integer type);
	
	/**
	 * 处理单个原始数据
	 * @param map
	 * @param terMap
	 * @param finalResult
	 */
	protected abstract void parseFalutMap(Map<String,Object> map,Map<String,Map<String,Object>> terMap,List<Object> finalResult);

	
	protected abstract void doLastResult(List<Object> finalResult,Map<String,Map<String,Object>> terMap);
	

	
	
	
	protected abstract Map<String,Map<String,Object>> getTerMap(List<Map<String,Object>> unfinishedList);
	
	/**
	 * 构造错误对象
	 * @return
	 */
	protected abstract FaultLog getNewFaultLog(ObjectId orgId,Date bTime,String code,String terId);
	
	private static final Logger log = LoggerFactory.getLogger(AbstractFaultLogGen.class);
	
	
	protected Integer type;

	protected String terminalNo = "terminalNo";
	
	protected String code = "code";
	
	protected String faultTime = "faultTime";
	

		
	
	
	@Override
	protected final Date[] getTimeRange() {
		// TODO Auto-generated method stub
		return getTimeRange(type);
	}


	@Override
	protected final void parseFalutList(List<Map<String, Object>> list, List<Map<String, Object>> listun,
			List<Object> finalResult) {
		Map<String,Map<String,Object>> terMap = getTerMap(listun);
		for(Map<String,Object> map:list) {
			parseFalutMap(map,terMap,finalResult);
		}
		doLastResult(finalResult,terMap);
	}


	@Override
	protected final List<Map<String, Object>> getUnfinished() {
		// TODO Auto-generated method stub
		return getUnfinished(type);
	}



	/**
	 * 获取终端编号
	 */
	protected String getTerId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return map.get(terminalNo).toString();
	}

	/**
	 * 获取错误码
	 */
	protected  String getCode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return map.get(code).toString();
	}

	/**
	 * 获取原始数据id
	 */
	protected final ObjectId getOrgId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (ObjectId) map.get("_id");
	}
	
	/**
	 * 获取错误时间
	 * @param faultMap
	 * @return
	 */
	protected Date getTime(Map<String,Object> map) {
		return ((Date)map.get(faultTime));
	}




	
	

	

}
