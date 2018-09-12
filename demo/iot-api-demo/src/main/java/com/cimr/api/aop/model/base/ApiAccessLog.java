package com.cimr.api.aop.model.base;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

/**
 * 接口访问记录
 * @author Administrator
 *
 */
@Entity
@Table(name="td_api_log")
public class ApiAccessLog extends BaseModel{
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String apiName;
	
	@Lob
	private String causes;
	
	private boolean res;
	
	private Long time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	public boolean isRes() {
		return res;
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
	
	
	
	

}
