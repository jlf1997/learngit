package com.cimr.sysmanage.dto;

import java.util.List;


public class LayuiTableData
{
  private Integer code;
  private String msg;
  private Integer count;
  private List data;
  
  public Integer getCode()
  {
    return this.code;
  }
  
  public void setCode(Integer code) {
    this.code = code;
  }
  
  public String getMsg() {
    return this.msg;
  }
  
  public void setMsg(String msg) {
    this.msg = msg;
  }
  
  public Integer getCount() {
    return this.count;
  }
  
  public void setCount(Integer count) {
    this.count = count;
  }
  
  public List getData() {
    return this.data;
  }
  
  public void setData(List data) {
    this.data = data;
  }
}
