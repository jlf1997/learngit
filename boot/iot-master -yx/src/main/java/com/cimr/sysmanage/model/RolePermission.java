package com.cimr.sysmanage.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cimr.comm.base.BaseEntity;



public class RolePermission extends BaseEntity
{
  private String id;
  private String roleId;
  private String permissionId;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getRoleId() {
    return this.roleId;
  }
  
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
  
  public String getPermissionId() {
    return this.permissionId;
  }
  
  public void setPermissionId(String permissionId) {
    this.permissionId = permissionId;
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
