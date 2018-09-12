package com.cimr.api.statistics.service.interfaces.statisticsDailyGen;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.api.statistics.dao.StaticsticsLogDao;
import com.cimr.api.statistics.service.interfaces.AbstractDailyGen;
import com.cimr.boot.utils.TimeUtil;


/**
 * 日统计类型 统计生成类
 * @author Administrator
 *
 */
public abstract class StaticsicsGen extends AbstractDailyGen{
	
	@Autowired
	private StaticsticsLogDao staticsticsLogDao;
	
	@Override
	protected void parseFalutList(List<Map<String, Object>> list, List<Map<String, Object>> listun,
			List<Object> finalResult) {
		Map<String,Map<String,Object>> terMap = new HashMap<>();
		for(Map<String,Object> map:list) {
			String terId = getTerId(map);
			Date time = getTime(map);
			Map<String,Object> staticsicsMap = terMap.get(terId);
			if(staticsicsMap==null) {
				//初始化
				staticsicsMap = new HashMap<>();
				staticsicsMap.put("day",TimeUtil.getDay(time));
				staticsicsMap.put("year",TimeUtil.getYear(time));
				staticsicsMap.put("month",TimeUtil.getMonth(time));
				staticsicsMap.put("logTime",time);
				staticsicsMap.put("terId", terId);
				initMap(staticsicsMap,map);
				terMap.put(terId, staticsicsMap);
			}
			addMap(staticsicsMap,map);
		}
		Iterator<String> iterator = terMap.keySet().iterator();
		while(iterator.hasNext()) {
			Map<String,Object> staticsicsMap = terMap.get(iterator.next());
			finalResult.add(staticsicsMap);
		}
		
	}

	/**
	 * 初始化统计量
	 * @param staticsicsMap
	 */
	protected abstract void initMap(Map<String, Object> staticsicsMap,Map<String,Object> map) ;

	/**
	 * 处理需要统计的数据
	 * @param staticsicsMap
	 * @param map
	 */
	protected abstract void addMap(Map<String, Object> staticsicsMap, Map<String, Object> map);

	/**
	 * 获取终端id
	 * @param map
	 * @return
	 */
	protected abstract String getTerId(Map<String, Object> map);
	/**
	 * 获取记录开始时间
	 * @param map
	 * @return
	 */
	protected abstract Date getTime(Map<String, Object> map);
	/**
	 * 获取统计记录保存的库名
	 * @return
	 */
	protected abstract String getCollectionName();

	

	@Override
	protected void update(List<Object> finalResult) {
		// TODO Auto-generated method stub
		if(finalResult.size()>0) {
			staticsticsLogDao.save(finalResult, getCollectionName());
		}
	}

	
}
