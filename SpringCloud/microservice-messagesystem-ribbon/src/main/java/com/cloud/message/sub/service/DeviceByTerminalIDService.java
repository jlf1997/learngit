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
 * 根据设备编号获取设备信息
 * @author Administrator
 *
 */
@Service
public class DeviceByTerminalIDService {
	private static final Logger logger = LoggerFactory.getLogger(DeviceByTerminalIDService.class);

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 根据设备编号获取设备信息
	 * @param terminalID
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findDeviceListByTerminalID(String terminalID,String stationID) {
		return this.restTemplate.postForObject("http://CLIENT-WULIU//device//findDeviceListByTerminalID?terminalID={terminalID}&stationID={stationID}", null, String.class,
				terminalID,stationID);
	}
	/**
	 * 熔断
	 * @param terminalID
	 * @return
	 */
	public String hiError(String terminalID,String stationID) {
		logger.error("异常类型：熔断\t\n url:http://CLIENT-WULIU//device//findDeviceListByTerminalID");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
