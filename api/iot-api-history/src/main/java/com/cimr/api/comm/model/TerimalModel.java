package com.cimr.api.comm.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "TerimalModel", description = "终端查询对象")
public class TerimalModel implements Serializable{
	
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "terId")
	private String terId;

	public String getTerId() {
		return terId;
	}

	public void setTerId(String terId) {
		this.terId = terId;
	}
	
	
}
