package com.cimr.api.comm.model;


public class HttpResult {
	
	private boolean success;

	private Object data;

	private String message;

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
}
