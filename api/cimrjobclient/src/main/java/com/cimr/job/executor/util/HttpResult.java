package com.cimr.job.executor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpResult {
	private static final Logger logger = LoggerFactory.getLogger(HttpResult.class);

	public String success;
	public String message;
	public String code;
	public Object data;
	public String getSuccess() {
		return success;
	}
	
	public HttpResult(String code,String msg){
		if(!code.equals("0")){
			logger.error(msg);
			success = "false";
			message = msg;
			this.code = code;
		}
	}
	public HttpResult(){
		
	}
	
	public void success(String code,Object data){
		this.data = data;
		this.code = code;
		this.success = "true";
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
