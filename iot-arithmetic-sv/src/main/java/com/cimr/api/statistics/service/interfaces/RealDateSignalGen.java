package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.history.dao.RealDataFalutHistoryDao;
import com.cimr.api.statistics.dao.RealDataSignalDao;


public abstract class RealDateSignalGen extends AbstractDailyGen{



	@Autowired
	private RealDataFalutHistoryDao RealDataFalutHistoryDao;
	
	@Autowired
	private RealDataSignalDao realDataSignalDao;
	
	
	protected abstract String getSignal();
	
	@Override
	protected List<Map<String, Object>> getDateFromSource(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return RealDataFalutHistoryDao.findAll(bTime, eTime, getSignal());
	}



	@Override
	protected void update(List<Object> finalResult) {
		// TODO Auto-generated method stub
		if(finalResult.size()>0) {
			realDataSignalDao.save(getSignal(), finalResult);
		}
	
	}
	
	protected abstract String getTerId(Map<String, Object> map);
	
	
	

}
