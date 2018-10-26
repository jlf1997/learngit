package com.cimr.sysmanage.model;

import com.cimr.comm.base.BaseEntity;

/**
 * 
 * <p>Title: SysArea</p>
 * <p>Description: 国家，省/直辖市，市，区/县</p>
 * @author lkl
 * @date 2016年4月15日 上午10:33:07
 * 
 */
public class Area extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 地区编码
	 */
	private String areaId;
	
	/**
	 * 地区名字
	 */
	private String title;
	
	/**
	 * 地区英文名（拼音）
	 */
	private String code;
	
	/**
	 * 地区父级地区编码id
	 */
	private String pid;
	
	/**
	 * bid
	 */
	private Integer bid;
	
	/**
	 * 国家区号编码
	 */
	private Long countryCode;

	/**
	 * 排序ID
	 */
	private Float orderId;

	/**
	 * 父级标题
	 */
	private String pTitle;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	public Long getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Long countryCode) {
		this.countryCode = countryCode;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Float getOrderId() {
		return orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}

	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}
}