package com.cimr.api.statistics.service;

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
import org.springframework.stereotype.Service;

import com.cimr.api.history.dao.RealDataFalutHistoryDao;
import com.cimr.api.log.service.TelLogService;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.dao.StaticsticsLogDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.boot.utils.IdGener;
import com.cimr.boot.utils.TimeUtil;

/**
 * 终端错误处理类
 * @author Administrator
 *
 */
@Service
public class TerminalFaultService {
	
	
	private static final Logger log = LoggerFactory.getLogger(TerminalFaultService.class);

	
	/**
	 * 错误发生回传周期，单位毫秒
	 */
	private static Long timePro = 10000L;
	
	/**
	 * 默认错误结束时长，单位毫秒
	 */
	private static Long defaultTime = timePro;

	
	@Autowired
	private TelLogService telLogService;
	
	@Autowired
	private FaultLogDao faultLogDao;
	
	@Autowired
	private FaultLogService faultLogService;
	
	
	
	@Autowired
	private RealDataFalutHistoryDao realDataFalutHistoryDao;
	
	@Autowired
	private StaticsticsLogDao staticsticsLogDao;
	
	
	
	public void f(int pageNumber, int pageSize) {
		
	}
	
	
	/**
	 * 生成给定日期当天的终端错误日志统计
	 * @param faultTime
	 */
	public void genDailyFaultLog() {
		//保存最后的结果
		List<FaultLog> finalResult = new ArrayList<>();
		Date[] range = faultLogService.getDefaultTimeRange(StaticsticsLog.TER_FAULT_TYPE);
		
		//需要处理的数据
		List<Map<String,Object>> faultMapList = telLogService.findAllByDay(range[0],range[1]);
		log.debug("new list size:"+faultMapList.size());
		
		//获取未结束的预警信息
		List<Map<String,Object>> listun = faultLogDao.getUnfininshLog(range[1], FaultLog.TERERROR);
		log.debug("unfinsh list size:"+listun.size());
		
		//格式化预警信息
		Map<String,Map<String,FaultLog>> terMap = faultLogDao.getPlcMap(listun);
		log.debug("finish get the map");
	
	
		for(Map<String,Object> map:faultMapList) {
			parseFalutMap(map,terMap,finalResult);
		}
		doLastResult(finalResult,terMap);
		List<FaultLog> updList = faultLogService.getPreList(finalResult);
		faultLogDao.saveByYear(finalResult);
		faultLogDao.updateYear(updList);
		//更新扫描时间
		staticsticsLogDao.updateDate(StaticsticsLog.TER_FAULT_TYPE,range[1]);
	}
	
	
	/**
	 * 将map中剩余数据 放到list中，最后一条数据
	 * @param finalResult
	 * @param terMap
	 */
	private void doLastResult(List<FaultLog> finalResult,Map<String,Map<String,FaultLog>> terMap) {
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
	
	/**
	 * 获取时间范围内的数据
	 * @param faultMap
	 */
	public List<Map<String,Object>> findFaultList() {
		Date[] range = faultLogService.getDefaultTimeRange(StaticsticsLog.PLC_FAULT_TYPE);
		List<Map<String,Object>> faultMapList = telLogService.findAllByDay(range[0],range[1]);
		return  faultMapList;
		
	}
	
	/**
	 * 生成错误结束时间，默认在原结束时间上增加一个周期
	 * @param faultLog
	 * @return
	 */
	private Date getRealEndTime(FaultLog faultLog) {
		return new Date(faultLog.getEndTime().getTime()+defaultTime);
	}
	
	
	private void parseFalutMap(Map<String,Object> map,Map<String,Map<String,FaultLog>> terMap,List<FaultLog> finalResult) {
			Long bTime = getTime(map);
			String code = getCode(map);
			String terId = getTerId(map);
			ObjectId orgId =  (ObjectId) map.get("_id");
			Map<String,FaultLog> falutMap = terMap.get(terId);
			if(falutMap==null) {
				//初始化
				falutMap = new HashMap<>();
				terMap.put(terId, falutMap);
			}
			
			
		
			FaultLog faultLogPre = falutMap.get("code");
				//新的异常
				if(faultLogPre==null){
					faultLogPre = getNewFaultLog(orgId,new Date(bTime),code,terId);
					falutMap.put("code", faultLogPre);
				}else{
					//判断是否异常结束
					if(bTime-faultLogPre.getEndTime().getTime()>timePro) {
						//更新错误发生的结束时间
						faultLogPre.setEndTime(getRealEndTime(faultLogPre));
						finalResult.add(faultLogPre);
						//map中替换为新的错误记录
						falutMap.put("code",getNewFaultLog(orgId,new Date(bTime),code,terId));
					}else {
						//更新时间
						faultLogPre.setEndTime(new Date(bTime));
					}
					
				}
				
					
			
		
			 
		
	}
	
	/**
	 * 构造新错误对象
	 * @param bTime
	 * @param code
	 * @param terId
	 * @return
	 */
	private FaultLog getNewFaultLog(ObjectId orgId,Date bTime,String code,String terId) {
		FaultLog faultLog = new FaultLog();
		faultLog.setbTime(bTime);
		faultLog.setEndTime(bTime);
		faultLog.setCode(code);
		faultLog.setId(getId());
		faultLog.setTerId(terId);
		faultLog.setFaultType(FaultLog.TERERROR);
		return faultLog;
	}
	
	
	/**
	 * 获取错误码
	 * @param faultMap
	 * @return
	 */
	private String getCode(Map<String,Object> faultMap) {
		return faultMap.get("code").toString();
	}
	
	/**
	 * 生成id
	 * @return
	 */
	private Long getId() {
		return IdGener.getInstance().getNormalId();
	}
	
	/**
	 * 获取终端id
	 * @param faultMap
	 * @return
	 */
	private String getTerId(Map<String,Object> faultMap) {
		return faultMap.get("terminalNo").toString();
	}
	
	/**
	 * 获取错误时间
	 * @param faultMap
	 * @return
	 */
	private Long getTime(Map<String,Object> faultMap) {
		Long faultTime = ((Date)faultMap.get("faultTime")).getTime();
		return faultTime;
	}
	
	

}
