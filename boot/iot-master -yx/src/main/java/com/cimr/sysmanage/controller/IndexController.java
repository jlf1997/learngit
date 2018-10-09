package com.cimr.sysmanage.controller;

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
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.LocalFileService;
import com.cimr.util.StringUtil;

@Controller
@RequestMapping({ "/index" })
public class IndexController {
	@Autowired
	private UnitFileService unitFileService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@FullPage(menu = "indexPage")
	@RequestMapping({ "indexPage", "nav/indexPage" })
	public ModelAndView indexPage(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		ModelAndView mav = new ModelAndView();
		String userId = TokenUtil.getUserId();
		User user = this.userService.selectObjById_common(userId);
		mav.setViewName("comm/index/indexPage");
        UnitFile unitFile = unitFileService.selectObjById_common(user.getAvatar());
        String fileBasePath = LocalFileService.LOCATION;
        String defaultHead = AppProperties.getDefaultHead();
        if (null == unitFile || StringUtil.isEmpty(unitFile.getSourceUrl())){
            //图像不存在
            unitFile = new UnitFile();
            unitFile.setSourceUrl(defaultHead);
        }else {
            //图像不存在
            File file = new File(fileBasePath+"/"+unitFile.getSourceUrl());
            if (!file.exists()){
                unitFile.setSourceUrl(defaultHead);
            }
        }
		mav.addObject("userHeader", unitFile);
		Group group = groupService.selectObjById_common(user.getGroupId());
		mav.addObject("userName", user.getUsername());
		mav.addObject("groupName",group.getGroupName());
//		String filepath = ConfUtil.getConf("file.root.path");
		String upath =  AppFileProperties.getServerPath();
		String url = req.getScheme()+"://"+ req.getServerName()+":"+req.getServerPort()+"/"+upath+"/";
		mav.addObject("filepath", url);
		mav.addObject("unitFile", unitFileService.selectObjById_common(user.getAvatar()));
		return mav;
	}

	@FullPage(menu = "home")
	@RequestMapping({ "home", "nav/home" })
	public ModelAndView indexHome(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/index/home");
//		return new ModelAndView("redirect:/deviceLocation/nav/management");

	}
}
