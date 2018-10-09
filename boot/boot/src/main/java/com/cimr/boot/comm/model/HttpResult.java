package com.cimr.boot.comm.model;

import com.cimr.boot.utils.StringUtil;

public class HttpResult {
	
	private boolean success;

	private Object data;

	private String message;
	
	private String apiCode;

	
	private Integer code;
	
	public HttpResult() {
		this.success = true;
	}

	public HttpResult(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	public HttpResult(boolean success, String message) {
		this.success = success;
		if (message!=null && StringUtil.valid(message) && message.indexOf("not have permission") >= 0) {
			this.message = "没有权限";
		} else {
			this.message = message;
		}
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	
	
	
	
}
