package com.cimr.boot.comm.model;

import java.util.Date;

/**
 * 错误日志模型
 * @author Administrator
 *
 */
public class ErrorLogModel {
	
	
	/**
	 * 自定义描述
	 */
	private String remark;

	/**
	 * 错误原因
	 */
	private String cause;
	
	/**
	 * 错误发生时间
	 */
	private Date time;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
