package com.cimr.api.statistics.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cimr.api.statistics.service.PlcFaultService;
import com.cimr.api.statistics.service.TerminalFaultService;
@Component
public class FaultSchedule {
	
	
	@Autowired
	private TerminalFaultService terminalFaultService;
	@Autowired
	private PlcFaultService plcFaultService;
	
	private static final Logger log = LoggerFactory.getLogger(FaultSchedule.class);


	@Scheduled(cron = "0 0/5 * * * *")
    public void timer(){
        //获取当前时间
//        LocalDateTime localDateTime =LocalDateTime.now();
//        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		log.info("begain scan fault");
		terminalFaultService.genLog();
		plcFaultService.genLog();
		log.info("end scan fault");
	
	}
}
