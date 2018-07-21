package com.cimr.api.video.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Entity
@Table
public class Video extends BaseModel{

	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	
	@OneToMany(fetch=FetchType.EAGER,cascade={},mappedBy="tagId")
	private List<Tags> tags;
	@OneToMany(fetch=FetchType.EAGER,cascade={},mappedBy="playerId")
	private List<Player> players;
	
	private String fh;
	
	private String md5;
	
	/**
	 * 首页图片
	 */
	private String pic;
	
	
	
	
	
	
}
