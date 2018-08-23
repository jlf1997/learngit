package com.cimr.api.schedule.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.schedule.interfaces.data.TerminalLastData;
import com.cimr.api.schedule.interfaces.data.TerminalRuntimeInfoController;
import com.cimr.api.schedule.model.comm.TerimalModel;

@Service
public class RealDateCallService {

	@Autowired
	private TerminalLastData terminalLastData;
	@Autowired
	private TerminalRuntimeInfoController terminalRuntimeInfoController;
	
	
	private final static String projectId = "P00001";
	
	private final static String signal = "503447856";
	
	private List<TerimalModel> getTerminals(){
		List<TerimalModel> list = new ArrayList<>();
		TerimalModel tm = new TerimalModel();
		tm.setTerId("TEL0000012");
		list.add(tm);
		tm.setTerId("TEL0000013");
		list.add(tm);
		return list;
	}
	
	public void getAllDate() {
		terminalLastData.getAllDate(signal, getTerminals(), projectId);
	}
	
	public void getAllDateBoolean() {
		terminalLastData.getAllDateBoolean(signal, getTerminals(), null, null, null, null, projectId);
	}
	
	public void getData() {
		terminalLastData.getData(signal, getTerminals(), null, null, projectId);
	}
	
	public void getDateExclude() {
		String[] fields = new String[]{"FAULT_FV_6"};
		terminalLastData.getDateExclude(fields, signal, getTerminals(), projectId);
	}
	
	public void getDateInclude() {
		String[] fields = new String[]{"FAULT_FV_6"};
		terminalLastData.getDateInclude(fields, signal, getTerminals(), projectId);
	}

	
	public void terminalRuntimeInfoControllerGetAllDataBoolean() {
		terminalRuntimeInfoController.getAllDataBoolean(getTerminals(), null, null, null, null);
	}
	
	public void terminalRuntimeInfoControllerGetData() {
		terminalRuntimeInfoController.getData(getTerminals(), null, null);
	}
	

	
}
