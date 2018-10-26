package com.cimr.sysmanage.service;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.UserDevice;

/**
 * Created by suhuanzhao on 2018/5/18.
 */
public interface UserDeviceService extends BaseService<UserDevice,String>{

    /**
     * 分配设备给用户
     * @param userId
     * @param deviceIds
     */
    void saveUserDevices(String userId,String deviceIds);
}
