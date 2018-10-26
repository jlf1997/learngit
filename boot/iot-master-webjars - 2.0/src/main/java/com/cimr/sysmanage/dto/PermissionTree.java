package com.cimr.sysmanage.dto;

import java.util.List;

public class PermissionTree
{
  private String name;
  private String value;
  private List<PermissionTree> children;
  
  public List<PermissionTree> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(List<PermissionTree> children) {
    this.children = children;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}
