package com.cimr.api.code.model.mgr;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 对应redis 信号1 数据
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@ApiModel(value = "Terminal_1_Info", description = "信号组1终端查询对象")
public class Terminal_1_Info extends TerimalModel{

	
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "lng",notes="经度")
	private String lng;
	@ApiModelProperty(value = "lat",notes="纬度")
	private String lat;

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public Terminal_1_Info() {
		
	}
	
	
	public Terminal_1_Info(Map map) {
		this.setLocation(map);
		this.setTerId(map);
	}
	
	public void setLocation(Map map) {
		
		this.lat = map.get("Z_LAT").toString();
		this.lng = map.get("Z_LNG").toString();
	}
	
	public void setTerId(Map map) {
		this.setTerId(map.get("terminalNo").toString());
	}
	
	
}
