package com.cimr.routers.model;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

@Table
@Entity
public class ExcludeUrl extends BaseModel{
	
	@Id
	@GeneratedValue
	private Long exId;
	
	private String url;
	
	@ManyToOne
    @JoinColumn(name = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private ZuulRoute zuulRoute;


	
	public Long getExId() {
		return exId;
	}

	public void setExId(Long exId) {
		this.exId = exId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	

}
