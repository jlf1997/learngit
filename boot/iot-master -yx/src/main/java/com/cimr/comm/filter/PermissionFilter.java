package com.cimr.comm.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.sysmanage.service.MenuService;
import com.cimr.util.AjaxUtil;
import com.cimr.util.StringUtil;
import com.xiaoleilu.hutool.util.StrUtil;

public class PermissionFilter extends AccessControlFilter {

	
	private static final Logger log = LoggerFactory.getLogger(PermissionFilter.class);

	@Autowired
	private MenuService menuService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if (AjaxUtil.isAjax(request)) {
			return true;
		}

		Subject subject = getSubject(request, response);
		if (null != mappedValue) {
			String[] arra = (String[]) mappedValue;
			for (String permission : arra) {
				if (subject.isPermitted(permission)) {
					return true;
				}
			}
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String uri = httpRequest.getRequestURI();
		String basePath = httpRequest.getContextPath();
		if ((null != uri) && (uri.startsWith(basePath))) {
			uri = uri.replaceFirst(basePath, "");
		}
		List<MenuBo> menuList = menuService.getMenuList();
		String menuPermission = null;
		for (MenuBo menuBo : menuList) {
			if (StringUtil.valid(menuBo.getHref()) && (StrUtil.removePrefix(menuBo.getHref(), "/").equalsIgnoreCase(StrUtil.removePrefix(uri, "/")))) {
				menuPermission = menuBo.getMenuKey();
			}
		}
		if (StringUtil.valid(menuPermission) && (subject.isPermitted(menuPermission))) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (!AjaxUtil.isAjax(request)) {
			Subject subject = getSubject(request, response);
			if (null == subject.getPrincipal()) {
				saveRequest(request);
				WebUtils.issueRedirect(request, response, "/login");
			} else if (StringUtils.hasText("/error/unauthorized")) {
				WebUtils.issueRedirect(request, response, "/error/unauthorized");
			} else {
				WebUtils.toHttp(response).sendError(401);
			}
		}

		return false;
	}
}
