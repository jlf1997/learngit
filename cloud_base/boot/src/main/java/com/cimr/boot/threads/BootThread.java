package com.cimr.boot.threads;

import com.cimr.boot.aop.ApiAccessLog;
import com.cimr.boot.utils.BootBeanUtils;

public abstract class BootThread implements Runnable{
	
	public String levl;
	
	public ApiAccessLog log;
	
	public BootThread(String level,ApiAccessLog log ) {
		this.levl = (String) BootBeanUtils.deepClone(level, String.class);
		this.log = (ApiAccessLog) BootBeanUtils.deepClone(log, ApiAccessLog.class);
		
	}
	
	

}
