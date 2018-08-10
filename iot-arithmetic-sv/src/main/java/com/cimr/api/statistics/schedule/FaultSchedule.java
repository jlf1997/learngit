package com.cimr.api.statistics.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.statistics.dao.StatisticsDailyLogDao;
import com.cimr.api.statistics.service.PlcFaultService;
import com.cimr.api.statistics.service.TerminalFaultService;
import com.cimr.api.statistics.service.gen.simpleStatistic.FaultDailyGen;
import com.cimr.api.statistics.service.gen.simpleStatistic.RealDateSignalOilGen;
import com.cimr.boot.utils.TimeUtil;
@Component
public class FaultSchedule {
	
	
	@Autowired
	private TerminalFaultService terminalFaultService;
	@Autowired
	private PlcFaultService plcFaultService;
	@Autowired
	private ProjectPropertities projectPropertities;
	@Autowired
	private RealDateSignalOilGen realDateSignalOilGen;
	@Autowired
	private StatisticsDailyLogDao statisticsDailyLogDao;
	
	@Autowired
	private FaultDailyGen faultDailyGen;
	
	private static final Logger log = LoggerFactory.getLogger(FaultSchedule.class);


//	@Scheduled(cron = "0 0/5 * * * *")
    public void faultTimer(){
        //存在单点故障 ，后期使用分布式任务调度框架优化
		if((projectPropertities.getRole()&ProjectPropertities.FAULT)==ProjectPropertities.FAULT||projectPropertities.isAllRole()) {
			log.info("begain scan fault");
			terminalFaultService.genLog();
			plcFaultService.genLog();
			log.info("end scan fault");
		}
		
	
	}
	

//	@Scheduled(cron = "0 0 1,2,3,4,5 * * ? ")
//	@Scheduled(cron = "0/10 * * * * ? ")
//	@Scheduled(cron = "0 0/2 * * * *")
    public void oilGetTimer(){
        
		getSignalLogDaily(projectPropertities.getSingalOil(),ProjectPropertities.OIL);
		
	
	}
//	@Scheduled(cron = "0 0/1 * * * *")
	 public void faultGetTimer(){
		log.info("begain statistics fault");
		faultDailyGen.genLog();
		log.info("end statistics fault");
		
	}
	
	private void getSignalLogDaily(String signal,Long role) {
		if((projectPropertities.getRole()&role)==role||projectPropertities.isAllRole()) {
			Date sDate = TimeUtil.getDay(-1);
			if(statisticsDailyLogDao.getDate("signal_"+signal, sDate)==null) {
				log.info("begain scan "+signal);
				realDateSignalOilGen.genLog();
				log.info("end scan "+signal);
			}else {//控制不重复生成
				
			}
			
		}
	}
}
