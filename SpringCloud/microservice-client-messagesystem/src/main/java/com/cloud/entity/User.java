
package com.cloud.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8384861530894953175L;
	private String UserID;
	private String WxID;
	private String UserName;
	private String RealName;
	private String Tel;
	private String Email;
	private Timestamp BirthDate;
	private String Address;
	private String Department;
	private String Job;
	private String ShortTel;
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getWxID() {
		return WxID;
	}
	public void setWxID(String wxID) {
		WxID = wxID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Timestamp getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(Timestamp birthDate) {
		BirthDate = birthDate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getJob() {
		return Job;
	}
	public void setJob(String job) {
		Job = job;
	}
	public String getShortTel() {
		return ShortTel;
	}
	public void setShortTel(String shortTel) {
		ShortTel = shortTel;
	}
	
	
}


