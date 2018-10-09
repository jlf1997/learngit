package com.cimr.sysmanage.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/error" })
public class ErrorController {
	@RequestMapping({ "404" })
	public ModelAndView error404(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/error/404");
	}

	@RequestMapping({ "500" })
	public ModelAndView error500(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/error/500");
	}

	@RequestMapping({ "error" })
	public ModelAndView error(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/error/error");
	}

	@RequestMapping({ "unauthorized" })
	public ModelAndView errorUnauthorized(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/error/unauthorized");
	}

	@RequestMapping({ "invalidSession" })
	public ModelAndView errorInvalidSession(HttpServletRequest req, HttpServletResponse resp, ModelMap data) {
		return new ModelAndView("comm/error/invalidSession");
	}
}
