package com.cimr.api.statistics.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bson.types.ObjectId;

@Table
@Entity
public class FaultLog {
	
	
	public static  String getDbName(String year) {
		return "TEL_FAULT_"+year;
	}
	
	public static Integer TERERROR = 1;
	
	public static Integer PLCERROR = 2;

	@Id
	private FaultPK id;
	
	
	
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
	 * 故障类型
	 */
	private Integer faultType;

	

	public FaultPK getId() {
		return id;
	}

	public void setId(FaultPK id) {
		this.id = id;
	}

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


	
	
}
