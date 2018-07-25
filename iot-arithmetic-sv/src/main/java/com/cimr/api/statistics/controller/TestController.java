package com.cimr.api.statistics.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.statistics.service.PlcFaultService;
import com.cimr.api.statistics.service.TerminalFaultService;
import com.cimr.boot.utils.TimeUtil;


@RequestMapping("/demo")
@RestController
public class TestController {

	@Autowired
	private TerminalFaultService terminalFaultService;
	@Autowired
	private PlcFaultService plcFaultService;
	
	@GetMapping("/all")
	public void findALl(
			@RequestParam("faultTime") Long faultTime
			){
		 Date faultDate = new Date(faultTime);
		 plcFaultService.genDailyFaultLog(faultDate);
		
	}
	
	@GetMapping("/plc")
	public List<Map<String,Object>> plc(
			@RequestParam("faultTime") Long faultTime
			){
		Date faultDate = new Date(faultTime);
		Date faultEndTime = TimeUtil.getEndTime(faultDate);
		Date faultStartTime = TimeUtil.getStartTime(faultDate);
		plcFaultService.genDailyFaultLog(faultDate);
		return plcFaultService.findFaultList(faultStartTime,faultEndTime);
		
	}
	
	
}
