package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.history.dao.RealDataFalutHistoryDao;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.utils.IdGener;
import com.cimr.boot.utils.TimeUtil;


@Service
public class PlcFaultService  {

	@Autowired
	private FaultLogService faultLogService;
	@Autowired
	private FaultLogDao faultLogDao;
	
	@Autowired
	private RealDataFalutHistoryDao realDataFalutHistoryDao;
	
	
	private static final Logger log = LoggerFactory.getLogger(PlcFaultService.class);

	
	/**
	 * 处理plc故障
	 * @param faultMap
	 */
	public List<Map<String,Object>> findFaultList(Date bDate,Date eDate) {
		//记录是否是之前的故障延续
		//查询出存在异常的记录
		return realDataFalutHistoryDao.findFaultList(bDate,eDate);
		
	}
	
	/**
	 * 按天生成plc错误日志
	 * @param faultDate
	 * @return
	 */
	public void genDailyFaultLog(Date faultDate){
		Date faultEndTime = TimeUtil.getEndTime(faultDate);
		Date faultStartTime = TimeUtil.getStartTime(faultDate);
		List<Map<String,Object>> faultList = findFaultList(faultStartTime,faultEndTime);
		//临时保存某个终端的错误map
		Map<String,Map<String,FaultLog>> terMap = new HashMap<>();
		//保存最终的结果
		List<FaultLog> result = new ArrayList<>();
		for(Map<String,Object> map : faultList) {
			parseFalutMap(result,map,terMap);
		}
		doLastResult(result,terMap);
		//保存日志 ，不需要进行事务管理
		faultLogDao.saveByYear(result);
		
	}
	
	private void doLastResult(List<FaultLog> resutl,Map<String,Map<String,FaultLog>> terMap) {
		//将map中剩余数据 放到list中，最后一条数据
				Iterator<String> iterator = terMap.keySet().iterator();
				while(iterator.hasNext()) {
					String terId = iterator.next();
					Map<String,FaultLog> faultMap = terMap.get(terId);
					Iterator<String> iteratorTer = faultMap.keySet().iterator();
					while(iteratorTer.hasNext()) {
						FaultLog faultLog =  faultMap.get(iteratorTer.next());
						faultLog.setEndTime(null);
						resutl.add(faultLog);
					}
					
				}
	}
	
	/**
	 * 解析map
	 * @param faultMap
	 */
	private void parseFalutMap(List<FaultLog> resutl,Map<String,Object> map,Map<String,Map<String,FaultLog>> terMap) {
		//获取终端id
		String terId = map.get("terminalNo").toString();
		Date faultTime = (Date)map.get("gatherMsgTime");
		Map<String,FaultLog> falutMap = terMap.get(terId);
		if(falutMap==null) {
			falutMap = new HashMap<>();
			terMap.put(terId, falutMap);
		}
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			Object value = map.get(key);
			if(value instanceof Boolean) {
				Boolean newValue = (Boolean) value;
				FaultLog inValue = falutMap.get(key);
				if(inValue==null) {//新的错误
					if(newValue) {
						falutMap.put(key, getNewFaultLog(faultTime,key,terId));
					}else {//如果新的数据为false，直接忽略
						log.debug("the fault is not change:"+key+" "+newValue);
					}
				}else {//已有的错误
					if(!newValue) {
						inValue.setEndTime(faultTime);
						resutl.add(inValue);
						falutMap.remove(key);
					}else {//旧数据依然为错误，直接忽略
						log.debug("the fault is not change"+key+" "+newValue);
					}
				}
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
	private FaultLog getNewFaultLog(Date bTime,String code,String terId) {
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
	 * 生成id
	 * @return
	 */
	private Long getId() {
		return IdGener.getInstance().getNormalId();
	}
	
	

	
}
