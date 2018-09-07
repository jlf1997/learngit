package com.cimr.api.code.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbNameSetting {
	
	
	private static  String type;
	
	@Value(value = "${iot.dbname.setting.type:DEMO}")
	public void setLogBrokerList(String typec) {
		type = "_"+typec;
	}
	
	
	private static final String middle = "_M_";
	
	
	
	//======================中间数据================================
	
	
	
	/**
	 * 中间数据:处理后的预警日志表
	 * @param year
	 * @return
	 */
	public static String getFaultLogName(String projectId) {
		return "TEL_FAULT"+middle+projectId+type;
	}
	
	
}
