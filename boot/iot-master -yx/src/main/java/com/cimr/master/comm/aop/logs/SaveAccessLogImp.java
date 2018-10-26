package com.cimr.master.comm.aop.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.sysmanage.dao.SysLogDao;
import com.cimr.util.IdUtil;

@Component
public class SaveAccessLogImp implements SaveAccessLog{

	private static final String SLM="/sysLog/nav/sysLogManagement";
	private static final String LAL="/deviceLocation/ajax/latitudeAndLongitude";

	@Autowired
	private SysLogDao sysLogDao;

	@Override
	public void saveLog(AccessLogEntity log) {
		log.setId(IdUtil.getId());
		if(needLog(log)){
			sysLogDao.saveLog(log);
		}else{

		}

	}

	public boolean needLog(AccessLogEntity log){
		if(SLM.equals(log.getPath()) || LAL.equals(log.getPath())){
			return false;
		}else{
			return true;
		}
	}

}
