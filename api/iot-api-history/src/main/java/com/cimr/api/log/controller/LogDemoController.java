package com.cimr.api.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.history.service.RealDataSignalHistoryService;

import io.swagger.annotations.Api;


@Api(description="历史记录相关查询",tags= {"log"})
@RestController
@RequestMapping("/log")
public class LogDemoController {
  
	@Autowired
	private  RealDataSignalHistoryService histroyService;
	
	


}
