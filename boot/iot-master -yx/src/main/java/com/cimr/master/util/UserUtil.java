package com.cimr.master.util;

import com.cimr.comm.constants.Constants;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.model.User;

/**
 * 类描述:获取当前登录用户信息的工具类 作者:admin 创建时间:2018年4月16日 下午4:11:24
 */
public class UserUtil {

	/**
	 * 获取当前登录的用户信息,从session获取
	 */
	public static User getLoginUser() {
		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes()).getRequest();
		// User user = (User) request.getSession().getAttribute("loginUser");
		User user = TokenUtil.getToken();
		return user;
	}

	/**
	 * 获取当前登录的用户id
	 * 
	 * @return
	 */
	public static String getLoginUserId() {
		User user = getLoginUser();
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}

	/**
	 * 获取当前登录的用户名称,显示在主页,以示友好
	 * 
	 * @return
	 */
	public static String getLoginUsername() {
		User user = getLoginUser();
		if (user != null) {
			return user.getUsername();
		} else {
			return null;
		}
	}

	public static boolean isLogin() {
		User user = getLoginUser();
		if (null == user) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isAdmin(){
		User user = getLoginUser();
		if (Constants.ADMIN_GROUP.equals(user.getGroupId())){
			return true;
		}
		return false;
	}
}
