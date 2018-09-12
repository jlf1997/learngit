package com.cimr.boot.comm.model;


public class HttpResult {
	
	private boolean success;

	private Object data;

	private String message;
	
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
		this.message = message;
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
	
	
}
