package com.cimr.api.statistics.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.statistics.service.FaultLogService;

@RequestMapping("statistics/fault")
@RestController
public class FaultStatisticsController {
	
	@Autowired
	private FaultLogService faultLogService;
	
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

}
