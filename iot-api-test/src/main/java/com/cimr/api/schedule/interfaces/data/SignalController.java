package com.cimr.api.schedule.interfaces.data;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cimr.api.schedule.model.data.Signal;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@FeignClient(name= "iot-api-data")
@Hystrix
@RequestMapping("/dev/signal")
public interface SignalController {

	
	
	@ApiOperation(value = "根据projectId查询信号信息", notes = ""			
			)	  
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", dataType = "String", name = "projectId", value = "信息id", required = true) }
	) 
	@RequestMapping(value="/info/{projectId}",method=RequestMethod.GET)
	public List<Signal> findDevInfoById(@PathVariable(value = "projectId") String projectId);
}
