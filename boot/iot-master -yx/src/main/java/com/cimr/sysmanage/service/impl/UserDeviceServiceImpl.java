package com.cimr.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cimr.comm.base.BaseDao;
import com.cimr.comm.base.BaseServiceImpl;
import com.cimr.sysmanage.dao.UserDeviceDao;
import com.cimr.sysmanage.model.UserDevice;
import com.cimr.sysmanage.service.UserDeviceService;
import com.cimr.util.Assist;
import com.cimr.util.StringUtil;

/**
 * Created by suhuanzhao on 2018/5/18.
 */
@Service("userDeviceService")
@Transactional
public class UserDeviceServiceImpl extends BaseServiceImpl<UserDevice,String> implements UserDeviceService{

    @Autowired
    private UserDeviceDao dao;

    @Override
    protected BaseDao<UserDevice, String> getEntityDao() {
        return dao;
    }

    @Override
    public void saveUserDevices(String userId, String deviceIds) {
        if (StringUtil.isEmpty(userId)){
            return;
        }
        //先删除该用户的所有关联关系
        Assist assist = new Assist();
        assist.setRequires(Assist.andEq("user_id",userId));
        this.deleteObj_common(assist);

        //再添加
        List<UserDevice> userDevices = new ArrayList<>();
        if(!StringUtil.isEmpty(deviceIds)){
            String[] ids = deviceIds.split(",");
            UserDevice userDeviceTemp;
            for (String id : ids){
                userDeviceTemp = new UserDevice(id,userId);
                userDevices.add(userDeviceTemp);
            }
            this.insertObjByBatch_common(userDevices);
        }
    }
}
