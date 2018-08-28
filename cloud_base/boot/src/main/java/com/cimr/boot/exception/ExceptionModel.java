package com.cimr.boot.exception;

/**
 * 异常模型
 * @author yx
 *
 */
public class ExceptionModel {

	/**
	 * 具体异常信息
	 */
	private String message;
	/**
	 * 异常类型
	 */
	private Integer tyep;
	/**
	 * 异常名称
	 */
	private String exceptionName;
	
	public ExceptionModel() {
		this.tyep = 0;
		this.exceptionName = "未知异常";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTyep() {
		return tyep;
	}

	public void setTyep(Integer tyep) {
		this.tyep = tyep;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
	
}
