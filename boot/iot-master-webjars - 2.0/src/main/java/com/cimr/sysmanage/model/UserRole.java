package com.cimr.sysmanage.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cimr.comm.base.BaseEntity;




public class UserRole extends BaseEntity
{
  private String id;
  private String userId;
  private String roleId;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
