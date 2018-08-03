package com.cimr.api.statistics.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bson.types.ObjectId;

import com.cimr.boot.utils.TimeUtil;

@Table
@Entity
public class FaultLog {
	public FaultLog() {
		
	}
	
	public FaultLog(Map<String,Object> map) {
		this.id = (Long)map.get("_id");
		this.bTime = (Date) map.get("bTime");
		this.creTime = (Date) map.get("creTime");
		this.updTime = (Date) map.get("updTime");
		this.code = (String)map.get("code");
		this.endTime = (Date) map.get("endTime");
		this.faultType = (Integer) map.get("faultType");
		this.terId = (String) map.get("terId");
		this.orgId = (ObjectId) map.get("orgId");
	}
	
	
	
	public static  String getDbName(String yearAndMonth) {
		return "TEL_FAULT_"+yearAndMonth;
	}
	
	public static Integer TERERROR = 1;
	
	public static Integer PLCERROR = 2;

	@Id
	private Long id;
	
	private Date updTime;
	
	private Date creTime;
	
	/**
	 * 开始时间
	 */
	private Date bTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 故障码
	 */
	private String code;
	/**
	 * 终端id
	 */
	private String terId;
	
	
	/**
	 * 原始数据id
	 */
	private ObjectId orgId;
	
	
	private Integer status;
	
	
	
	/**
	 * 故障类型
	 */
	private Integer faultType;
	
	
	/**
	 * 用于表示是否是之前未完结的数据
	 */
	private String year;
	
	private String month;
	
	private String day;

	

	public Date getbTime() {
		return bTime;
	}

	public void setbTime(Date bTime) {
		this.bTime = bTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = terId;
	}

	public Integer getFaultType() {
		return faultType;
	}

	public void setFaultType(Integer faultType) {
		this.faultType = faultType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public ObjectId getOrgId() {
		return orgId;
	}

	public void setOrgId(ObjectId orgId) {
		this.orgId = orgId;
	}

	public String getYear() {
		return year;
	}

	
	public void setYMD(Date date) {
		this.year = TimeUtil.getYear(date);
		this.month = TimeUtil.getMonth(date);
		this.day = TimeUtil.getDay(date);
	}
	public void setYear(String year) {
		this.year = year;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
