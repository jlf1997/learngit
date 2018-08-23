package com.cimr.api.schedule.interfaces.code;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.schedule.model.code.CodeSenderObject;
import com.cimr.api.schedule.model.comm.HttpResult;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@FeignClient(name= "iot-api-code")
@Hystrix
public interface SendSysCodeController {

	
	
	@ApiOperation(value = "向终端发送系统命令，可自定义参数，api中未限制，所有给的参数将全部传给终端"			
			)	
	@RequestMapping(value="/code/system/command",method=RequestMethod.POST)
	public HttpResult sendSysCommemd(
			@RequestParam(name="title",required=true) Integer title,
			@RequestParam(name="type",required=true) Integer type,
			@RequestParam(name="version",required=true) Integer version,
			@RequestBody CodeSenderObject codeSenderObject) ;
	
	@ApiOperation(value = "向终端发送系统命令：清除信号配置缓存"			
			)	
	@RequestMapping(value="/code/system/clean",method=RequestMethod.POST)
	public HttpResult sendSysCommemdClean(
			@RequestBody CodeSenderObject codeSenderObject);
	
	
}
