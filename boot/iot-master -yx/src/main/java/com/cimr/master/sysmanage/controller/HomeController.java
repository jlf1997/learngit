package com.cimr.master.sysmanage.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.comm.config.AppFileProperties;
import com.cimr.comm.config.AppProperties;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.model.Group;
import com.cimr.sysmanage.model.UnitFile;
import com.cimr.sysmanage.model.User;
import com.cimr.util.LocalFileService;
import com.cimr.util.StringUtil;

@Controller
@RequestMapping({ "/" })
public class HomeController {

	@Autowired
	private AppProperties appProperties;
	
	@FullPage(menu = "indexPage")
	@RequestMapping({ "" })
	public ModelAndView indexPage(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		ModelAndView mav = new ModelAndView(appProperties.getDefaultPage());
		return mav;
	}
}
