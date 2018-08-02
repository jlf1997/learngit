package com.cloud.message.sub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.cloud.message.constant.HttpRespStatusConstant;
import com.cloud.message.sub.controller.DeviceController;
import com.cloud.message.utils.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
/**
 * 设备信息负载均衡控制层
 * @author Administrator
 *
 */
@Service
public class DeviceByStationIDService {
	private static final Logger logger = LoggerFactory.getLogger(DeviceByStationIDService.class);

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 根据客户编号获取设备信息
	 * @param stationID 客户编号
	 * @param recoredNum 需查询条数
	 * @param page 当前条数
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findDeviceListByStationID(String stationID,String recoredNum,String page) {
		return this.restTemplate.postForObject("http://CLIENT-WULIU//device//findDeviceListByStationID?stationID={stationID}&recoredNum={recoredNum}&page={page}",null, String.class,stationID,recoredNum,page);
	}
	
	
	/**
	 * 熔断
	 * @param stationID
	 * @param recoredNum
	 * @param page
	 * @return
	 */
	public String hiError(String stationID,String recoredNum,String page) {
		logger.error("异常类型：熔断\t\n url:http://CLIENT-WULIU//device//findDeviceListByStationID");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
