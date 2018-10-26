package com.cimr.sysmanage.model;

import com.cimr.comm.base.BaseEntity;
import com.xiaoleilu.hutool.json.JSONObject;

public class UserDevice extends BaseEntity{

    private String deviceId;
    private String userId;
    public UserDevice() {
        super();
    }

    public UserDevice(String deviceId,String userId){
        super();
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public UserDevice(JSONObject jsonObject){
        setId(jsonObject.getStr("id"));
        this.deviceId = jsonObject.getStr("deviceId");
        this.userId = jsonObject.getStr("userId");
    }
}
