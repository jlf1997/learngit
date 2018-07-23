package com.cimr.api.statistics.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Table
@Entity
public class FaultLog extends BaseModel{

	@Id
	private Long id;
	
	/**
	 * 开始时间
	 */
	private Long bTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getbTime() {
		return bTime;
	}

	public void setbTime(Long bTime) {
		this.bTime = bTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
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
