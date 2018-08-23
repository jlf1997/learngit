package com.cimr.api.schedule.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.schedule.interfaces.code.SendCodeController;
import com.cimr.api.schedule.interfaces.code.SendSysCodeController;
import com.cimr.api.schedule.model.code.CodeSenderObject;
import com.cimr.api.schedule.model.comm.TerimalModel;

@Service
public class CodeCallServiceTask {

	@Autowired
	private SendCodeController sendCodeController;
	@Autowired
	private SendSysCodeController sendSysCodeController;
	
	private final static String cmdId = "b846c55746b0405cb9b08c50d884514d";
	
	private final static Integer cmdTitle = 1;
	private final static Integer cmdType = 0;
	
	public CodeSenderObject getCodeSenderObject() {
		CodeSenderObject codeSenderObject = new CodeSenderObject();
		codeSenderObject.setCodeId("2222");
		codeSenderObject.setNotify_url("http://wwww.baidu.com");
		List<TerimalModel> telIds = new ArrayList<>();
		TerimalModel t= new TerimalModel();
		t.setTerId("222222");
		telIds.add(t);
		codeSenderObject.setTelIds(telIds);
		return codeSenderObject;
	}
	
	public void sendCodeController_sendCode() {
		sendCodeController.sendCode(cmdId, 0, 1, getCodeSenderObject());
	}
	
	public void sendCodeController_sendDebug() {
		sendCodeController.sendDebug(getCodeSenderObject());
	}
	
	
	public void sendCodeController_sendCodeDely() {
		sendCodeController.sendCode(cmdId, cmdTitle, cmdType, 10000, getCodeSenderObject());
	}
	
	public void sendCodeController_sendEndDebug() {
		sendCodeController.sendEndDebug(getCodeSenderObject());
	}
	
	
}
