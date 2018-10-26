package com.cimr.sysmanage.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cimr.comm.base.BaseEntity;

public class User extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	public static final int _0 = 0;

	public static final int _1 = 1;

	private String username;
	private transient String pswd;
	private Integer status;
	
	/**
	 * 姓名
	 */
	private String fullname;
	private String phone;
	private String email;
	private String comment;
	private Date lastLoginTime;
	private Float orderId;
	private String theme;
	private String avatar;
	private String groupId;
	private String userType;

	public User(User user) {
		this.username = user.getUsername();
		this.pswd = user.getPswd();
		this.status = user.getStatus();
		this.fullname = user.getFullname();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.comment = user.getComment();
		this.lastLoginTime = user.getLastLoginTime();
		this.orderId = user.getOrderId();
		this.theme = user.getTheme();
		this.avatar = user.getAvatar();
		this.groupId = user.getGroupId();
		this.userType = user.getUserType();
	}

	public User() {
	}

	public User(String id){
		setId(id);
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPswd() {
		return this.pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Float getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
