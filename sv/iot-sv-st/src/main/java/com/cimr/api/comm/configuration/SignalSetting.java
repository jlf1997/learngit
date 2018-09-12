package com.cimr.api.comm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 记录关键字段
 * @author Administrator
 *
 */
@Component
public class SignalSetting {
	
	@Autowired
	private ProjectPropertities projectPropertities;

	/**
	 * 根据不同信号返回记录时间
	 * @param signal 信号量
	 * @return
	 */
	public  String getGatherMsgTime(String signal) {
//		//特殊信号的返回
		if(projectPropertities.getSingalOil().equals(signal)) {
			return "insertTime";
		}
		if(projectPropertities.getSingalFault().equals(signal)) {
			return "insertTime";
		}
		//默认返回
		return "gatherMsgTime";
	}
	
	public String getTerminalId(String signal) {
		return "terminalNo";
	}
	
	

}
