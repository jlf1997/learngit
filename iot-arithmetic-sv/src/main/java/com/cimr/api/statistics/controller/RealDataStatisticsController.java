package com.cimr.api.statistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.HttpResult;

@RestController
@RequestMapping("iotsv/statistics/realdata")
public class RealDataStatisticsController {

	
	
	@GetMapping("/dev/day")
	public HttpResult getStatisticsDataDay(
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		
		
		
		result = new HttpResult(true,"");
		return result;
	}
	
	@GetMapping("/dev/month")
	public HttpResult getStatisticsDataMonth(
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		
		
		
		result = new HttpResult(true,"");
		return result;
	}
	
	@GetMapping("/dev/year")
	public HttpResult getStatisticsDataYear(
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		
		
		
		result = new HttpResult(true,"");
		return result;
	}
	
}
