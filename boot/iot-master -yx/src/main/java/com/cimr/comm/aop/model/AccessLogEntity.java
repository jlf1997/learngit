package com.cimr.comm.aop.model;

import java.util.Date;

public class AccessLogEntity {

	private String id;

	/**
	 * 访问ip
	 */
	private String ip;


	/**
	 * 访问时间
	 */
	private Date accessTime;


	/**
	 * 访问路由
	 */
	private String path;


	private String method;


	private String module;

	private String userId;

	private String commit;

	public AccessLogEntity() {
		super();
	}
	public AccessLogEntity(AccessLogEntity accessLogEntity) {
		super();
		this.id = accessLogEntity.getId();
		this.ip = accessLogEntity.getIp();
		this.accessTime = accessLogEntity.getAccessTime();
		this.path = accessLogEntity.getPath();
		this.method = accessLogEntity.getMethod();
		this.userId = accessLogEntity.getUserId();
		this.commit = accessLogEntity.getCommit();
		this.responTime=accessLogEntity.getResponTime();
	}
	/**
	 * 接口响应时间
	 */
	private String responTime;


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public Date getAccessTime() {
		return accessTime;
	}


	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getResponTime() {
		return responTime;
	}


	public void setResponTime(String responTime) {
		this.responTime = responTime;
	}


	public String getCommit() {
		return commit;
	}


	public void setCommit(String commit) {
		this.commit = commit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
