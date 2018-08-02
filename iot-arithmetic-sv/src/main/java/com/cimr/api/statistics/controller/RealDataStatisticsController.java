package com.cimr.api.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.statistics.service.RealDataSignalService;

@RestController
@RequestMapping("iotsv/statistics/realdata")
public class RealDataStatisticsController {

	@Autowired
	private RealDataSignalService RealDataSignalService;
	
	@GetMapping("/{signal}/day")
	public HttpResult getStatisticsDataDay(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataDay(signal, bTime, eTime, terid);
		return result;
	}
	
	@GetMapping("/{signal}/month")
	public HttpResult getStatisticsDataMonth(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataMonth(signal, bTime, eTime, terid);
		return result;
	}
	

	
	@GetMapping("/{signal}/year")
	public HttpResult getStatisticsDataYear(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=false) String terid,
			@RequestParam(name="bTime",required=false) Long bTime,
			@RequestParam(name="eTime",required=false) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataYear(signal, bTime, eTime, terid);
		return result;
	}
	
	
	
}
