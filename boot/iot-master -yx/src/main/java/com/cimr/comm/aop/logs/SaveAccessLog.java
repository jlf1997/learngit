package com.cimr.comm.aop.logs;

import com.cimr.comm.aop.model.AccessLogEntity;

public interface SaveAccessLog {

	public void saveLog(AccessLogEntity log);
}
