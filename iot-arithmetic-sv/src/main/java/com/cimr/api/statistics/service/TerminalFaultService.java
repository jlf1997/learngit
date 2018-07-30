package com.cimr.api.statistics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.log.service.TelLogService;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.service.gen.TerminalFaultGen;
import com.cimr.api.statistics.service.interfaces.DefaultFaultGen;

/**
 * 终端错误处理类
 * @author Administrator
 *
 */
@Service
public class TerminalFaultService  {
	
	
	

	private static final Logger log = LoggerFactory.getLogger(TerminalFaultService.class);

	@Autowired
	private TerminalFaultGen terminalFaultGen;

	public void genLog() {
		terminalFaultGen.genLog();
	}

	
	
	
	

	


	
	
	
	

}
