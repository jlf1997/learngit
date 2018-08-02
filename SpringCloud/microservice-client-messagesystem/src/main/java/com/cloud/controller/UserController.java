package com.cloud.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.constant.HttpRespStatusConstant;
import com.cloud.constant.MemcachedConstant;
import com.cloud.entity.User;
import com.cloud.service.UserService;
import com.cloud.util.JsonResult;
import com.cloud.util.MemcachedUtils;

import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 用户业务控制层
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	/**
	 * 根据stype查询用户数据
	 * 
	 * @param stype
	 *            用户类型
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findUserList", method = RequestMethod.POST)
	public JsonResult findUserList(String stype) {
		String log = "\nurl:user/findUserList\nip:" + "\n";
		logger.info(log + "参数：stype=" + stype + "\n");
		//logger.error(log + "异常类型：" + "测试" + "\nLine=" );
		if (stype == null || stype.equals("") || stype.trim().length() == 0) {
			return new JsonResult(HttpRespStatusConstant.STYPE_LIST_FAILED_CODE,
					HttpRespStatusConstant.STYPE_LIST_FAILED_MSG);
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.setSuccess("true");
		return jsonResult;
	}
	
	
}
