package com.cimr.master.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

import com.cimr.comm.config.AppProperties;

public class RequestUtils {
	public static void setCharacterEncoding(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
	}

	public static String getString(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value != null ? value : "";
	}

	public static String getStrAtt(HttpServletRequest request, String paramName) {
		String value = (String) request.getAttribute(paramName);
		return value != null ? value : "";
	}

	public static String[] getArray(HttpServletRequest request, String paramName) {
		return request.getParameterValues(paramName);
	}

	public static byte getByte(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if ((value == null) || (value.length() == 0)) {
			return 0;
		}
		return Byte.parseByte(value);
	}

	public static int getInt(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if ((value == null) || (value.length() == 0)) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getIntAtt(HttpServletRequest request, String paramName) {
		String value = request.getAttribute(paramName).toString();
		if ((value == null) || (value.length() == 0)) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getLong(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if ((value == null) || (value.length() == 0)) {
			return 0L;
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public static short getShort(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if ((value == null) || (value.length() == 0)) {
			return 0;
		}
		return Short.parseShort(value);
	}

	public static boolean getBoolean(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if ((value == null) || (value.length() == 0)) {
			return false;
		}
		return Boolean.valueOf(value).booleanValue();
	}

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + AppProperties.getDomainName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}
}
