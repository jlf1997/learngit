package com.cimr.api.statistics.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.statistics.service.TerminalFaultService;


@RequestMapping("/demo")
@RestController
public class TestController {

	@Autowired
	private TerminalFaultService terminalFaultService;
	
	@GetMapping("/all")
	public void findALl(
			@RequestParam("faultTime") Long faultTime
			){
		 Date faultDate = new Date(faultTime);
		 terminalFaultService.genDailyFaultLog(faultDate);
		
	}
}
