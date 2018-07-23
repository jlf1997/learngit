package com.cimr.api.statistics.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.log.service.TelLogService;
import com.cimr.api.statistics.service.TerminalFaultService;


@RequestMapping("/demo")
@RestController
public class TestController {

	@Autowired
	private TelLogService telLogService;
	@Autowired
	private TerminalFaultService terminalFaultService;
	
	@GetMapping("/all")
	public void findALl(
			@RequestParam("faultTime") Long faultTime
			){
		
		 terminalFaultService.test(faultTime);
		
	}
}
