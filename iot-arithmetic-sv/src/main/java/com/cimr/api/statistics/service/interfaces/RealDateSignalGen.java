package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.api.history.dao.RealDataSignalHistoryDao;
import com.cimr.api.statistics.dao.RealDataSignalDao;
import com.cimr.api.statistics.dao.StatisticsDailyLogDao;


public abstract class RealDateSignalGen extends AbstractDailyGen{

	@Autowired
	private StatisticsDailyLogDao statisticsDailyLogDao;

	@Autowired
	private RealDataSignalHistoryDao realDataSignalHistoryDao;
	
	@Autowired
	private RealDataSignalDao realDataSignalDao;
	
	
	
	protected abstract String getSignal();
	
	@Override
	protected List<Map<String, Object>> getDateFromSource(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return realDataSignalHistoryDao.findAll(bTime, eTime, getSignal());
	}



	@Override
	protected void update(List<Object> finalResult) {
		// TODO Auto-generated method stub
		if(finalResult.size()>0) {
			realDataSignalDao.save(getSignal(), finalResult);
		}
	
	}
	
	protected abstract String getTerId(Map<String, Object> map);
	
	

	@Override
	protected void updateDate(List<Map<String, Object>> listun) {
		// 按日统计无需更新时间
		statisticsDailyLogDao.updateDate("signal_"+getSignal(), getbTime());
	}

}
