
package com.cimr.api.schedule.interfaces.code;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimr.api.schedule.model.code.CodeSenderObject;
import com.cimr.api.schedule.model.comm.HttpResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import io.swagger.annotations.ApiOperation;

@FeignClient(name= "iot-api-code")
@Hystrix
public interface SendCodeController {
	
	
	
	

	@ApiOperation(value = "应用向终端发指令", notes = ""			
			)
	@RequestMapping(value="/code/app/ter/code",method=RequestMethod.POST)
	public HttpResult sendCode(
			@RequestParam("cmdId") String cmdId,
			@RequestParam("cmdTitle") Integer cmdTitle,
			@RequestParam("cmdType") Integer cmdType,
			@RequestBody CodeSenderObject codeSenderObject) ;
	
	
	
	@ApiOperation(value = "应用设置终端调试", notes = "cmdContents与telIds均以逗号隔开"			
			)	
	@RequestMapping(value="/code/app/ter/debug",method=RequestMethod.POST)
	public HttpResult sendDebug(
			@RequestBody CodeSenderObject codeSenderObject) ;
	
	
	@ApiOperation(value = "应用设置终端解除调试", notes = ""			
			)	
	@RequestMapping(value="/code/app/ter/endDebug",method=RequestMethod.POST)
	public HttpResult sendEndDebug(
			@RequestBody CodeSenderObject codeSenderObject) ;
	
	
	
	@ApiOperation(value = "发送指令 获取实时数据"			
			)	
	@RequestMapping(value="/code/app/ter/realData",method=RequestMethod.POST)
	public HttpResult sendCodeToGetRealData(
			@RequestParam("signal") String signal,
			@RequestBody CodeSenderObject codeSenderObject) ;
	
	
	
	
	
	
	@ApiOperation(value = "应用向终端发延时锁机指令", notes = ""			
			)
	@RequestMapping(value="/code/app/ter/code/delayLock",method=RequestMethod.POST)
	public HttpResult sendCode(
			@RequestParam("cmdId") String cmdId,
			@RequestParam("cmdTitle") Integer cmdTitle,
			@RequestParam("cmdType") Integer cmdType,
			@RequestParam("delay") Integer delay,
			@RequestBody CodeSenderObject codeSenderObject);
	
	
	
	

	
}
