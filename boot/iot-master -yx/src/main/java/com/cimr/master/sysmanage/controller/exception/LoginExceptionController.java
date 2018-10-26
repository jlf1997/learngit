package com.cimr.master.sysmanage.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/exception" })
public class LoginExceptionController {
	@RequestMapping({ "login" })
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/exception/loginException");
	}
}
