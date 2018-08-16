package com.cimr.api.log.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.history.service.RealDataSignalHistoryService;
import com.cimr.boot.mongodb.dao.MongoDbFindUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description="历史记录相关查询",tags= {"log"})
@RestController
@RequestMapping("/log")
public class LogDemoController {
  
	@Autowired
	private  RealDataSignalHistoryService histroyService;
	
	
	
	@ApiOperation(value = "根据id查询单台设备信息", notes = ""			
			)	  
//	@ApiImplicitParams({ 
//		@ApiImplicitParam(paramType = "path", dataType = "String", name = "id", value = "信息id", required = true) }
//	) 
	@RequestMapping(value="/demo",method=RequestMethod.GET)
	public String findDevInfoById() {
		
		return "ddd";
	}

}
