package com.cimr.api.statistics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Table(name="tb_fault_field")
@Entity
public class FaultField extends BaseModel{

	@Id
	@GeneratedValue
	private Long id;
	
	private String faultKey;
	
	private String sType;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFaultKey() {
		return faultKey;
	}

	public void setFaultKey(String faultKey) {
		this.faultKey = faultKey;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}
}
