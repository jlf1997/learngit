package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.history.dao.RealDataFalutHistoryDao;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.dao.StaticsticsLogDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.api.statistics.service.gen.PlcFaultGen;
import com.cimr.boot.utils.IdGener;
import com.cimr.boot.utils.TimeUtil;


@Service
public class PlcFaultService  {

	
	@Autowired
	private PlcFaultGen plcFaultGen;
	
	
	public void genLog() {
		plcFaultGen.genLog();
	}
	
}
