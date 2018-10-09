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

import com.cimr.api.statistics.service.RealDataSignalService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("statistics/realdata")
public class RealDataStatisticsController {

	@Autowired
	private RealDataSignalService RealDataSignalService;
	
	
	@ApiOperation(value = "指定终端实时信息每日统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "terid", value = "终端id", required = true)
		}) 
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
	

	@ApiOperation(value = "指定终端实时信息每月统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = false),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "terid", value = "终端id", required = true)
		}) 
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
	


	@ApiOperation(value = "指定终端实时信息每年统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "terid", value = "终端id", required = true)
		}) 
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

	@ApiOperation(value = "终端实时信息每日统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "指令", required = false)
		}) 
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

	@ApiOperation(value = "终端实时信息每月统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "指令", required = false)
		}) 
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
	

	@ApiOperation(value = "终端实时信息每月统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "signal", value = "信号", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "指令", required = false)
		}) 
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
