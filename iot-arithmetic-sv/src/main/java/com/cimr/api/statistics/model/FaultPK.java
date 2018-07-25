package com.cimr.api.statistics.model;

import java.io.Serializable;

public class FaultPK implements Serializable{

	private String code;
	
	private Object orgId;

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getOrgId() {
		return orgId;
	}

	public void setOrgId(Object orgId) {
		this.orgId = orgId;
	}
	
	
}
