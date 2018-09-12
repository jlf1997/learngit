package com.cimr.api.schedule.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.schedule.interfaces.st.FaultStatisticsController;
import com.cimr.api.schedule.interfaces.st.RealDataStatisticsController;
import com.cimr.api.schedule.model.comm.TerimalModel;

@Service
public class StCallServiceTask {

	@Autowired
	private FaultStatisticsController faultStatisticsController;
	@Autowired
	private RealDataStatisticsController realDataStatisticsController;
	
	private final static Long bTime = 1532462644000L;
	
	private final static Long endTime = 1535141044000L;
	
	private final static Long eTime = 1535141044000L;
	
	private String codes = "FQ_DAY_OIL";
	
	
	private String terid = "TEL0000012";
	private String signal = "503447824";
	
	private List<String> getTerminals(){
		List<String> list = new ArrayList<>();
		list.add("TEL0000012");
		return list;
	}
	
	public void faultStatisticsController_findByPage() {
		faultStatisticsController.findByPage(1, 10, bTime, endTime, null, null, terid);
	}
	public void faultStatisticsController_getStatisticsDataDay() {
		faultStatisticsController.getStatisticsDataDay(bTime, eTime, codes, getTerminals());
	}
	public void faultStatisticsController_getStatisticsDataMonth() {
		faultStatisticsController.getStatisticsDataMonth(bTime, eTime, codes, getTerminals());
	}
	public void faultStatisticsController_getStatisticsDataYear() {
		faultStatisticsController.getStatisticsDataYear(bTime, eTime, codes, getTerminals());
	}
	public void realDataStatisticsController_getStatisticsDataDay() {
		realDataStatisticsController.getStatisticsDataDay(signal, terid, bTime, eTime);
	}
	public void faultStatisticsController_getStatisticsDataDayp() {
		realDataStatisticsController.getStatisticsDataDayp(signal, getTerminals(), bTime, eTime, codes);
	}
	public void realDataStatisticsController_getStatisticsDataMonth() {
		realDataStatisticsController.getStatisticsDataMonth(signal, terid, bTime, eTime);
	}
	public void realDataStatisticsController_getStatisticsDataMonthp() {
		realDataStatisticsController.getStatisticsDataMonthp(signal, getTerminals(), bTime, eTime, codes);
	}
	public void faultStatisticsControllergetStatisticsDataYear() {
		realDataStatisticsController.getStatisticsDataYear(signal, terid, bTime, eTime);
	}
	public void faultStatisticsController_getStatisticsDataYearp() {
		realDataStatisticsController.getStatisticsDataYearp(signal, getTerminals(), bTime, eTime, codes);
	}

}
