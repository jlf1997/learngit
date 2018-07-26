package com.cimr.api.statistics.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

public class StaticsticsLog {
	
	public static Integer PLC_FAULT_TYPE = 2;
	
	public static Integer TER_FAULT_TYPE = 1;
	
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
	
	
	
	
}
