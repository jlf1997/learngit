package com.cloud.message.sub.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3495238333711411485L;
	private String UserID;
	private String WxID;
	private String UserName;
	private String RealName;
	private String PassWord;
	private String PasswordSalt;
	private String Tel;
	private int Gender;
	private String Email;
	private short IsUse;
	private int LoginTimes;
	private Timestamp LastLoginTime;
	private short IsAdmin;
	private short IsCustomerAdmin;
	private short IsMoveToWxGroup;
	private String DefaultStationID;
	private String DefaultGroupID;
	private String DefaultAssociationID;
	private String ProtectedTicket;
	private String RefreshToken;
	private String RoleCode;
	private String RegistID;
	private String UserStatus;
	private String Avatar;
	private int  SceneID;
	private short TelValid;
	private int UserType;
	private Timestamp Entrytime;
	private Timestamp LicensingTime;
	private Timestamp BirthDate;
	private String IdentityID;
	private String Address;
	private String Department;
	private String Job;
	private String ShortTel;
	private String DepartmentID;
	
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
	public String getPassWord() {
		return PassWord;
	}
	public void setPassWord(String passWord) {
		PassWord = passWord;
	}
	public String getPasswordSalt() {
		return PasswordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		PasswordSalt = passwordSalt;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public int getGender() {
		return Gender;
	}
	public void setGender(int gender) {
		Gender = gender;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public short getIsUse() {
		return IsUse;
	}
	public void setIsUse(short isUse) {
		IsUse = isUse;
	}
	public int getLoginTimes() {
		return LoginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		LoginTimes = loginTimes;
	}
	public Timestamp getLastLoginTime() {
		return LastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		LastLoginTime = lastLoginTime;
	}
	public short getIsAdmin() {
		return IsAdmin;
	}
	public void setIsAdmin(short isAdmin) {
		IsAdmin = isAdmin;
	}
	public short getIsCustomerAdmin() {
		return IsCustomerAdmin;
	}
	public void setIsCustomerAdmin(short isCustomerAdmin) {
		IsCustomerAdmin = isCustomerAdmin;
	}
	public short getIsMoveToWxGroup() {
		return IsMoveToWxGroup;
	}
	public void setIsMoveToWxGroup(short isMoveToWxGroup) {
		IsMoveToWxGroup = isMoveToWxGroup;
	}
	public String getDefaultStationID() {
		return DefaultStationID;
	}
	public void setDefaultStationID(String defaultStationID) {
		DefaultStationID = defaultStationID;
	}
	public String getDefaultGroupID() {
		return DefaultGroupID;
	}
	public void setDefaultGroupID(String defaultGroupID) {
		DefaultGroupID = defaultGroupID;
	}
	public String getDefaultAssociationID() {
		return DefaultAssociationID;
	}
	public void setDefaultAssociationID(String defaultAssociationID) {
		DefaultAssociationID = defaultAssociationID;
	}
	public String getProtectedTicket() {
		return ProtectedTicket;
	}
	public void setProtectedTicket(String protectedTicket) {
		ProtectedTicket = protectedTicket;
	}
	public String getRefreshToken() {
		return RefreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
	public String getRoleCode() {
		return RoleCode;
	}
	public void setRoleCode(String roleCode) {
		RoleCode = roleCode;
	}
	public String getRegistID() {
		return RegistID;
	}
	public void setRegistID(String registID) {
		RegistID = registID;
	}
	public String getUserStatus() {
		return UserStatus;
	}
	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}
	public String getAvatar() {
		return Avatar;
	}
	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	public int getSceneID() {
		return SceneID;
	}
	public void setSceneID(int sceneID) {
		SceneID = sceneID;
	}
	public short getTelValid() {
		return TelValid;
	}
	public void setTelValid(short telValid) {
		TelValid = telValid;
	}
	public int getUserType() {
		return UserType;
	}
	public void setUserType(int userType) {
		UserType = userType;
	}
	public Timestamp getEntrytime() {
		return Entrytime;
	}
	public void setEntrytime(Timestamp entrytime) {
		Entrytime = entrytime;
	}
	public Timestamp getLicensingTime() {
		return LicensingTime;
	}
	public void setLicensingTime(Timestamp licensingTime) {
		LicensingTime = licensingTime;
	}
	public Timestamp getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(Timestamp birthDate) {
		BirthDate = birthDate;
	}
	public String getIdentityID() {
		return IdentityID;
	}
	public void setIdentityID(String identityID) {
		IdentityID = identityID;
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
	public String getDepartmentID() {
		return DepartmentID;
	}
	public void setDepartmentID(String departmentID) {
		DepartmentID = departmentID;
	}
	
	
	
}
