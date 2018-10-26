package com.cimr.comm.aop.logs;

import com.cimr.comm.aop.model.DaoLogEntity;

public interface SaveDaoLog {
	public void saveLog(DaoLogEntity log);
}
