package com.cimr.boot.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 默认日志存储类：日志输出到logback
 * @author Administrator
 *
 */
public class DefaultLogAspect extends AbstractLogAspect{
	
	
	private static final Logger logs = LoggerFactory.getLogger(DefaultLogAspect.class);


	@Override
	public void saveLog(String level, ApiAccessLog log) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();  
		if("info".equals(level)) {
			
			logs.info("接口调用结果："+gson.toJson(log));
			return;
		}
		if("debug".equals(level)) {
			logs.debug("接口调用结果："+gson.toJson(log));
			return;
		}
		if("error".equals(level)) {
			logs.error(log.getCauses());
			logs.error("接口调用结果："+gson.toJson(log));
			return;
		}
		logs.error("日志级别错误");
	}

}
