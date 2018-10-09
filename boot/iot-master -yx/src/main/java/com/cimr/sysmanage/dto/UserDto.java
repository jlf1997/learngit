package com.cimr.sysmanage.dto;

import com.cimr.sysmanage.model.User;

/**
 * Created by suhuanzhao on 2018/5/21.
 */
public class UserDto extends User {
    private String currentUserGroupId;

    //用来判断复选框是否选中
    private Boolean checkbox;

    private String userTypeStr;
    public UserDto(){

    }

    public UserDto(User user, String currentUserGroupId) {
        super(user);
        this.setId(user.getId());
        this.currentUserGroupId = currentUserGroupId;
    }

    public UserDto(String id){
        setId(id);
    }

    public String getCurrentUserGroupId() {
        return currentUserGroupId;
    }

    public void setCurrentUserGroupId(String currentUserGroupId) {
        this.currentUserGroupId = currentUserGroupId;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getUserTypeStr() {
        return userTypeStr;
    }

    public void setUserTypeStr(String userTypeStr) {
        this.userTypeStr = userTypeStr;
    }
}
