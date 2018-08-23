package com.cimr.api.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

	@Autowired
	private TaskGen taskGen;
	
	@Scheduled(cron = "0 0/5 * * * *")
    public void historyCallServiceTask(){
		
		taskGen.historyCallServiceTask();
		
	}
	
	@Scheduled(cron = "0/40 * * * * *")
    public void realDateCallServiceTask(){
		
		taskGen.realDateCallServiceTask();
	
	}
	
	@Scheduled(cron = "0/70 * * * * *")
    public void stCallServiceTask(){
		
		taskGen.stCallServiceTask();
	
	}
	
	@Scheduled(cron = "0 0/5 * * * *")
    public void codeCallServiceTask(){
		
		taskGen.codeCallServiceTask();
	
	}
}
