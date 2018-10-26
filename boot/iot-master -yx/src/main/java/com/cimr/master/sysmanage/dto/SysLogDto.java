package com.cimr.sysmanage.dto;

import com.cimr.comm.aop.model.AccessLogEntity;

/**
 * Created by admin on 2018/8/21.
 */
public class SysLogDto extends AccessLogEntity {
    //用户名称
    private String userName;
    //访问时间改为字符串
    private String AccessTimeStr;
    public SysLogDto(){
    }
    public SysLogDto(AccessLogEntity accessLogEntity){
        super(accessLogEntity);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccessTimeStr() {
        return AccessTimeStr;
    }

    public void setAccessTimeStr(String accessTimeStr) {
        AccessTimeStr = accessTimeStr;
    }
}
