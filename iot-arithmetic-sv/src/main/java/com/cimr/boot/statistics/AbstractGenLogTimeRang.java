package com.cimr.boot.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.api.statistics.model.FaultLog;

public abstract class AbstractGenLogTimeRang implements GenLogTimeRang{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractGenLogTimeRang.class);
	
	/**
	 * 获取此次统计的开始时间 结束时间 数组0为开始 1为结束
	 * @param type
	 * @return
	 */
	protected  abstract Date[] getTimeRange();
	
	/**
	 * 获取时间范围内的原始数据
	 * @param bTime
	 * @param eTime
	 * @return
	 */
	protected abstract List<Map<String,Object>> getDateFromSource(Date bTime,Date eTime);
	
	/**
	 * 获取未结束的任务
	 * @param type
	 * @return
	 */
	protected abstract List<Map<String,Object>>  getUnfinished();
	
	/**
	 * 处理原始数据列表
	 * @param map
	 * @param terMap
	 * @param finalResult
	 */
	protected abstract void parseFalutList(List<Map<String,Object>> list,List<Map<String,Object>> listun,List<FaultLog> finalResult);

	
	
	/**
	 * 更新时间
	 * @param listun
	 */
	protected abstract void updateDate(List<Map<String, Object>> listun);
	
	/**
	 * 更新时间
	 * @param finalResult
	 */
	protected abstract void update(List<FaultLog> finalResult);


	private ThreadLocal<Date> bthreadLocal = new ThreadLocal<>();
	
	private ThreadLocal<Date> ethreadLocal = new ThreadLocal<>();
	
	
	public final void genLog() {
		//保存最后的结果
		List<FaultLog> finalResult = new ArrayList<>();
		
		//获取需要处理的时间范围
		Date[] range = getTimeRange();
		bthreadLocal.set(range[0]);
		ethreadLocal.set(range[1]);
		
		
		//需要处理的数据
		List<Map<String,Object>> faultMapList = getDateFromSource(getbTime(),geteTime());
		log.debug("new list size:"+faultMapList.size());
		
		//获取未结束的预警信息
		List<Map<String,Object>> listun = getUnfinished();
		log.debug("unfinsh list size:"+listun.size());
		
		//处理数据
		parseFalutList(faultMapList,listun,finalResult);
	
	
		//更新数据
		update(finalResult);
		//更新时间
		updateDate(faultMapList);
		
	}


	protected Date getbTime() {
		return bthreadLocal.get();
		
	}

	protected Date geteTime() {
		return ethreadLocal.get();
	}


	
	
}
