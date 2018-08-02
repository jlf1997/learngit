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
 * 获取时间段内工作时间统计数据
 * @author Administrator
 *
 */
@Service
public class EquipLogCountService {
	private static final Logger logger = LoggerFactory.getLogger(EquipLogCountService.class);

	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 获取时间段内工作时间统计数据
	 * @param terminalID 设备编号
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "hiError")
	public String findList(String terminalID,String startTime,String endTime,String stationID) {
		return this.restTemplate.postForObject("http://CLIENT-WULIU//equipLog//findEquipLogCountList?terminalID={terminalID}&startTime={startTime}&endTime={endTime}&stationID={stationID}",null, String.class,terminalID,startTime,endTime,stationID);
	}
	public String hiError(String terminalID,String startTime,String endTime,String stationID) {
		logger.error("异常类型：熔断\t\n url:http://CLIENT-WULIU//equipLog//findEquipLogCountList");
		return  JSON.toJSONString(new JsonResult(HttpRespStatusConstant.FUSE_CODE,HttpRespStatusConstant.FUSE_MSG));
	}
}
