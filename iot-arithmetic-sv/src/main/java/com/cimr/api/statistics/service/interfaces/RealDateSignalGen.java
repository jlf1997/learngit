package com.cimr.api.statistics.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.comm.configuration.SignalSetting;
import com.cimr.api.history.dao.RealDataSignalHistoryDao;
import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.api.statistics.service.interfaces.statisticsDailyGen.StaticsicsGen;

/**
 * 通用实时数据生成统计类
 * @author Administrator
 *
 */
public abstract class RealDateSignalGen extends StaticsicsGen{


	@Autowired
	private RealDataSignalHistoryDao realDataSignalHistoryDao;
	
	@Autowired
	private SignalSetting signalSetting;
	
	protected abstract String getSignal();
	
	@Override
	protected List<Map<String, Object>> getDateFromSource(Date bTime, Date eTime) {
		return realDataSignalHistoryDao.findAll(bTime, eTime, getSignal());
	}

	
	
	@Override
	protected Long getCount(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return realDataSignalHistoryDao.getCount(bTime, eTime, getSignal());
	}

	@Override
	protected  String getTerId(Map<String, Object> map) {
		return map.get(signalSetting.getTerminalId(getSignal())).toString();
	}


	@Override
	protected Date getTime(Map<String, Object> map) {
		return (Date) map.get(signalSetting.getGatherMsgTime(getSignal()));
	}

	@Override
	protected String getCollectionName() {
		return DbNameSetting.getRealDateStatisticsDbName(getSignal());
	}

	@Override
	protected String getTimeSaveType() {
		return "signal_"+getSignal();
	}
	

}
