package com.cimr.api.statistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.statistics.service.RealDataSignalService;

@RestController
@RequestMapping("statistics/realdata")
public class RealDataStatisticsController {

	@Autowired
	private RealDataSignalService RealDataSignalService;
	
	@GetMapping("/{signal}/day")
	public HttpResult getStatisticsDataDay(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataDay(signal, bTime, eTime, terid);
		return result;
	}
	
	@GetMapping("/{signal}/month")
	public HttpResult getStatisticsDataMonth(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataMonth(signal, bTime, eTime, terid);
		return result;
	}
	

	
	@GetMapping("/{signal}/year")
	public HttpResult getStatisticsDataYear(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataYear(signal, bTime, eTime, terid);
		return result;
	}
	
	@PostMapping("/{signal}/day")
	public HttpResult getStatisticsDataDayp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataDay(signal, bTime, eTime, terIds,codes);
		return result;
	}
	
	@PostMapping("/{signal}/month")
	public HttpResult getStatisticsDataMonthp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataMonth(signal, bTime, eTime, terIds,codes);
		return result;
	}
	

	
	@PostMapping("/{signal}/year")
	public HttpResult getStatisticsDataYearp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			) {
		HttpResult result;
		result = RealDataSignalService.getStatisticsDataYear(signal, bTime, eTime, terIds,codes);
		return result;
	}
	
	
	
}