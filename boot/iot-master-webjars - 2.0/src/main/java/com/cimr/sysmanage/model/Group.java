package com.cimr.sysmanage.model;

import com.cimr.comm.base.BaseEntity;

/**
 * 类描述:组织 作者:admin 创建时间:2018年4月16日 下午1:44:31
 */
public class Group extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组织关键字
	 */
	private String groupKey;
	
	/**
	 * 组织名称
	 */
	private String groupName;
	
	/**
	 * 说明
	 */
	private String comment;
	private Float orderId;
	private String parentId;
	private String parentName;

	//字段不存在
	private String updateId;
	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Float getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
}
