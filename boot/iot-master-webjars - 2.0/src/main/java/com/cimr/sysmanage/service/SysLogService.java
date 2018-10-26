package com.cimr.sysmanage.service;

import com.cimr.comm.aop.AccessLog;
import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Dict;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

import java.util.List;


/**
 * 
 * <p>Title: DictService</p>
 */
public interface SysLogService{
    /**
     * 列表查询
     * @param assist
     * @return
     */
    List<AccessLogEntity> selectList(Assist assist);

    /**
     * 分页获得Obj数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     * @param assist
     * @return
     */
    PageData<AccessLogEntity> selectObj_common(Assist assist, int page, int limit);
}
