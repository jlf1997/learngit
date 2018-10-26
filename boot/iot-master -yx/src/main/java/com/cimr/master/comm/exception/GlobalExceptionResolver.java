package com.cimr.master.comm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.util.AjaxUtil;
import com.cimr.util.LogsUtil;

public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {
//	private static final Log log = LogFactory.get();
	
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionResolver.class);


	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			if (AjaxUtil.isAjax(request)) {
				HttpResult result = new HttpResult(false, ex.getMessage());
				AjaxUtil.out(response, result);
				return null;
			}
			try {
				throw new NotLoginException("请重新登录。");
			} catch (NotLoginException e) {
				e.printStackTrace();

				return getModelAndView("comm/exception/loginException", ex, request);
			}
		}

		String viewName = determineViewName(ex, request);
		response.setCharacterEncoding("UTF-8");
		if (viewName != null) {
			if (!AjaxUtil.isAjax(request)) {

				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode.intValue());
				}
				System.out.println("JSP格式返回" + viewName);
				return getModelAndView(viewName, ex, request);
			}

			HttpResult result = new HttpResult(false, ex.getMessage());
			AjaxUtil.out(response, result);

			log.error("异常信息："+LogsUtil.getStackTrace(ex));
			System.out.println("JSON格式返回" + viewName);
			return null;
		}

		return null;
	}
}
