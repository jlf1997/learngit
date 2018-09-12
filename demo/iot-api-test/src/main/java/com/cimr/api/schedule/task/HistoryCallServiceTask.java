package com.cimr.api.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.schedule.interfaces.data.HistoryRealDataController;
import com.cimr.api.schedule.interfaces.data.SignalController;

@Service
public class HistoryCallServiceTask {
	
	private final static String projectId = "P00001";
	
	private final static String singal = "503447856";
	
	private final static String terid = "TEL0000012";
	
	private final static Long beg = 1532462644000L;
	
	private final static Long end = 1535141044000L;
	
	@Autowired
	private HistoryRealDataController historyRealDataController;

	@Autowired
	private SignalController signalController;
	
	public void findDevInfoById() {
		 signalController.findDevInfoById(projectId);
	}
	
	public void findTersAllRealData() {
		historyRealDataController.findTersAllRealData(singal, terid, beg, end, null, null, projectId);
	}
	public void findTersAllRealDataBooleanCount() {
		String[] fields = new String[]{"FAULT_FV_6"};
		String[] countFields = new String[]{"FAULT_FV_2"};
		String includeType = "INCLUDE";
		String countIncludeType = "EXCLUDE";
		historyRealDataController.findTersAllRealDataBooleanCount(singal, terid, beg, end, null, null, includeType, fields, countIncludeType, countFields, projectId);
	}
	public void findTersRealDataExcludeFields() {
		String[] fields = new String[]{"FAULT_FV_6"};
		historyRealDataController.findTersRealDataExcludeFields(singal, terid, beg, end, null, null, fields, projectId);
	}
	public void findTersRealDataIncludeFields() {
		 String[] fields = new String[]{"FAULT_FV_6"};
		historyRealDataController.findTersRealDataIncludeFields(singal, terid, beg, end, null, null, fields, projectId);
	}
	
	
	
	
	
}
