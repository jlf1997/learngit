package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.statistics.AbstractGenLogTimeRang;

public abstract class AbstractFaultLogGen extends AbstractGenLogTimeRang implements FaultLogGen{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractFaultLogGen.class);
	
	
	protected Integer type;
//	
	protected String terminalNo = "terminalNo";
	
	protected String code = "code";
	
	protected String faultTime = "faultTime";
		
	
//	public AbstractFaultLogGen(Integer type) {
//		this.type = type;
//	}
	
	
	@Override
	public final Date[] getTimeRange() {
		// TODO Auto-generated method stub
		return getTimeRange(type);
	}


	@Override
	public final void parseFalutList(List<Map<String, Object>> list, List<Map<String, Object>> listun,
			List<FaultLog> finalResult) {
		Map<String,Map<String,FaultLog>> terMap = getTerMap(listun);
		for(Map<String,Object> map:list) {
			parseFalutMap(map,terMap,finalResult);
		}
		doLastResult(finalResult,terMap);
	}


	@Override
	public final List<Map<String, Object>> getUnfinished() {
		// TODO Auto-generated method stub
		return getUnfinished(type);
	}

	@Override
	public final void updateDate(List<Map<String, Object>> listun) {
		// TODO Auto-generated method stub
		updateDate(type,listun);
	}

	/**
	 * 获取终端编号
	 */
	@Override
	public String getTerId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return map.get(terminalNo).toString();
	}

	/**
	 * 获取错误码
	 */
	@Override
	public  String getCode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return map.get(code).toString();
	}

	/**
	 * 获取原始数据id
	 */
	@Override
	public final ObjectId getOrgId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (ObjectId) map.get("_id");
	}
	
	/**
	 * 获取错误时间
	 * @param faultMap
	 * @return
	 */
	@Override
	public Date getTime(Map<String,Object> map) {
		return ((Date)map.get(faultTime));
	}

	
	

	

}
