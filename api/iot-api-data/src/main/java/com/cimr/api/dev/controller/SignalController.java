package com.cimr.api.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.dev.model.mgr.Signal;
import com.cimr.api.dev.service.SignalService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description="信号相关",tags= {"signal"})
@RestController
@RequestMapping("/dev/signal")
public class SignalController {
	@Autowired
	private SignalService signalService;
	
	
	@ApiOperation(value = "根据projectId查询信号信息", notes = ""			
			)	  
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", dataType = "String", name = "projectId", value = "信息id", required = true) }
	) 
	@RequestMapping(value="/info/{projectId}",method=RequestMethod.GET)
	public HttpResult findDevInfoById(@PathVariable(value = "projectId") String projectId) {
		Signal t = new Signal();
		t.setProjectId(projectId);
		List<Signal> data = signalService.findAll(t);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
}
