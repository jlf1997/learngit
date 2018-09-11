package com.cimr.api.auth.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;


@Table
@Entity
public class AuthenticationModel extends BaseModel{

	@Id
	private Long id;
	
	private String appid;
	
	private String appSecret;
	
	private String appKey;
	
	/**
	 * 盐值
	 */
	private String saltValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSaltValue() {
		return saltValue;
	}

	public void setSaltValue(String saltValue) {
		this.saltValue = saltValue;
	}
	
	
	
	
}
