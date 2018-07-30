package com.cimr.api.statistics.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

public class StaticsticsLog {
	

	
	public static String getDbName() {
		return "staticstics_log";
	}

	@Id
	private ObjectId id;
	
	/**
	 * 统计日期
	 */
	private Date sDate;
	
	/**
	 * 统计日期
	 */
	private Integer type;

	
	private Date updTime;


	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
	
	
}
