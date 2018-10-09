package com.cimr.comm.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.model.User;
import com.cimr.util.AjaxUtil;
import com.cimr.util.LocalFileService;
import com.xiaoleilu.hutool.util.StrUtil;

public class LoginFilter extends AccessControlFilter {
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

	private String unauthorizedUrl;

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		User token = TokenUtil.getToken();
		System.out.println("file location:" + LocalFileService.LOCATION);
		

		if ((null != token) || (isLoginRequest(request, response))) {
			return Boolean.TRUE.booleanValue();
		}
		if (AjaxUtil.isAjax(request)) {
			String url = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
			if (url.equalsIgnoreCase("/ajax/login")) {
				return Boolean.TRUE.booleanValue();
			}
			Map<String, Object> resultMap = new HashMap();
			log.debug("当前用户没有登录，并且是Ajax请求！", new Object[0]);
//			resultMap.put("httpStatus", "300");
//			resultMap.put("message", "当前用户没有登录！");
			resultMap.put("success", false);
			resultMap.put("error", "登录信息已过期,请重新登录!");
			AjaxUtil.out(response, resultMap);
		}
		return Boolean.FALSE.booleanValue();
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {

			saveRequestAndRedirectToLogin(request, response);

		} else if (!AjaxUtil.isAjax(request)) {
			HttpResult result = new HttpResult(false, "您没有足够的权限执行该操作!");
			AjaxUtil.out(response, result);
		} else {
			String unauthorizedUrl = getUnauthorizedUrl();
			if (StrUtil.isNotEmpty(unauthorizedUrl)) {
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			} else {
				WebUtils.toHttp(response).sendError(404);
			}
		}

		return Boolean.FALSE.booleanValue();
	}
}
