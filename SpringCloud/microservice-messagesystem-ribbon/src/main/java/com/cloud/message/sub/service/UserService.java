package com.cloud.message.sub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.cloud.message.constant.HttpRespStatusConstant;
import com.cloud.message.utils.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	
	/**
	 * 根据用户类型查询用户信息
	 * @param stype 1 商砼 2 物流
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findUserList(String stype) {
		System.out.println("stype="+stype);
		return this.restTemplate.postForObject("http://MESSAGESYSTEM//user//findUserList?stype={stype}",null, String.class,stype);
	}
	
	/**
	 * 熔断
	 * @param stype
	 * @return
	 */
	public String hiError(String stype) {
		logger.error("异常类型：熔断\t\n url:http://MESSAGESYSTEM//user//findUserList");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
