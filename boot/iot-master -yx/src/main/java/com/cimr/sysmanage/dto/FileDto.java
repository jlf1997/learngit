package com.cimr.sysmanage.dto;

import com.xiaoleilu.hutool.json.JSONObject;


public class FileDto
{
  private String originalName;
  private String newFileName;
  private String sourceUrl;
  private String minLogoUrl;
  private String middleLogoUrl;
  private String maxLogoUrl;
  private Integer imgWidth;
  private Integer imgHeight;
  private String type;
  private String descript;
  
  public FileDto() {}
  
  public FileDto(JSONObject jsonObject)
  {
    this.originalName = ((String)jsonObject.get("originalName"));
    this.newFileName = ((String)jsonObject.get("newFileName"));
    this.sourceUrl = ((String)jsonObject.get("sourceUrl"));
    this.minLogoUrl = "null".equals(jsonObject.get("minLogoUrl").toString()) ? null : jsonObject.get("minLogoUrl").toString();
    this.middleLogoUrl = "null".equals(jsonObject.get("middleLogoUrl").toString()) ? null : jsonObject.get("middleLogoUrl").toString();
    this.maxLogoUrl = "null".equals(jsonObject.get("maxLogoUrl").toString()) ? null : jsonObject.get("maxLogoUrl").toString();
//    this.imgHeight = ((Integer)jsonObject.get("imgHeight"));
//    this.imgWidth = ((Integer)jsonObject.get("imgWidth"));
    this.type = "null".equals(jsonObject.get("type").toString()) ? "" : jsonObject.get("type").toString();
    this.descript = "null".equals(jsonObject.get("descript").toString()) ? "" : jsonObject.get("descript").toString();
  }
  
  public String getOriginalName() {
    return this.originalName;
  }
  
  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }
  
  public String getNewFileName() {
    return this.newFileName;
  }
  
  public void setNewFileName(String newFileName) {
    this.newFileName = newFileName;
  }
  
  public String getSourceUrl() {
    return this.sourceUrl;
  }
  
  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }
  
  public String getMinLogoUrl() {
    return this.minLogoUrl;
  }
  
  public void setMinLogoUrl(String minLogoUrl) {
    this.minLogoUrl = minLogoUrl;
  }
  
  public String getMiddleLogoUrl() {
    return this.middleLogoUrl;
  }
  
  public void setMiddleLogoUrl(String middleLogoUrl) {
    this.middleLogoUrl = middleLogoUrl;
  }
  
  public String getMaxLogoUrl() {
    return this.maxLogoUrl;
  }
  
  public void setMaxLogoUrl(String maxLogoUrl) {
    this.maxLogoUrl = maxLogoUrl;
  }
  
  public Integer getImgWidth() {
    return this.imgWidth;
  }
  
  public void setImgWidth(Integer imgWidth) {
    this.imgWidth = imgWidth;
  }
  
  public Integer getImgHeight() {
    return this.imgHeight;
  }
  
  public void setImgHeight(Integer imgHeight) {
    this.imgHeight = imgHeight;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getDescript() {
    return this.descript;
  }
  
  public void setDescript(String descript) {
    this.descript = descript;
  }
}
