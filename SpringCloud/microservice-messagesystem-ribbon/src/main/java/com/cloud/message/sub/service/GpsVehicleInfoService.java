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
 * gps历史数据业务
 * @author Administrator
 *
 */

@Service
public class GpsVehicleInfoService {
	private static final Logger logger = LoggerFactory.getLogger(GpsVehicleInfoService.class);

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 获取gps历史数据业务
	 * @param terminalID 设备编号
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findList(String terminalID,String stationID,String startTime,String endTime) {
		return restTemplate.postForObject("http://CLIENT-WULIU//gps//findGpsVehicleInfoList?terminalID={terminalID}&stationID={stationID}&startTime={startTime}&endTime={endTime}", null,String.class,terminalID,stationID,startTime,endTime);
	}
	
	
	public String hiError(String terminalID,String stationID,String startTime,String endTime) {
		logger.error("异常类型：熔断\t\n url:http://CLIENT-WULIU//gps//findGpsVehicleInfoList");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
