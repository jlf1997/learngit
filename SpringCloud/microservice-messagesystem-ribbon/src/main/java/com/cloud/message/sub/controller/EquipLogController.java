package com.cloud.message.sub.controller;

import java.io.UnsupportedEncodingException;
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
import com.cloud.message.sub.service.EquipLogCountService;
import com.cloud.message.sub.service.EquipLogItemService;
import com.cloud.message.sub.service.StationEquipLogCountService;
import com.cloud.message.utils.DateUtil;
import com.cloud.message.utils.Des;
import com.cloud.message.utils.JsonResult;

/**
 * 工作数据
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/equipLog")
public class EquipLogController {
	private static final Logger logger = LoggerFactory.getLogger(EquipLogController.class);

	@Autowired
	EquipLogItemService equipLogItemService;
	@Autowired
	EquipLogCountService equipLogCountService;
	@Autowired
	StationEquipLogCountService stationEquipLogCountService;

	/**
	 * 获取时间段内工作数据条目
	 * 
	 * @param p
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectWorkTimeList")
	public String selectWorkTimeList(String p, HttpServletRequest req) {
		String log = "\nurl:equipLog/selectWorkTimeList\nip:" + req.getRemoteAddr() + "\n";
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
			logger.info(log + "参数：token=" + token + "&terminalID=" + terminalID + "&startTime=" + startTime
					+ "&endTime=" + endTime + "&stationID=" + stationID + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {
				if (DateUtil.getDayNum(startTime, endTime) > 31) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.OUT_OF_DATE_CODE,
							HttpRespStatusConstant.OUT_OF_DATE_MSG));

				}
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
				String[] terminalArray = JSONArray.parseObject(terminalID, String[].class);
				if(terminalArray!=null&&terminalArray.length>0){
					result = this.equipLogItemService.findList(terminalID, startTime, endTime, stationID);

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
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_CODE,
					HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_MSG));
		}
	}

	/**
	 * 统计时间
	 * 
	 * @param p
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectEquipLogCountList")
	public String selectEquipLogCountList(String p, HttpServletRequest req) {
		String log = "\nurl:equipLog/selectEquipLogCountList\nip:" + req.getRemoteAddr() + "\n";
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
			logger.info(log + "参数：token=" + token + "&terminalID=" + terminalID + "&startTime=" + startTime
					+ "&endTime=" + endTime + "&stationID=" + stationID + "\n");
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
				String[] terminalArray = JSONArray.parseObject(terminalID, String[].class);
				if(terminalArray!=null&&terminalArray.length>0){
					result = this.equipLogCountService.findList(terminalID, startTime, endTime, stationID);

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
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_CODE,
					HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_MSG));
		}
	}

	/**
	 * 根据客户编号统计所有设备工作时间
	 * 
	 * @param p
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@PostMapping("/selectEquipLogCountListByStationID")
	public String selectEquipLogCountListByStationID(String p, HttpServletRequest req) {
		String log = "\nurl:equipLog/selectEquipLogCountListByStationID\nip:" + req.getRemoteAddr() + "\n";
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
			String startTime = (String) map.get("startTime");
			String endTime = (String) map.get("endTime");
			logger.info(log + "参数：token=" + token + "&stationID=" + stationID + "&startTime=" + startTime + "&endTime="
					+ endTime + "\n");
			if (token != null && token.equals(CommonConstant.TOKEN)) {
				if (stationID == null || stationID.equals("") || stationID.trim().length() <= 2) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
				if (startTime == null || startTime.equals("") || startTime.trim().length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.START_TIME_IS_NULL_CODE,
							HttpRespStatusConstant.START_TIME_IS_NULL_MSG));
				}
				if (endTime == null || endTime.equals("") || endTime.trim().length() == 0) {
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.END_TIME_IS_NULL_CODE,
							HttpRespStatusConstant.END_TIME_IS_NULL_MSG));
				}
				String[] stationIDArray = JSONArray.parseObject(stationID, String[].class);
				if(stationIDArray!=null&&stationIDArray.length>0){
					result = this.stationEquipLogCountService.findList(stationID, startTime, endTime);

				}else{
					return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.STATIONID_IS_NULL_CODE,
							HttpRespStatusConstant.STATIONID_IS_NULL_MSG));
				}
			} else {
				return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.TOKEN_ERROR_CODE,
						HttpRespStatusConstant.TOKEN_ERROR_MSG));
			}
		} catch (Exception e) {
			logger.error(log + "异常类型：" + e.fillInStackTrace());
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.RUNTIME_EXCEPTION_CODE,
					HttpRespStatusConstant.RUNTIME_EXCEPTION_MSG));
		}
		if (result != null && !result.equals("") && !result.equals("{}")) {
			return result;
		} else {
			return JSON.toJSONString(new JsonResult(HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_CODE,
					HttpRespStatusConstant.EQUIPLOG_LIST_FAILED_MSG));
		}
	}

}
