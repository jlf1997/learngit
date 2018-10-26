package com.cimr.sysmanage.dto;

 
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cimr.sysmanage.model.UnitFile;


public class UnitFileDto {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String sourceUrl;
    private String maxLogoUrl;
    private String middleLogoUrl;
    private String minLogoUrl;
    private String resId;
    private String resType;
    private String opeType;
    private String descript;
    private String userId;
    private String userType;
    private Integer width;
    private Integer height;
    private String ext;
    private String type;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String createTimeStr;
    private String updateTimeStr;

    public UnitFileDto() {
    }

    public UnitFileDto(UnitFile unitFile) {
        this.id = unitFile.getId();
        this.title = unitFile.getTitle();
        this.sourceUrl = unitFile.getSourceUrl();
        this.maxLogoUrl = unitFile.getMaxLogoUrl();
        this.middleLogoUrl = unitFile.getMiddleLogoUrl();
        this.minLogoUrl = unitFile.getMinLogoUrl();
        this.resId = unitFile.getResId();
        this.resType = unitFile.getResType();
        this.opeType = unitFile.getOpeType();
        this.descript = unitFile.getDescript();
        this.userId = unitFile.getUserId();
        this.userType = unitFile.getUserType();
        this.width = unitFile.getWidth();
        this.height = unitFile.getHeight();
        this.ext = unitFile.getExt();
        this.type = unitFile.getType();
        this.status = unitFile.getDelFlag().toString();
        this.createTime = unitFile.getCreateTime();
        this.updateTime = unitFile.getUpdateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (this.createTime != null) {
            this.createTimeStr = sdf.format(this.createTime);
        }
        if (this.updateTime != null) {
            this.updateTimeStr = sdf.format(this.updateTime);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getMaxLogoUrl() {
        return this.maxLogoUrl;
    }

    public void setMaxLogoUrl(String maxLogoUrl) {
        this.maxLogoUrl = maxLogoUrl;
    }

    public String getMiddleLogoUrl() {
        return this.middleLogoUrl;
    }

    public void setMiddleLogoUrl(String middleLogoUrl) {
        this.middleLogoUrl = middleLogoUrl;
    }

    public String getMinLogoUrl() {
        return this.minLogoUrl;
    }

    public void setMinLogoUrl(String minLogoUrl) {
        this.minLogoUrl = minLogoUrl;
    }

    public String getResId() {
        return this.resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResType() {
        return this.resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getOpeType() {
        return this.opeType;
    }

    public void setOpeType(String opeType) {
        this.opeType = opeType;
    }

    public String getDescript() {
        return this.descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return this.updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
