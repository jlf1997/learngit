package com.cimr.api.code.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iot.redis")
public class RedisProperties {
	public static final String NEW_DATA = "NEW_DATA_";
	
	public static final String RUNNTIME = "TERMINAL_RUNTIME_INFO";
	/**
	 * 实时数据库编号
	 */
	private int newdataIndex = 4;
	/**
	 * 运行数据库编号
	 */
	private int runtimeIndex = 0;
	
	/**
	 * 消息缓存使用库
	 */
	private int messageTempIndex = 0;
	
	
	public int getNewdataIndex() {
		return newdataIndex;
	}
	public void setNewdataIndex(int newdataIndex) {
		this.newdataIndex = newdataIndex;
	}
	public int getRuntimeIndex() {
		return runtimeIndex;
	}
	public void setRuntimeIndex(int runtimeIndex) {
		this.runtimeIndex = runtimeIndex;
	}
	public int getMessageTempIndex() {
		return messageTempIndex;
	}
	public void setMessageTempIndex(int messageTempIndex) {
		this.messageTempIndex = messageTempIndex;
	}
	
	
	
}
