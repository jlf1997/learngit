package com.cimr.api.statistics.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlcFaultService  {

	@Autowired
	private FaultLogService faultLogService;
	
	
	
	/**
	 * 处理plc故障
	 * @param faultMap
	 */
	public void get(List<Map<String,Object>> faultMapList) {
		//记录是否是之前的故障延续
		
	}
	
	
	/**
	 * 
	 * @param faultMap
	 */
	private void ff(Map<String,Object> faultMap) {
		
	}
	
}
