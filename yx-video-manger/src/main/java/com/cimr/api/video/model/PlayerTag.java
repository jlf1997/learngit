package com.cimr.api.video.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;


@Entity
@Table
public class  PlayerTag extends BaseModel{
	@Id
	@GeneratedValue
	private Long playerTagId;
	
	private String name;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPlayerTagId() {
		return playerTagId;
	}

	public void setPlayerTagId(Long playerTagId) {
		this.playerTagId = playerTagId;
	}
	
	
	
	
	
}