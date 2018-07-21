package com.cimr.api.video.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Entity
@Table
public class Tags extends BaseModel{

	private Long tagId;
	
	private String name;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
