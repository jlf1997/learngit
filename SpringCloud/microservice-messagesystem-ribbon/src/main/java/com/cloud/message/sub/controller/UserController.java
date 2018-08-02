package com.cloud.message.sub.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.message.sub.service.UserService;

/**
 * 用户信息
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@RequestMapping(value = "/findUserList", method = RequestMethod.GET)
	public String findUserList(String stype) {
		String log = "\nurl:user/findUserList\nip:"+ "\n";
		System.out.println("进入");
		String result = null;
		result = this.userService.findUserList(stype);
		return result;

	}
}
