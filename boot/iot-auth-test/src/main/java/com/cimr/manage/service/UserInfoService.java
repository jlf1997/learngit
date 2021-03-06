package com.cimr.manage.service;

import com.cimr.comm.base.BaseService;
import com.cimr.manage.dto.UserInfoDto;
import com.cimr.manage.model.UserInfo;

/**
 * Created by lenovo on 2017/12/25.
 */
public interface UserInfoService extends BaseService<UserInfo, String> {


    /**
     * 单个删除
     * @param userId
     */
    String delete(String userId);


    /**
     * 通过对象插入信息
     * @param userInfoDto
     */
    String insertByObj(UserInfoDto userInfoDto);


    /**
     * 通过对象更新信息
     * @param userInfoDto
     */
    String updateByObj(UserInfoDto userInfoDto);
    
    UserInfo getByUserId(String userId);

}
