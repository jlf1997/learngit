package com.cimr.sysmanage.dto;

import java.util.List;





public class MenuTree
{
  private String id;
  private String name;
  private String menuKey;
  private String parentId;
  private Integer level;
  private String comment;
  private Float orderId;
  private String target;
  private String url;

  public String getMenuIcon() {
    return menuIcon;
  }

  public void setMenuIcon(String menuIcon) {
    this.menuIcon = menuIcon;
  }

  private String menuIcon;
  private List<MenuTree> children;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getMenuKey() {
    return this.menuKey;
  }
  
  public void setMenuKey(String menuKey) {
    this.menuKey = menuKey;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getParentId() {
    return this.parentId;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  
  public Integer getLevel() {
    return this.level;
  }
  
  public void setLevel(Integer level) {
    this.level = level;
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
  
  public String getTarget() {
    return this.target;
  }
  
  public void setTarget(String target) {
    this.target = target;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public List<MenuTree> getChildren() {
    return this.children;
  }
  
  public void setChildren(List<MenuTree> children) {
    this.children = children;
  }
}
