package com.cimr.master.comm.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class RoleFilter extends AccessControlFilter {
	private static final String LOGIN_URL = "/login";
	private static final String UNAUTHORIZED_URL = "/unauthorized.html";

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String[] arra = (String[]) mappedValue;

		Subject subject = getSubject(request, response);
		for (String role : arra) {
			if (subject.hasRole("role:" + role)) {
				return true;
			}
		}
		return false;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {
			saveRequest(request);
			WebUtils.issueRedirect(request, response, LOGIN_URL);
		} else if (StringUtils.hasText(UNAUTHORIZED_URL)) {
			WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
		} else {
			WebUtils.toHttp(response).sendError(401);
		}

		return false;
	}
}
