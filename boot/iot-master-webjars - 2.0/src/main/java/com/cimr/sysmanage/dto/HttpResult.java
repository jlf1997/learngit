package com.cimr.sysmanage.dto;

import com.cimr.util.StringUtil;

public class HttpResult {

	private boolean success;

	private Object data;

	private String error;

	private Integer code;

	public HttpResult(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	public HttpResult(boolean success, String error) {
		this.success = success;
		if (StringUtil.valid(error) && error.indexOf("not have permission") >= 0) {
			this.error = "没有操作权限";
		} else {
			this.error = error;
		}
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
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
