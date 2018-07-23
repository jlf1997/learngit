package com.cimr.api.statistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.HttpResult;

@RestController
@RequestMapping("/statistics/realdata")
public class RealDataStatisticsController {

	
	
	@GetMapping("/daily")
	public HttpResult getStatisticsDataDaily(
			@RequestParam("") String terid
			) {
		HttpResult result;
		
		
		
		result = new HttpResult(true,"");
		return result;
	}
	
	
	
}
