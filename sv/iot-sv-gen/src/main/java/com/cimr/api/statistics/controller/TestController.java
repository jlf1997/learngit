package com.cimr.api.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.statistics.dao.StatisticsDailyLogDao;
import com.cimr.api.statistics.service.gen.simpleStatistic.FaultDailyGen;
import com.cimr.api.statistics.service.gen.simpleStatistic.RealDateSignalOilGen;


@RequestMapping("/demo")
@RestController
public class TestController {

	@Autowired
	private RealDateSignalOilGen realDateSignalOilGen;
	
	@Autowired
	private StatisticsDailyLogDao statisticsDailyLogDao;
	@Autowired
	private FaultDailyGen faultDailyGen;
	

	
	
	
	
	
	@GetMapping("/oil")
	public void oil(
			){
		realDateSignalOilGen.genLog();
		
	}
	@GetMapping("/faultGen")
	public void faultGen(
			){
//		Date date = getDate();
//		realDateSignalOilGen.genLogDaily(date);
		faultDailyGen.genLog();
	}
	
	
	
	
	
	
}
