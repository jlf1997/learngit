package com.cimr.sysmanage.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.cimr.comm.base.BaseEntity;



public class Permission extends BaseEntity
{
  /**
   * 权限关键字
   */
  private String permissionKey;
  /**
   * 权限名称
   */
  private String permissionName;
  /**
   * 权限类型
   */
  private String permissionType;
  /**
   * 父节点ID
   */
  private String parentId;
  /**
   * 级别（菜单）
   */
  private Integer level;
  /**
   * 说明
   */
  private String comment;
  /**
   * 序号
   */
  private Float orderId;
  /**
   * 目标名称
   */
  private String target;
  /**
   * 链接
   */
  private String href;

  public String getMenuIcon() {
    return menuIcon;
  }

  public void setMenuIcon(String menuIcon) {
    this.menuIcon = menuIcon;
  }

  private String menuIcon;
  public Integer getLevel()
  {
    return this.level;
  }
  
  public void setLevel(Integer level) {
    this.level = level;
  }
  
  public String getParentId() {
    return this.parentId;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  
  public String getComment() {
    return this.comment;
  }
  
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public String getPermissionKey() {
    return this.permissionKey;
  }
  
  public void setPermissionKey(String permissionKey) {
    this.permissionKey = permissionKey;
  }
  
  public String getPermissionName() {
    return this.permissionName;
  }
  
  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }
  
  public Float getOrderId() {
    return this.orderId;
  }
  
  public void setOrderId(Float orderId) {
    this.orderId = orderId;
  }
  
  public String getTarget() {
    return this.target;
  }
  
  public void setTarget(String target) {
    this.target = target;
  }
  
  public String getHref() {
    return this.href;
  }
  
  public void setHref(String href) {
    this.href = href;
  }
  
  public String getPermissionType() {
    return this.permissionType;
  }
  
  public void setPermissionType(String permissionType) {
    this.permissionType = permissionType;
  }
  
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
