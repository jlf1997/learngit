package com.cimr.boot.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.model.FaultLog;

public abstract class AbstractGenLogTimeRang implements GenLogTimeRang{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractGenLogTimeRang.class);


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


	public Date getbTime() {
		return bthreadLocal.get();
		
	}


	


	public Date geteTime() {
		return ethreadLocal.get();
	}


	
	
	
}
