package com.cimr.sysmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.comm.config.AppFileProperties;
import com.cimr.comm.config.AppProperties;
import com.cimr.util.UserUtil;

/**
 * 类描述:主菜单管理页面跳转
 * 作者:admin
 * 创建时间:2018年4月26日 上午11:02:52
 */
@Controller
@RequestMapping({ "/manager" })
public class ManagerController {
	
	@FullPage(menu = "manager_userManager")
	@RequestMapping({ "nav/userManager" })
	public ModelAndView userManager(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Boolean isAdmin =  UserUtil.isAdmin();
		String filepath = AppFileProperties.getRootPath();
		mav.addObject("filepath", filepath);
		mav.addObject("isAdmin",isAdmin);
		mav.setViewName("comm/manager/userManagement");
		return mav;
	}

	@FullPage(menu = "manager_roleManager")
	@RequestMapping({ "nav/roleManager" })
	public ModelAndView roleManager(HttpServletRequest request) {
		return new ModelAndView("comm/manager/roleManagement");
	}

	@FullPage(menu = "manager_menuManager")
	@RequestMapping({ "nav/menuManager" })
	public ModelAndView menuManager(HttpServletRequest request) {
		return new ModelAndView("comm/manager/menuManagement");
	}

	@FullPage(menu = "manager_operationManager")
	@RequestMapping({ "nav/operationManager" })
	public ModelAndView operationManager(HttpServletRequest request) {
		return new ModelAndView("comm/manager/operationManagement");
	}

	@FullPage(menu = "manager_permissionManager")
	@RequestMapping({ "nav/permissionManager" })
	public ModelAndView permissionManager(HttpServletRequest request) {
		return new ModelAndView("comm/manager/permissionManager");
	}
	
	@FullPage(menu = "manager_groupManager")
	@RequestMapping({ "nav/groupManager" })
	public ModelAndView groupManager(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("comm/manager/groupManagement");
		return mav;
	}

	@RequestMapping({ "nav/menuIcon" })
	public ModelAndView menuIcon(HttpServletRequest request) {
		return new ModelAndView("comm/manager/menuIcon");
	}
}
