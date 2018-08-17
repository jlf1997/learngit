package com.cimr.api.statistics.service.gen.simpleStatistic;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.statistics.service.interfaces.RealDateSignalGen;


/**
 * 生成油耗等的统计数据，以日为单位
 * @author Administrator
 *
 */
@Service
public class RealDateSignalOilGen extends RealDateSignalGen{
	
	
	private static final Logger log = LoggerFactory.getLogger(RealDateSignalOilGen.class);

	
	@Autowired
	private ProjectPropertities projectPropertities;
	

	@Override
	protected String getSignal() {
		return projectPropertities.getSingalOil();
	}


	@Override
	protected void initMap(Map<String, Object> staticsicsMap, Map<String, Object> map) {
		staticsicsMap.put("FQ_DAY_OIL", 0);
		staticsicsMap.put("KQ_DAY_WORK", 0);
		staticsicsMap.put("K_ENG_WORK", 0);
		staticsicsMap.put("K_DEV_WORK", 0);
	}


	@Override
	protected void addMap(Map<String, Object> staticsicsMap, Map<String, Object> map) {
		//日油耗量
		pushTask("FQ_DAY_OIL",staticsicsMap,map);
		//日工作量
		pushTask("KQ_DAY_WORK",staticsicsMap,map);
		
		pushTask("K_ENG_WORK",staticsicsMap,map);
		
		pushTask("K_DEV_WORK",staticsicsMap,map);
	}
	
	@Override
	protected Date getTime(Map<String, Object> map) {
		return (Date) map.get("insertTime");
	}
	
	@Override
	protected  String getTerId(Map<String, Object> map) {
		return map.get("terminalNo").toString();
	}

	
	/**
	 * 处理累计量
	 * @param key
	 * @param staticsicsMap
	 * @param map
	 */
	private void pushTask(String key,Map<String, Object> staticsicsMap, Map<String, Object> map) {
		Long value = 0L;
		if(map.containsKey(key)) {
			value = Long.parseLong(map.get(key).toString());
			
		}else {
			log.warn(key+" error");
		}
		Long value_IN = 0L;
		if(staticsicsMap.containsKey(key)) {
			value_IN = Long.parseLong(staticsicsMap.get(key).toString());
		}
		if(value_IN<value) {
			staticsicsMap.put(key, value);
		}
	}

	
}
