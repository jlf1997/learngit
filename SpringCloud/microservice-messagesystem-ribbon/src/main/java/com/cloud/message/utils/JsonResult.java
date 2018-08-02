package com.cloud.message.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonResult {
	
	public JsonResult(String msg) {
		if (msg.equals("hiError")) {
			this.success = "false";
			this.code = "1";
			this.message = "服务器异常，熔断";
		}
	}

	public JsonResult(String code, String msg) {
		if (!code.equals("0")&&!code.equals("1999")) {
			success = "false";
			message = msg;
			this.code = code;
		}
	}

	public JsonResult() {

	}

	public void success(String code, Object data) {
		this.data = data;
		this.code = code;
		this.success = "true";
	}

	public String success;
	public String message;
	public String code;
	public Object data;

	public String getSuccess() {
		return success;
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
