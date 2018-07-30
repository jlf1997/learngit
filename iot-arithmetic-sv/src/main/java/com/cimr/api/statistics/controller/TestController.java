package com.cimr.api.statistics.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.statistics.service.PlcFaultService;
import com.cimr.api.statistics.service.TerminalFaultService;
import com.cimr.api.statistics.service.gen.RealDateSignalOilGen;
import com.cimr.boot.utils.TimeUtil;


@RequestMapping("/demo")
@RestController
public class TestController {

	@Autowired
	private TerminalFaultService terminalFaultService;
	@Autowired
	private PlcFaultService plcFaultService;
	@Autowired
	private RealDateSignalOilGen realDateSignalOilGen;
	
	@GetMapping("/ter")
	public void findALl(

			){

		terminalFaultService.genLog();
		
	}
	
	@GetMapping("/plc")
	public void plc(
			
			){
		Date faultDate = new Date();
		Date faultEndTime = TimeUtil.getEndTime(faultDate);
		Date faultStartTime = TimeUtil.getStartTime(faultDate);
		plcFaultService.genLog();
//		return plcFaultService.findFaultList(faultStartTime,faultEndTime);
		
	}
	
	@GetMapping("/oil")
	public void oil(
			
			){
		
		Date faultDate = new Date();
		Date faultEndTime = TimeUtil.getEndTime(faultDate);
		Date faultStartTime = TimeUtil.getStartTime(faultDate);
		realDateSignalOilGen.genLog(faultStartTime,faultEndTime);
		
	}
	
	
}
