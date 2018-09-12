package com.cimr.api.schedule.service;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cimr.api.schedule.task.CodeCallServiceTask;
import com.cimr.api.schedule.task.HistoryCallServiceTask;
import com.cimr.api.schedule.task.RealDateCallService;
import com.cimr.api.schedule.task.StCallServiceTask;

@Component
public class TaskGen {
	
	@Value("${iot.test.num:1000}")
	public int c ;
	
	@Autowired
	private ExecutorService executorService;

	@Autowired
	private HistoryCallServiceTask historyCallServiceTask;
	
	@Autowired
	private RealDateCallService realDateCallService;
	@Autowired
	private StCallServiceTask stCallServiceTask;
	@Autowired
	private CodeCallServiceTask codeCallServiceTask;
	
	
	public void historyCallServiceTask() {
		for(int i=0;i<c;i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					historyCallServiceTask.findDevInfoById();
					historyCallServiceTask.findTersAllRealData();
					historyCallServiceTask.findTersAllRealDataBooleanCount();
					historyCallServiceTask.findTersRealDataExcludeFields();
					historyCallServiceTask.findTersRealDataIncludeFields();
				}
			});
		}
	}
	
	public void realDateCallServiceTask() {
		for(int i=0;i<c;i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					realDateCallService.getAllDate();
					realDateCallService.getAllDateBoolean();
					realDateCallService.getData();
					realDateCallService.getDateExclude();
					realDateCallService.getDateInclude();
					realDateCallService.terminalRuntimeInfoControllerGetAllDataBoolean();
					realDateCallService.terminalRuntimeInfoControllerGetData();
				}
			});
		}
	}
	
	
	public void stCallServiceTask() {
		for(int i=0;i<c;i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					stCallServiceTask.faultStatisticsController_findByPage();
					stCallServiceTask.faultStatisticsController_getStatisticsDataDay();
					stCallServiceTask.faultStatisticsController_getStatisticsDataDayp();
					stCallServiceTask.faultStatisticsController_getStatisticsDataMonth();
					stCallServiceTask.faultStatisticsController_getStatisticsDataMonth();
					stCallServiceTask.faultStatisticsController_getStatisticsDataYear();
					stCallServiceTask.faultStatisticsController_getStatisticsDataYearp();
					stCallServiceTask.realDataStatisticsController_getStatisticsDataDay();
					stCallServiceTask.realDataStatisticsController_getStatisticsDataMonth();
					stCallServiceTask.realDataStatisticsController_getStatisticsDataMonthp();
					
				}
			});
		}
	}
	
	
	public void codeCallServiceTask() {
		for(int i=0;i<c/10;i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					codeCallServiceTask.sendCodeController_sendCode();
					codeCallServiceTask.sendCodeController_sendCodeDely();
					codeCallServiceTask.sendCodeController_sendDebug();
					codeCallServiceTask.sendCodeController_sendEndDebug();
				}
			});
		}
	}
	
}
