package com.cimr.boot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.boot.utils.GsonUtil;

public class DefaultHandlleException implements HandlleException{
	
	
	private static final Logger log = LoggerFactory.getLogger(DefaultHandlleException.class);


	@Override
	public void saveLog(ExceptionModel model) {
		log.error(GsonUtil.objToJson(model));
	}

}
