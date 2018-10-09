package com.cimr.api.statistics.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.statistics.service.FaultLogService;
import com.cimr.api.statistics.service.FaultStatisticsService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("statistics/fault")
@RestController
public class FaultStatisticsController {
	

	@Autowired
	private FaultStatisticsService faultStatisticsService;
	
	@Autowired
	private FaultLogService faultLogService;

	
	@ApiOperation(value = "分页查询异常信息",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "pageNumber", value = "页号", required = true),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true),
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "status", value = "异常状态", required = false),
		@ApiImplicitParam(paramType = "query", name = "code", value = "异常码", required = false),
		@ApiImplicitParam(paramType = "query", name = "terid", value = "终端id", required = false)
		}) 
	@GetMapping("/page")
	public HttpResult findByPage(
			@RequestParam(value="pageNumber",defaultValue="0") Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize,
			@RequestParam(value="bTime") Long bTime,
			@RequestParam(value="endTime") Long endTime,
			@RequestParam(value="status",required=false) Boolean status,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="terid",required=false) String terId
			){
		//临时解决前端传输空串导致无法查询
		if(StringUtils.isBlank(terId) || "undefind".equals(terId)) {
			terId =null;
		}
		return faultLogService.findByPage(pageNumber, pageSize, bTime, endTime, terId, code, status);
		
	}

	@ApiOperation(value = "异常信息每日统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "异常码", required = false)
		}) 
	@PostMapping("/day")
	public HttpResult getStatisticsDataDay(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			) {
		HttpResult result;
		result = faultStatisticsService.findAllByDay(terIds,bTime,eTime,codes);
		return result;
	}
	
	@ApiOperation(value = "异常信息每月统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "异常码", required = false)
		}) 
	@PostMapping("/month")
	public HttpResult getStatisticsDataMonth(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			) {
		HttpResult result;
		result = faultStatisticsService.findAllByMonth(terIds,bTime,eTime,codes);
		return result;
	}
	

	@ApiOperation(value = "异常信息每年统计",notes=""		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", name = "bTime", value = "开始时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "eTime", value = "结束时间", required = true),
		@ApiImplicitParam(paramType = "query", name = "codes", value = "异常码", required = false)
		}) 
	@PostMapping("/year")
	public HttpResult getStatisticsDataYear(
			@RequestParam(name="bTime",required=true) Long bTime,
			@RequestParam(name="eTime",required=true) Long eTime,
			@RequestParam(name="codes",required=false) String codes,
			@RequestBody List<String> terIds
			) {
		HttpResult result;
		result = faultStatisticsService.findAllByYear(terIds,bTime,eTime,codes);
		return result;
	}

}
