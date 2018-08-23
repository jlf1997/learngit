package com.cimr.api.schedule.interfaces.st;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimr.api.schedule.model.comm.HttpResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@FeignClient(name= "iot-sv-st")
@Hystrix
@RequestMapping("statistics/realdata")
public interface RealDataStatisticsController {

	
	@GetMapping("/{signal}/day")
	public HttpResult getStatisticsDataDay(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) ;
	
	@GetMapping("/{signal}/month")
	public HttpResult getStatisticsDataMonth(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) ;
	

	
	@GetMapping("/{signal}/year")
	public HttpResult getStatisticsDataYear(
			@PathVariable(name="signal") String signal,
			@RequestParam(name="terid",required=true) String terid,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime
			) ;
	
	@PostMapping("/{signal}/day")
	public HttpResult getStatisticsDataDayp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			);
	
	@PostMapping("/{signal}/month")
	public HttpResult getStatisticsDataMonthp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			) ;
	

	
	@PostMapping("/{signal}/year")
	public HttpResult getStatisticsDataYearp(
			@PathVariable(name="signal") String signal,
			@RequestBody List<String> terIds,
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes
			);
	
	
	
}
