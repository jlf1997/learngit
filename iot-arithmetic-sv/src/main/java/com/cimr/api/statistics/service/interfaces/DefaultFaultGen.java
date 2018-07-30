package com.cimr.api.statistics.service.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.dao.StaticsticsLogDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.boot.utils.IdGener;
import com.cimr.boot.utils.TimeUtil;


public abstract class DefaultFaultGen extends AbstractFaultLogGen{
	
	
	/**
	 * 解析具体的每一个历史记录
	 * @param map
	 * @param falutMap
	 * @param finalResult
	 */
	protected abstract void parseFaultLog(Map<String, Object> map,Map<String,FaultLog> falutMap,List<FaultLog> finalResult);
	
	private static final Logger log = LoggerFactory.getLogger(DefaultFaultGen.class);
	

	@Autowired
	private FaultLogDao faultLogDao;
	
	@Autowired
	private StaticsticsLogDao staticsticsLogDao ;
	
	

	@Override
	protected Date[] getTimeRange(Integer type) {
		
		//记录是否是之前的 故障延续
		//查询出存在异常的记录
		//统计结束时间
		Date faultEndDate = new Date();
		//上次统计时间
		Date faultStartTime = staticsticsLogDao.getFaultDate(type);
		//第一次统计 
		if(faultStartTime==null) {
			faultStartTime = TimeUtil.getTheLastYear(new Date());
		}else {
			faultStartTime = TimeUtil.getNextSecord(faultStartTime);
		}
		if(TimeUtil.getYearSpan(faultStartTime, faultEndDate)>=1) {
			faultEndDate = TimeUtil.getTheLastYear(faultEndDate);
			faultEndDate = TimeUtil.getTheLastDayOfYear(faultEndDate);
		}
		log.debug("begin get the log:"+faultStartTime+"~"+faultEndDate);
		return new Date[]{faultStartTime,faultEndDate};
	}

	@Override
	protected List<Map<String, Object>> getUnfinished(Integer type) {
		
		return faultLogDao.getUnfininshLog(getbTime(), type);
	}

	@Override
	protected void parseFalutMap(Map<String, Object> map, Map<String, Map<String, FaultLog>> terMap,
			List<FaultLog> finalResult) {
		String terId = getTerId(map);
		Map<String,FaultLog> falutMap = terMap.get(terId);
		if(falutMap==null) {
			//初始化
			falutMap = new HashMap<>();
			terMap.put(terId, falutMap);
		}
		parseFaultLog(map,falutMap,finalResult);
	}

	@Override
	protected void doLastResult(List<FaultLog> finalResult, Map<String, Map<String, FaultLog>> terMap) {
		Iterator<String> iterator = terMap.keySet().iterator();
		while(iterator.hasNext()) {
			String terId = iterator.next();
			Map<String,FaultLog> faultMap = terMap.get(terId);
			Iterator<String> iteratorTer = faultMap.keySet().iterator();
			while(iteratorTer.hasNext()) {
				FaultLog faultLog = faultMap.get(iteratorTer.next());
				//错误未结束
				faultLog.setEndTime(null);
				finalResult.add(faultLog);
			}
			
		}
		
	}

	@Override
	protected void updateDate(Integer type,List<Map<String, Object>> listun) {
		if(listun.size()>0) {
			staticsticsLogDao.updateDate(type,getTime(listun.get(listun.size()-1)));
		}else {
			//已经超过一天 则默认没有数据
			if(new Date().getTime()-geteTime().getTime()>TimeUtil.DAY_1) {
				staticsticsLogDao.updateDate(type,geteTime());
			}else {
				staticsticsLogDao.updateDate(type,null);
			}
		}
		
	}

	/**
	 * 格式化数据
	 */
	@Override
	protected Map<String, Map<String, FaultLog>> getTerMap(List<Map<String, Object>> unfinishedList) {
		// TODO Auto-generated method stub
		Map<String,Map<String,FaultLog>>  rs = new HashMap<>();
		 for(Map<String,Object> masp :unfinishedList) {
			 String terId = masp.get("terId").toString();
			 String code = masp.get("code").toString();
			 Map<String,FaultLog> terMap = rs.get(terId);
			 if(terMap==null) {
				 terMap = new HashMap<>();
			 }
			FaultLog log = terMap.get(code);
			if(log==null) {
				log = new FaultLog(masp);
				log.setYear(TimeUtil.getYear(log.getbTime()));
			}			 
			terMap.put(code, log);
			rs.put(terId, terMap);
		 }
		 return rs;
	}
	
	

	/**
	 * 保存数据
	 */
	@Override
	protected void update(List<FaultLog> finalResult) {
		List<FaultLog> updList = getPreList(finalResult);
		faultLogDao.saveByYear(finalResult);
		faultLogDao.updateYear(updList);
	}
	
	/**
	 * 分离新数据与之前数据
	 * @param resutl
	 * @return
	 */
	private List<FaultLog> getPreList(List<FaultLog> resutl){
		List<FaultLog> updList = new ArrayList<>();
		Iterator<FaultLog> iterator = resutl.iterator();
		FaultLog faultLog;
		while(iterator.hasNext()) {
			faultLog = iterator.next();
			if(faultLog.getYear()!=null) {
				updList.add(faultLog);
				iterator.remove();
			}else {
				String year = TimeUtil.getYear(faultLog.getbTime());
				faultLog.setYear(year);
			}
		}
		return updList;
	}
	

	
	/**
	 * 生成id
	 * @return
	 */
	protected final Long getId() {
		return IdGener.getInstance().getNormalId();
	}

}
