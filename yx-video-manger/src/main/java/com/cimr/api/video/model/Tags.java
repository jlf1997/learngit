package com.cimr.api.video.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Entity
@Table
public class Tags extends BaseModel{
	@Id
	@GeneratedValue
	private Long tagId;
	
	private String name;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	
	
	
	
}
