package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.log.service.TelLogService;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.utils.IdGener;
import com.cimr.boot.utils.TimeUtil;

/**
 * 终端错误处理类
 * @author Administrator
 *
 */
@Service
public class TerminalFaultService {
	
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
	
	
	
	public void f(int pageNumber, int pageSize) {
		
	}
	
	
	/**
	 * 生成给定日期当天的终端错误日志统计
	 * @param faultTime
	 */
	public void genDailyFaultLog(Date faultTime) {
		
		
		List<Map<String,Object>>faultMapList = telLogService.findAllByDay(faultTime);
		//TODO 将所有endTime 为-1 的加入列表
		
		List<FaultLog> finalResult = new ArrayList<>();
		//临时存放错误记录
		Map<String,FaultLog> result = new HashMap<>();
		FaultLog faultLogPre = null;
		//错误发生时间
		Long bTime;
		//错误码
		String code;
		//终端id
		String terId;
		for(Map<String,Object> faultMap :faultMapList) {
			bTime = getTime(faultMap);
			code = getCode(faultMap);
			terId = getTerId(faultMap);
			faultLogPre = result.get(terId);
			if(faultLogPre==null) {
				//初始化
				result.put(terId,getNewFaultLog(new Date(bTime),code,terId));
			}else {
				//间隔超过一分钟，表示错误结束
				if(bTime-faultLogPre.getEndTime().getTime()>timePro) {
					//更新错误发生的结束时间
					faultLogPre.setEndTime(getRealEndTime(faultLogPre));
					finalResult.add(faultLogPre);
					//map中替换为新的错误记录
					result.put(terId,getNewFaultLog(new Date(bTime),code,terId));
				}else {
					//更新时间
					faultLogPre.setEndTime(new Date(bTime));
				}
			}
		}
		//将map中剩余数据 放到list中，最后一条数据
		Iterator<String> iterator = result.keySet().iterator();
		while(iterator.hasNext()) {
			terId = iterator.next();
			FaultLog faultLog = result.get(terId);
			//
			if(TimeUtil.getEndTime(faultLog.getEndTime()).getTime()-faultLog.getEndTime().getTime()>timePro) {
				
				faultLog.setEndTime(getRealEndTime(faultLog));
//				faultLog.setEndTime(null);
			}else {
				//表示当天结束，错误依然存在，需要第二天继续计算
				faultLog.setEndTime(null);
			}
			
			finalResult.add(faultLog);
		}
		faultLogDao.saveByYear(finalResult);
	}
	
	/**
	 * 生成错误结束时间，默认在原结束时间上增加一个周期
	 * @param faultLog
	 * @return
	 */
	private Date getRealEndTime(FaultLog faultLog) {
		return new Date(faultLog.getEndTime().getTime()+defaultTime);
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
