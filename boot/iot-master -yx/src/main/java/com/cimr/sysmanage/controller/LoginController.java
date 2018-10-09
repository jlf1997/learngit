package com.cimr.sysmanage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.sysmanage.service.UserService;

@Controller
@RequestMapping({ "/" })
public class LoginController {
	@Resource
	private UserService userService;

	@FullPage(menu = "")
	@RequestMapping({ "index", "nav/index" })
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("index");
	}

	@FullPage(menu = "")
	@RequestMapping({ "login" })
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/login/login", data);
	}

	@FullPage(menu = "")
	@RequestMapping({ "login/blank" })
	public ModelAndView blank(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/login/loginBlank", data);
	}
}
