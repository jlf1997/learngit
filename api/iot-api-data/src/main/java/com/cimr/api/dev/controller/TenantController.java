package com.cimr.api.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.dev.model.mgr.Signal;
import com.cimr.api.dev.model.mgr.Tenant;
import com.cimr.api.dev.service.SignalService;
import com.cimr.api.dev.service.TenantService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description="客户相关操作",tags= {"tenant"})
@RestController
@RequestMapping("/tenant")
public class TenantController {

	
	@Autowired
	private TenantService tenantService;
	
	
	@ApiOperation(value = "查询所有客户信息", notes = ""			
			)	  
	@ApiImplicitParams({ 
	}) 
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public HttpResult findDevInfoById() {
		Tenant t = new Tenant();
		List<Tenant> data = tenantService.findAll(t);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
}
