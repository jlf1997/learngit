package com.cloud.message.sub.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.cloud.message.constant.CommonConstant;
import com.cloud.message.constant.HttpRespStatusConstant;
import com.cloud.message.sub.service.DeviceByStationIDService;
import com.cloud.message.sub.service.DeviceByTerminalIDService;
import com.cloud.message.utils.Des;
import com.cloud.message.utils.JsonResult;

/**
 * 设备信息
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/device")
public class DeviceController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	@Autowired
	DeviceByStationIDService deviceByStationIDService;
	/**
	 * 根据客户编号查所有设备信息
	 */
	@Autowired
	DeviceByTerminalIDService deviceByTerminalIDService;

	@PostMapping("/selectDeviceListByStationID")
	public String selectDeviceListByStationID(String p, HttpServletRequest req) {
		String log = "\nurl:device/selectDeviceListByStationID\nip:" + req.getRemoteAddr() + "\n";
		String result = null;
		if (p == null || p.equals("")) {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.PARAM_IS_NULL_CODE,
					HttpRespStatusConstant.PARAM_IS_NULL_MSG));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String des = URLDecoder.decode(Des.decrypt(p, Des.KEY), "utf-8");
			for (String params : des.split("&")) {
				String[] param = params.split("=");
				map.put(param[0], param[1]);
			}
			String token = (String) map.get("token");
			String recoredNum = (String) map.get("recoredNum");
			String page = (String) map.get("page");
			String stationID = (String) map.get("stationID");
			logger.info(log + "参数：token=" + token + "&stationID=" + stationID + "&recoredNum=" + recoredNum + "&page="
					+ page + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {
				if (stationID == null || stationID.equals("") || stationID.length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
				if (recoredNum == null || recoredNum.length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.RECOREDNUM_IS_NULL_CODE,
							HttpRespStatusConstant.RECOREDNUM_IS_NULL_MSG));
				}
				if (page == null || page.length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.PAGENUM_IS_NULL_CODE,
							HttpRespStatusConstant.PAGENUM_IS_NULL_MSG));
				}
				result = this.deviceByStationIDService.findDeviceListByStationID(stationID, recoredNum, page);
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
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.DEVICE_LIST_FAILED_CODE,
					HttpRespStatusConstant.DEVICE_LIST_FAILED_MSG));
		}

	}

	/**
	 * 根据设备编号查询设备信息
	 * 
	 * @param p
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectDeviceListByTerminalID")
	public String selectDeviceListByTerminalID(String p, HttpServletRequest req) {
		String log = "\nurl:device/selectDeviceListByTerminalID\nip:" + req.getRemoteAddr() + "\n";
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
			String stationID = (String) map.get("stationID");
			logger.info(log + "参数：token=" + token + "&terminalID=" + terminalID + "&stationID=" + stationID + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {
				if (terminalID == null || terminalID.equals("") || terminalID.trim().length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TERMINALID_IS_NULL_CODE,
							HttpRespStatusConstant.TERMINALID_IS_NULL_MSG));
				}
				if (stationID == null || stationID.equals("") || stationID.length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
				result = this.deviceByTerminalIDService.findDeviceListByTerminalID(terminalID, stationID);
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
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.DEVICE_LIST_FAILED_CODE,
					HttpRespStatusConstant.DEVICE_LIST_FAILED_MSG));
		}
	}
}
