package com.cimr.sysmanage.model;


import com.cimr.comm.base.BaseEntity;

public class UnitFile extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件名称/标题
	 */
	private String title;

	/**
	 * 原文件地址
	 */
	private String sourceUrl;

	/**
	 * 图片文件，大图地址
	 */
	private String maxLogoUrl;

	/**
	 * 图片文件，中图地址
	 */
	private String middleLogoUrl;

	/**
	 * 图片文件，小图地址
	 */
	private String minLogoUrl;

	/**
	 * 资源Id
	 */
	private String resId;

	/**
	 * 资源类型（0:未知）
	 * 对应 平台资源字典表 table_code
	 */
	private String resType;

	/**
	 * 业务类型
	 * 对应 平台资源字典表  opt_code
	 */
	private String opeType;

	/**
	 * 描述
	 */
	private String descript;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户类型（1010010180001企业，1010010180002:普通用户）
	 */
	private String userType;

	/**
	 * 图片宽度 单位像素
	 */
	private Integer width;

	/**
	 * 图片高度 单位像素
	 */
	private Integer height;

	/**
	 * 文件后缀
	 */
	private String ext;

	/**
	 * 文件类型
	 */
	private String type;
	
	private Float orderId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getMaxLogoUrl() {
		return maxLogoUrl;
	}

	public void setMaxLogoUrl(String maxLogoUrl) {
		this.maxLogoUrl = maxLogoUrl;
	}

	public String getMiddleLogoUrl() {
		return middleLogoUrl;
	}

	public void setMiddleLogoUrl(String middleLogoUrl) {
		this.middleLogoUrl = middleLogoUrl;
	}

	public String getMinLogoUrl() {
		return minLogoUrl;
	}

	public void setMinLogoUrl(String minLogoUrl) {
		this.minLogoUrl = minLogoUrl;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getOpeType() {
		return opeType;
	}

	public void setOpeType(String opeType) {
		this.opeType = opeType;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getOrderId() {
		return orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}
	
	

}