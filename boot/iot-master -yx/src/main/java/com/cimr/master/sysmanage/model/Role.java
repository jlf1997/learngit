package com.cimr.sysmanage.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cimr.comm.base.BaseEntity;

public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色关键字
	 */
	private String roleKey;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 说明
	 */
	private String comment;
	private Float orderId;
	private String groupId;
	/**
	 * 不持久化
	 */
	private String groupName;

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRoleKey() {
		return this.roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Float getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
