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
	
	@OneToMany(cascade={},mappedBy="tagId")
	private List<Tags> tags;
	@OneToMany(cascade={},mappedBy="playerId")
	private List<Player> players;
	
	private String fh;
	
	private String md5;
	
	/**
	 * 首页图片
	 */
	private String pic;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Tags> getTags() {
		return tags;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getFh() {
		return fh;
	}

	public void setFh(String fh) {
		this.fh = fh;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	
	
	
	
	
}
