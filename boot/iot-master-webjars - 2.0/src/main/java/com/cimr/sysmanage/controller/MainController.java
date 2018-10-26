package com.cimr.sysmanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.util.ShiroUtil;

@Controller
@RequestMapping({ "/main" })
public class MainController {
	@RequiresPermissions({ "/main/index" })
	@FullPage(menu = "")
	@RequestMapping({ "index", "nav/index" })
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		Subject subject = ShiroUtil.getSubject();
		subject.isPermitted("/main/index");
		return new ModelAndView("comm/main/main");
	}

	@FullPage(menu = "")
	@RequestMapping({ "indexaaa" })
	public ModelAndView main1(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		Subject subject = ShiroUtil.getSubject();
		subject.hasRole("100002");

		return new ModelAndView("comm/main/main1");
	}

	@FullPage(menu = "")
	@RequestMapping({ "table", "nav/table" })
	public ModelAndView table(HttpServletRequest request, HttpServletResponse response, ModelMap data) {
		return new ModelAndView("main/table");
	}
}
