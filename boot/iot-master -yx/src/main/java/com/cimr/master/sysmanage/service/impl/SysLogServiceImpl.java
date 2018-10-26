package com.cimr.master.sysmanage.service.impl;

import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.sysmanage.dao.SysLogDao;
import com.cimr.sysmanage.service.SysLogService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl  implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public List<AccessLogEntity> selectList(Assist assist) {
        return sysLogDao.selectList(assist);
    }

    @Override
    public PageData<AccessLogEntity> selectObj_common(Assist assist, int page, int limit) {
        if (null == assist) {
            assist = new Assist();
        }
        PageData<AccessLogEntity> pageData = new PageData<>();
        Long count = sysLogDao.getObjRowCountCommon(assist);
        int startRow = limit * (page - 1);
        assist.setStartRow(startRow);
        assist.setRowSize(limit);
        List<AccessLogEntity> list = sysLogDao.selectList(assist);
        pageData.setCount(count.intValue());
        pageData.setList(list);
        return pageData;
    }
}