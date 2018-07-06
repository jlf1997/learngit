package com.cimr.push.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.push.model.LinkTemplatePushModel;
import com.cimr.push.model.NotificationTemplatePushModel;
import com.cimr.push.model.TransmissionObject;
import com.cimr.push.service.AppPushService;
import com.cimr.push.util.HttpResult;

@RestController
@RequestMapping("/appPush")
public class AppPushController {

	
	
	@Autowired
	private AppPushService appPushService;
	
	/**
	 * 推送到指定cid,并用浏览器打开指定链接
	 * @param content 内容
	 * @param title 标题
	 * @param url 使用浏览器打开的链接
	 * @param cids 发送的cid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pushToCidList/link/demo",method=RequestMethod.GET)
	public HttpResult pushToCidListLink(
		
			) {
		LinkTemplatePushModel pushModel = new LinkTemplatePushModel();
		pushModel.setContent("测试内容");
		pushModel.setTitle("标题");
		pushModel.setUrl("http://www.baidu.com");
		List<String> cids = new ArrayList<>();
		cids.add("acb13ef058ebf94f362f4eb1a9bc3979");
		return appPushService.pushToCidList(cids, pushModel);
		
	}
	
	@RequestMapping(value="/pushToCidList/not/demo",method=RequestMethod.GET)
	public HttpResult pushToCidListnot(
		
			) {
		TransmissionObject transmissionObject = new TransmissionObject();
		transmissionObject.setTransmissionContent("{\"url\":\"http://www.baidu.com\"}");
		transmissionObject.setTransmissionType(1);
		NotificationTemplatePushModel pushModel = new NotificationTemplatePushModel();
		pushModel.setContent("ne");
		pushModel.setTitle("biaoti");
		pushModel.setTransmissionObject(transmissionObject);
		List<String> cids = new ArrayList<>();
		cids.add("acb13ef058ebf94f362f4eb1a9bc3979");
		return appPushService.pushToCidList(cids, pushModel);	
		
	}

	
	/**
	 * 推送到指定cid,并用浏览器打开指定链接
	 * @param content 内容
	 * @param title 标题
	 * @param url 使用浏览器打开的链接
	 * @param cids 发送的cid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pushToCidList/link",method=RequestMethod.POST)
	public HttpResult pushToCidListLink(
			@RequestParam("content") String content,
			@RequestParam("title") String title,
			@RequestParam("url") String url,
			@RequestBody List<String> cids
			) {
		LinkTemplatePushModel pushModel = new LinkTemplatePushModel();
		pushModel.setContent(content);
		pushModel.setTitle(title);
		pushModel.setUrl(url);
		return appPushService.pushToCidList(cids, pushModel);
		
	}
	
	
	/**
	 * 推送到指定cid，并打开应用
	 * @param content 内容
	 * @param title 标题
	 * @param transmissionType 1为立即打开应用 2为等待打开应用
	 * @param transmissionContent 透传内容
	 * @param cids 发送的cids列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pushToCidList/notification",method=RequestMethod.POST)
	public HttpResult pushToCidListNotification(
			@RequestParam("content") String content,
			@RequestParam("title") String title,
			@RequestParam(name="transmissionType",required=false,defaultValue="1") Integer transmissionType,
			@RequestParam("transmissionContent") String transmissionContent,
			@RequestBody List<String> cids
			) {	
		TransmissionObject transmissionObject = new TransmissionObject();
		transmissionObject.setTransmissionContent(transmissionContent);
		transmissionObject.setTransmissionType(transmissionType);
		NotificationTemplatePushModel pushModel = new NotificationTemplatePushModel();
		pushModel.setContent(content);
		pushModel.setTitle(title);
		pushModel.setTransmissionObject(transmissionObject);
		return appPushService.pushToCidList(cids, pushModel);		
	}
	


	/**
	 * 推送到指定别名,并用浏览器打开指定链接
	 * @param content 内容
	 * @param title 标题
	 * @param url 打开的url
	 * @param aliasList 别名类别
	 * @return
	 */
	@RequestMapping(value="/pushToAliasList/link",method=RequestMethod.POST)
	public HttpResult pushToAliasListLink(
			@RequestParam("content") String content,
			@RequestParam("title") String title,
			@RequestParam("url") String url,
			@RequestBody List<String> aliasList
			) {
		LinkTemplatePushModel pushModel = new LinkTemplatePushModel();
		pushModel.setContent(content);
		pushModel.setTitle(title);
		pushModel.setUrl(url);
		return appPushService.pushToAliasList(aliasList, pushModel);
		
	}
	
	/**
	 * 推送到指定别名 ，并打开应用
	 * @param content 内容
	 * @param title 标题
	 * @param transmissionType 透传类型
	 * @param transmissionContent 透传内容
	 * @param aliasList 别名列表
	 * @return
	 */
	@RequestMapping(value="/pushToAliasList/notification",method=RequestMethod.POST)
	public HttpResult pushToAliasListNotification(
			@RequestParam("content") String content,
			@RequestParam("title") String title,
			@RequestParam(name="transmissionType",required=false,defaultValue="1") Integer transmissionType,
			@RequestParam("transmissionContent") String transmissionContent,
			@RequestBody List<String> aliasList
			) {	
		TransmissionObject transmissionObject = new TransmissionObject();
		transmissionObject.setTransmissionContent(transmissionContent);
		transmissionObject.setTransmissionType(transmissionType);
		NotificationTemplatePushModel pushModel = new NotificationTemplatePushModel();
		pushModel.setContent(content);
		pushModel.setTitle(title);
		pushModel.setTransmissionObject(transmissionObject);
		return appPushService.pushToAliasList(aliasList, pushModel);		
	}
	
}
