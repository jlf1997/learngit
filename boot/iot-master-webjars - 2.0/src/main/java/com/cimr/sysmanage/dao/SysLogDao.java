package com.cimr.sysmanage.dao;

import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.util.Assist;

import java.util.List;

/**
 * 日志dao类
 */
public interface SysLogDao {

    /**
     * 获得Obj数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     * @param assist
     * @return
     */
    long getObjRowCountCommon(Assist assist);

    /**
     * 列表查询
     * @param assist
     * @return
     */
    List<AccessLogEntity> selectList(Assist assist);

    /**
     * 日志保存
     * @return
     */
    int saveLog(AccessLogEntity log);
}
