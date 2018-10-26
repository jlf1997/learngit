package com.cimr.sysmanage.dto;


public class ZtreeDto
{
  private String id;
  
  private String pId;
  
  private boolean isParent;
  private String name;
  private String property;
  private boolean checked;
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  public void setChecked(boolean checked) {
    this.checked = checked;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getpId() {
    return this.pId;
  }
  
  public void setpId(String pId) {
    this.pId = pId;
  }
  
  public boolean isParent() {
    return this.isParent;
  }
  
  public void setParent(boolean parent) {
    this.isParent = parent;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  







  public String getProperty()
  {
    return this.property;
  }
  
  public void setProperty(String property) {
    this.property = property;
  }
}
