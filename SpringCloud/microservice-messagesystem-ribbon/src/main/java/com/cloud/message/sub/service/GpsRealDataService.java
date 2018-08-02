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
/**
 * 当前gps数据业务
 * @author Administrator
 *
 */
@Service
public class GpsRealDataService {
	private static final Logger logger = LoggerFactory.getLogger(GpsRealDataService.class);
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 根据客户编号获取所有gps 当前数据
	 * @param stationID
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findList(String stationID) {
		return this.restTemplate.postForObject("http://CLIENT-WULIU//gps//findGpsRealDataList?stationID={stationID}",null, String.class,stationID);
	}
	public String hiError(String stationID) {
		logger.error("异常类型：熔断\t\n url:http://CLIENT-WULIU//gps//findGpsRealDataList");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
