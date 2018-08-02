package com.cloud.message.sub.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cloud.message.constant.CommonConstant;
import com.cloud.message.constant.HttpRespStatusConstant;
import com.cloud.message.sub.service.GpsRealDataService;
import com.cloud.message.sub.service.GpsVehicleInfoService;
import com.cloud.message.utils.DateUtil;
import com.cloud.message.utils.Des;
import com.cloud.message.utils.JsonResult;

/**
 * gps数据
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/gps")
public class GpsController {
	private static final Logger logger = LoggerFactory.getLogger(GpsController.class);

	@Autowired
	private GpsRealDataService gpsRealDataService;
	@Autowired
	private GpsVehicleInfoService gpsVehicleInfoService;

	/**
	 * 根据客户编号获取所有gps当前数据
	 * 
	 * @param p
	 *            需解密
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectGpsRealData")
	public String selectGpsRealData(String p, HttpServletRequest req) {
		String log = "\nurl:equipLog/selectGpsRealData\nip:" + req.getRemoteAddr() + "\n";
		String result = null;
		if (p == null || p.equals("")) {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.PARAM_IS_NULL_CODE,
					HttpRespStatusConstant.PARAM_IS_NULL_MSG));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String des = java.net.URLDecoder.decode(Des.decrypt(p, Des.KEY), "utf-8");
			for (String params : des.split("&")) {
				String[] param = params.split("=");
				map.put(param[0], param[1]);
			}
			String token = (String) map.get("token");
			String stationID = (String) map.get("stationID");
			logger.info(log + "参数：token=" + token + "&stationID=" + stationID + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {

				if (stationID == null || stationID.equals("") || stationID.trim().length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
				String[] stationArray = JSONArray.parseObject(stationID, String[].class);
				if (stationArray != null && stationArray.length > 0) {
					result = this.gpsRealDataService.findList(stationID);
				} else {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
			} else {
				return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TOKEN_ERROR_CODE,
						HttpRespStatusConstant.TOKEN_ERROR_MSG));
			}
		} catch (Exception e) {
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			logger.error(log + "异常类型：" + e.fillInStackTrace().toString() + "\nLine=" + stackTraceElement.getLineNumber()
					+ "\n" + "Method=" + stackTraceElement.getMethodName());
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.RUNTIME_EXCEPTION_CODE,
					HttpRespStatusConstant.RUNTIME_EXCEPTION_MSG));
		}
		if (result != null && !result.equals("") && !result.equals("{}")) {
			return result;
		} else {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.GPS_LIST_FAILED_CODE,
					HttpRespStatusConstant.GPS_LIST_FAILED_MSG));
		}
	}

	/**
	 * 根据设备编号获取所有gps历史数据
	 * 
	 * @param p
	 *            需解密
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectGpsVehicleInfo")
	public String selectGpsVehicleInfo(String p, HttpServletRequest req) {
		String log = "\nurl:equipLog/selectGpsVehicleInfo\nip:" + req.getRemoteAddr() + "\n";
		String result = null;
		if (p == null || p.equals("")) {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.PARAM_IS_NULL_CODE,
					HttpRespStatusConstant.PARAM_IS_NULL_MSG));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String des = java.net.URLDecoder.decode(Des.decrypt(p, Des.KEY), "utf-8");
			for (String params : des.split("&")) {
				String[] param = params.split("=");
				map.put(param[0], param[1]);
			}
			String token = (String) map.get("token");
			String terminalID = (String) map.get("terminalID");
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			String stationID = (String) map.get("stationID");
			logger.info(log + "参数：token=" + token + "&terminalID=" + terminalID + "&startTime=" + startTime+"&endTime="+endTime
					+ "&stationID=" + stationID + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {
				if (stationID == null || stationID.equals("") || stationID.length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
				if (terminalID == null || terminalID.equals("") || terminalID.trim().length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TERMINALID_IS_NULL_CODE,
							HttpRespStatusConstant.TERMINALID_IS_NULL_MSG));
				}
				if (startTime == null || startTime.equals("") || startTime.trim().length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.START_TIME_IS_NULL_CODE,
							HttpRespStatusConstant.START_TIME_IS_NULL_MSG));
				}
				if (endTime == null || endTime.equals("") || endTime.trim().length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.END_TIME_IS_NULL_CODE,
							HttpRespStatusConstant.END_TIME_IS_NULL_MSG));
				}
				String[] terminalIDArray = JSONArray.parseObject(terminalID, String[].class);
				if(terminalIDArray!=null&&terminalIDArray.length>0){
					result = this.gpsVehicleInfoService.findList(terminalID, stationID, startTime,endTime);
				}else{
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TERMINALID_IS_NULL_CODE,
							HttpRespStatusConstant.TERMINALID_IS_NULL_MSG));
				}
			} else {
				return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TOKEN_ERROR_CODE,
						HttpRespStatusConstant.TOKEN_ERROR_MSG));
			}
		} catch (Exception e) {
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			logger.error(log + "异常类型：" + e.fillInStackTrace().toString() + "\nLine=" + stackTraceElement.getLineNumber()
					+ "\n" + "Method=" + stackTraceElement.getMethodName());
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.RUNTIME_EXCEPTION_CODE,
					HttpRespStatusConstant.RUNTIME_EXCEPTION_MSG));
		}
		if (result != null && !result.equals("") && !result.equals("{}")) {
			return result;
		} else {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.GPS_LIST_FAILED_CODE,
					HttpRespStatusConstant.GPS_LIST_FAILED_MSG));
		}
	}

}
