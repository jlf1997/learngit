package com.cimr.api.code.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.code.model.warnning.FaultLog;
import com.cimr.api.code.model.warnning.PlcWarning;
import com.cimr.api.code.model.warnning.TerminalMessage;
import com.cimr.api.code.model.warnning.Warning;
import com.cimr.boot.utils.GsonUtil;
import com.google.gson.JsonObject;

/**
 * 预警处理类
 * @author yx
 *
 */
@Service
public class WarningService {

	
	private static final Logger log = LoggerFactory.getLogger(WarningService.class);
	
	@Autowired
	private FaultLogService faultLogService;

	private  void parseWarningMessage(List<Warning> warnings,String proid) {
		
		//TODO 发生消息到消息服务
		
		//保存消息 为提高效率 直接将消息存到mongo库中
		List<FaultLog> saveList =  new ArrayList<>();
		List<FaultLog> updList =  new ArrayList<>();
		for(Warning warning:warnings) {
			faultLogService.getFaultLogList(warning, saveList, updList);
		}
		faultLogService.updateList(updList, proid);
		faultLogService.saveList(saveList, proid);
		
	}
	
	 
	/**
	 * 获取项目id
	 * @param warnningMessage
	 * @return
	 */
	private String getProId(JsonObject data) {
		return GsonUtil.getString(data, "projectNo");
	}

	private Long getGatherMsgTime(JsonObject data) {
		return GsonUtil.getLong(data, "gatherMsgTime");
	}

	
	/**
	 * 阈值消息
	 * @param warnningMessage
	 * @return
	 */
	public void parsePlcWarning(TerminalMessage warnningMessage) {
		JsonObject data = warnningMessage.getData();
		String proid = getProId(data);
		List<Warning> warnings = new ArrayList<>();
		Long gatherMsgTime = getGatherMsgTime(data);
		String terminalNo = GsonUtil.getString(data, "terminalNo");
	
		Map<String,PlcWarning> plcs = new HashMap<>();
		Set<String> keys = data.keySet();
		String key;
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()) {
			key = iterator.next();
			if(check(key)==1) {
				String name = key.substring(0, key.length()-7);
				PlcWarning plcWarning = plcs.get(name);
				if(plcWarning==null) {
					plcWarning = new PlcWarning();
					plcs.put(name, plcWarning);
				}
				plcWarning.setName(name);
				plcWarning.setStatus(GsonUtil.getInteger(data, key));
			}
//			if(check(key)==0) {
//				String name = key;
//				PlcWarning plcWarning = plcs.get(name);
//				if(plcWarning==null) {
//					plcWarning = new PlcWarning();
//					plcs.put(name, plcWarning);
//				}
//				plcWarning.setName(name);
//				plcWarning.setValue(GsonUtil.getJsonElement(data, key));
//			}
			
		}
		iterator = plcs.keySet().iterator();
		while(iterator.hasNext()) {
			PlcWarning plcWarning = plcs.get(iterator.next());
			Warning warning = new Warning();
			warning.setFaultType(FaultLog.PLCERROR);
			warning.setGatherMsgTime(gatherMsgTime);
			warning.setProjectID(proid);
			warning.setTerminalId(terminalNo);
			warning.setWarningKey(plcWarning.getName());
			warning.setWarningStatus(plcWarning.getStatus());
			warnings.add(warning);
		}
		parseWarningMessage(warnings,proid);
	}
	
    
	
	private int check(String name) {
		if(name.endsWith("_STATUS")) {
			return 1;
		}
		if("gatherMsgTime".equals(name)
			|| "projectNo".equals(name)
			|| "terminalNo".equals(name)) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * 终端告警消息
	 * @param warnningMessage
	 * @return
	 */
	public void parseTerWarning(TerminalMessage warnningMessage) {
		JsonObject data = warnningMessage.getData();
		String proid = getProId(data);
		List<Warning> warnings = new ArrayList<>();
		Long gatherMsgTime = getGatherMsgTime(data);
		String terminalNo = warnningMessage.getProducerId();
		Boolean status = GsonUtil.getBoolean(data, "status");
		if(status) {//发生故障
			String faultCode = GsonUtil.getString(data, "faultCode");
			Warning warning = new Warning();
			warning.setFaultType(FaultLog.TERERROR);
			warning.setGatherMsgTime(gatherMsgTime);
			warning.setProjectID(proid);
			warning.setTerminalId(terminalNo);
			warning.setWarningKey(faultCode);
			warning.setWarningStatus(1);
			warnings.add(warning);
		}else {
			String faultCodes = GsonUtil.getString(data, "faultCode");
		}
		parseWarningMessage(warnings,proid);
	}
	 
}
