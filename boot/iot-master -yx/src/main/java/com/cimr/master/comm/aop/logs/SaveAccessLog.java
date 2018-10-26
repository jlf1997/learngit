package com.cimr.master.comm.aop.logs;

import com.cimr.comm.aop.model.AccessLogEntity;

public interface SaveAccessLog {

	public void saveLog(AccessLogEntity log);
}
