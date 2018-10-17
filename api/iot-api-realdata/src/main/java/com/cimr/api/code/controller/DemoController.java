package com.cimr.api.code.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.service.DemoService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.Api;

@Api(description="实时数据相关操作",tags= {"latestData"})
@RestController
@RequestMapping("/demoredis")
public class DemoController {

	
	@Autowired
	private DemoService demoService;
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public HttpResult save(
			@RequestParam("key") String key,
			@RequestParam("hkey") String hkey
			) {
		demoService.put(key, hkey, new Date());
		HttpResult res = new HttpResult(true,"");
		return res;
	}
	
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public HttpResult get(
			@RequestParam("key") String key,
			@RequestParam("p") String p
			) {
		HttpResult res = new HttpResult(true,"");
		res.setData(demoService.find(key, p));
		return res;
	}
	
	
}
