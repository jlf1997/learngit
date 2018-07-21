package com.cimr.api.video.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Entity
@Table
public class Player extends BaseModel{
	
	@Id
	@GeneratedValue
	private Long playerId;
	
	private String name;
	
	private Long birthday;
	
	private List<PlayerTag> playerTags;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	
	
	
	
}
