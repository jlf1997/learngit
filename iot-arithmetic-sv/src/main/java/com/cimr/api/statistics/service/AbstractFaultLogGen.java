package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsticsLog;

public class AbstractFaultLogGen {

	
//	public void genDailyFaultLog(Date faultTime) {
//		//保存最后的结果
//		List<FaultLog> finalResult = new ArrayList<>();
//		Date[] range = faultLogService.getDefaultTimeRange(StaticsticsLog.PLC_FAULT_TYPE);
//		
//		//需要处理的数据
//		List<Map<String,Object>> faultMapList = telLogService.findAllByDay(range[0],range[1]);
//		
//		//获取未结束的预警信息
//		List<Map<String,Object>> listun = faultLogDao.getUnfininshLog(range[1], FaultLog.TERERROR);
//		log.debug("unfinsh list size:"+listun.size());
//		
//		//格式化预警信息
//		Map<String,Map<String,FaultLog>> terMap = faultLogDao.getPlcMap(listun);
//		log.debug("finish get the map");
//	
//	
//		for(Map<String,Object> map:faultMapList) {
//			parseFalutMap(map,terMap,finalResult);
//		}
//		doLastResult(finalResult,terMap);
//		faultLogDao.saveByYear(finalResult);
//		List<FaultLog> updList = faultLogService.getPreList(finalResult);
//		faultLogDao.updateYear(updList);
//		//更新扫描时间
//		staticsticsLogDao.updateDate(StaticsticsLog.TER_FAULT_TYPE);
//	}
}
