package com.cimr.api.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.dev.model.mgr.Commands;
import com.cimr.api.dev.service.CommandsService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/command")
public class Commandcontroller {

	@Autowired
	private CommandsService commandsService;
	
	@ApiOperation(value = "查询指令内容", notes = "根据指令id查询指令内容"			
			)	  
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", dataType = "String", name = "number", value = "指令id", required = true) }
	) 
	@GetMapping("/contents/{number}")
	public HttpResult getCommandsById(@PathVariable(value = "number") String number){
		HttpResult res = new HttpResult(true,"success");
		Commands com = commandsService.getCommandsByNumber(number);
		res.setData(com);
		return res;
	}
}
