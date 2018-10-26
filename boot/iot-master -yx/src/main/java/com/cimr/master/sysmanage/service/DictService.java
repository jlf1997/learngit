package com.cimr.sysmanage.service;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Dict;


/**
 * 
 * <p>Title: DictService</p>
 */
public interface DictService extends BaseService<Dict, String> {
    //判断是否重复添加字典
    public boolean isExist(String type,String value);




}
