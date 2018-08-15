package com.cimr.api.statistics.model.base;

import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;

public class StaticsicsRealDate {
	
	public StaticsicsRealDate() {
		this.updTime = new Date();
		this.creTime = new Date();
	}

	@Id
	private ObjectId id;
	
	private Date creTime;
	
	private Date updTime;
	
	
	private String year;
	
	private String month;
	
	private String day;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	

}
