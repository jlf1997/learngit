package com.cimr.api.statistics.service.gen.simpleStatistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.service.FaultLogService;
import com.cimr.api.statistics.service.interfaces.statisticsDailyGen.StaticsicsGen;
import com.cimr.boot.utils.BootBeanUtils;
/**
 * 生成预警信息统计：以日为单位进行统计
 * @author Administrator
 *
 */
@Service
public class FaultDailyGen extends StaticsicsGen{
	
	@Autowired
	private FaultLogService faultLogService;

	@Override
	protected List<Map<String, Object>> getDateFromSource(Date bTime, Date eTime) {
		List<Map<String, Object>> list =  (List<Map<String, Object>>) faultLogService.findAll(bTime.getTime(),eTime.getTime(),null,null,null).getData();
		return list;  
			  
	
	}
	
	@Override
	protected Long getCount(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return faultLogService.getCount(bTime,eTime);
	}


	@Override
	protected String getTimeSaveType() {
		return "fault_s";
	}


	@Override
	protected void initMap(Map<String, Object> staticsicsMap,Map<String,Object> map) {
		String code = map.get("warningKey").toString();
		staticsicsMap.put(code, 0);
	}

	@Override
	protected void addMap(Map<String, Object> staticsicsMap, Map<String, Object> map) {
		String code = map.get("warningKey").toString();
		if(!staticsicsMap.containsKey(code)) {
			staticsicsMap.put(code, 1);
		}else {
			int num = (int) staticsicsMap.get(code);
			staticsicsMap.put(code, num+1);
		}
	}

	@Override
	protected String getTerId(Map<String, Object> map) {
		return map.get("terId").toString();
	}

	@Override
	protected Date getTime(Map<String, Object> map) {
		return (Date) map.get("bTime");
	}

	@Override
	protected String getCollectionName() {
		return DbNameSetting.getFaultStatic();
	}


	



	

}
