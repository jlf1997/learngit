package com.cimr.api.statistics.model;

import java.util.Date;

import org.bson.types.ObjectId;

public class StaticsticsLog {
	

	
//	public static String getDbName() {
//		return "staticstics_log";
//	}

	private ObjectId id;
	
	/**
	 * 统计日期
	 */
	private Date sDate;
	
	/**
	 * 统计日期
	 */
	private String type;

	
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



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
	
	
}
