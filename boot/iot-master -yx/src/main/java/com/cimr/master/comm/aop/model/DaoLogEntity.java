package com.cimr.master.comm.aop.model;

import java.util.Date;

public class DaoLogEntity {

	
	private String sqlComment;
	
	private String methods;
	
	private Date accessTime;
	
	private String commit;
	
	/**
	 * 调用时间 单位毫秒
	 */
	private String responDate;

	public String getSqlComment() {
		return sqlComment;
	}

	public void setSqlComment(String sqlComment) {
		this.sqlComment = sqlComment;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public String getResponDate() {
		return responDate;
	}

	public void setResponDate(String responDate) {
		this.responDate = responDate;
	}
	
	
	
	
}
