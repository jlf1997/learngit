package com.cimr.api.code.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.reciver.TerminalReciver;
import com.cimr.api.code.service.CommandsService;
import com.cimr.api.code.service.WarningService;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="指令发送历史查询",tags= {"codeHistory"})
@RestController
@RequestMapping("/codeHistory")
public class CodeHistoryController {
	
	@Autowired
	private CommandsService commandsService;
	
	@Autowired
	private TerminalReciver demoReciver;
	
	
	@ApiOperation(value = "测试用接口", notes = ""			
			)
	@GetMapping("/demo")
	public HttpResult demo() {
		CodeSenderObject codeSenderObject = new CodeSenderObject();
		codeSenderObject.setCodeId("2222");
		codeSenderObject.setNotify_url("http://wwww.baidu.com");
		codeSenderObject.setTelIds(new ArrayList<>());
		commandsService.sendCodeToTerminal("testmessage", codeSenderObject);
		
		HttpResult res = new HttpResult(true,"success");
		return res;
		
	}
}
