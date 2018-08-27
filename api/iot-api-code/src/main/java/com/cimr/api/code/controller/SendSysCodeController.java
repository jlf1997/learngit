package com.cimr.api.code.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.model.mgr.Message;
import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.service.CodeHistoryService;
import com.cimr.api.code.service.CommandsService;
import com.cimr.api.code.util.MessageUtil;
import com.cimr.boot.comm.model.HttpResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description="系统指令操作，只提供给管控平台使用",tags= {"systemCode"})
@RestController
@RequestMapping("/code")
public class SendSysCodeController {

	
	private static final Logger log = LoggerFactory.getLogger(SendSysCodeController.class);

	@Autowired
	private CodeHistoryService codeHistoryService;
	
	@Autowired
	private CommandsService commandsService;
	
	@ApiOperation(value = "向终端发送系统命令，可自定义参数，api中未限制，所有给的参数将全部传给终端"			
			)	
	@RequestMapping(value="/system/command",method=RequestMethod.POST)
	public HttpResult sendSysCommemd(
			HttpServletRequest request,
			@RequestParam(name="title",required=true) Integer title,
			@RequestParam(name="type",required=true) Integer type,
			@RequestParam(name="version",required=true) Integer version,
			@RequestBody CodeSenderObject codeSenderObject) {
		Message message = null;
		try {
			if(codeSenderObject==null || codeSenderObject.getTelIds()==null) {
				message = MessageUtil.getSysMessage(version,type,title,request, null);
			}else {
				message = MessageUtil.getSysMessage(version,type,title,request, MessageUtil.convertTerminalModelListToStringList(codeSenderObject.getTelIds()));
			}
			
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			Long idHistory  =commandsService.sendCodeToTerminalByKafka(messageJson,codeSenderObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpResult(false,"发送失败");
		}
		return new HttpResult(true,"发送成功");
	}
	
	
	@ApiOperation(value = "向终端发送系统命令：清除信号配置缓存"			
			)	
	@RequestMapping(value="/system/clean",method=RequestMethod.POST)
	public HttpResult sendSysCommemdClean(
			HttpServletRequest request,
			@RequestBody CodeSenderObject codeSenderObject) {
		Message message = null;
		try {
			if(codeSenderObject==null || codeSenderObject.getTelIds()==null) {
				message = MessageUtil.getSysMessage(1,50,13,null, null);
			}else {
				message = MessageUtil.getSysMessage(1,50,13,null, MessageUtil.convertTerminalModelListToStringList(codeSenderObject.getTelIds()));
			}
			
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			Long idHistory = commandsService.sendCodeToTerminalByKafka(messageJson,codeSenderObject);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpResult(false,"发送失败");
		}
		return new HttpResult(true,"发送成功");
	}
	
	
//	@KafkaListener(topics="topicTest")
//	private void listener(String message) {
//		System.out.println("getMessge:"+message);
//	}
//	@RequestMapping(value="/demo",method=RequestMethod.GET)
//	public  String sendMessageDemo() {
//		KafkaTemplate.send("topicTest","message test from clused");
//		return "success";
//	}
}
