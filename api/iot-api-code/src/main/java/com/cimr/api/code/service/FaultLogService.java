package com.cimr.api.code.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.code.dao.FaultLogDao;
import com.cimr.api.code.model.warnning.FaultLog;
import com.cimr.api.code.model.warnning.Warning;
import com.cimr.boot.utils.BootBeanUtils;

import io.jsonwebtoken.lang.Assert;

@Service
public class FaultLogService {
	
	@Autowired
	private FaultLogDao faultLogDao;
	
	private static final Logger log = LoggerFactory.getLogger(FaultLogService.class);


	public void saveList(List<FaultLog> saveList,String proid) {
		faultLogDao.save(saveList, proid);
	}
	
	public void updateList(List<FaultLog> updList,String proid) {
		for(FaultLog faultLog:updList) {
			faultLogDao.updateFinished(faultLog, proid);
		}
		
	}
	
	/**
	 * 获取需要保存的 更新的列表
	 * @param warning
	 * @param saveList
	 * @param updList
	 */
	public void getFaultLogList(Warning warning,List<FaultLog> saveList,List<FaultLog> updList) {
		FaultLog faultLog = getFaultLog(warning);
		if(warning.getWarningStatus()!=0) {//非预警解除
			saveList.add(faultLog);
			
		}
		updList.add(faultLog);
		
		
		
		
	}
	
	public FaultLog getFaultLog(Warning warning) {
		FaultLog faultLog = new FaultLog();
		faultLog.setbTime(new Date(warning.getGatherMsgTime()));
		faultLog.setTerId(warning.getTerminalId());
		faultLog.setFaultType(warning.getFaultType());
		faultLog.setWarningStauts(warning.getWarningStatus());
		faultLog.setWarningKey(warning.getWarningKey());
		return faultLog;
	}
	
	
	
}
