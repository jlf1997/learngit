package com.cimr.master.util;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class AjaxUtil {
	public static boolean isAjax(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	private static final Logger log = LoggerFactory.getLogger(AjaxUtil.class);

	public static void out(ServletResponse response, Object result) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			log.debug(JSONObject.fromObject(result).toString());
		} catch (Exception e) {
			log.error( "输出JSON报错。", new Object[0]);
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}
}
