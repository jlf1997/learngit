package com.cimr.api.statistics.model;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

/**
 * 保存日更新类型的更新记录
 * @author Administrator
 *
 */
public class StatisticsDailyLog {

	/**
	 * 更新日期
	 */
	private Date sDate;
	
	private Date creTime;
	
	private Date updTime;
	
	@Id
	private ObjectId id;
	
	private String type;

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public static String getDbName() {
//		// TODO Auto-generated method stub
//		return "staticstics_daily_log_1";
//	}
	
	
	

}
