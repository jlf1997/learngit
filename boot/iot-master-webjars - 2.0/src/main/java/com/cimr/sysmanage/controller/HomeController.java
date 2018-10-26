package com.cimr.sysmanage.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.comm.config.AppFileProperties;
import com.cimr.comm.config.AppProperties;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.dto.HttpResult;
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
		data.put("projectName", AppProperties.getPlatformName());
		ModelAndView mav = new ModelAndView(appProperties.getDefaultPage(),data);
		return mav;
	}
	
	@RequestMapping({ "/permission/getList" })
	@ResponseBody
	public HttpResult getPermissionList() {
		HttpResult res = new HttpResult(true,"");
		Map<String,String> data = new HashMap<>();
		data.put("1", "1");
		data.put("2", "0");
		data.put("3", "0");
		data.put("4", "1");
		res.setData(data);
		return res;
	}
}
