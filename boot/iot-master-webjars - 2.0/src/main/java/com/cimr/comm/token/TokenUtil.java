package com.cimr.comm.token;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.cimr.comm.session.CustomSessionManager;
import com.cimr.sysmanage.model.User;
import com.cimr.util.SpringContextUtil;

@SuppressWarnings("unused")
public class TokenUtil {
	
//	public static final MyRealm realm = SpringContextUtil.getBean("myRealm", MyRealm.class);

//	public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager", CustomSessionManager.class);

	/**
	 * 方法描述: 获取当前已登录的用户信息
	 * 
	 * @return User 作者: admin 创建时间: 2018年5月10日 下午2:36:28 修改人: 修改时间: 修改内容: 修改次数:
	 *         0
	 */
	public static User getToken() {
		User token = (User) SecurityUtils.getSubject().getPrincipal();
		return token;
	}

	/**
	 * 方法描述: 获取session
	 * 
	 * @return Session 作者: admin 创建时间: 2018年5月10日 下午2:37:04 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 方法描述: 获取已登录用户ID
	 * 
	 * @return String 作者: admin 创建时间: 2018年5月10日 下午2:38:04 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static String getUserId() {
		return getToken() == null ? null : getToken().getId();
	}

	/**
	 * 方法描述: 获取已登录用户姓名
	 * 
	 * @return String 作者: admin 创建时间: 2018年5月10日 下午2:41:39 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static String getFullname() {
		return getToken() == null ? null : getToken().getFullname();
	}

	/**
	 * 方法描述: 向session设置键值对
	 * 
	 * @param key
	 * @param value
	 *            void 作者: admin 创建时间: 2018年5月10日 下午2:38:59 修改人: 修改时间: 修改内容:
	 *            修改次数: 0
	 */
	public static void setVal2Session(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 方法描述: 从session获取值
	 * 
	 * @param key
	 * @return Object 作者: admin 创建时间: 2018年5月10日 下午2:40:02 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static Object getVal2Session(Object key) {
		return getSession().getAttribute(key);
	}


	/**
	 * 方法描述: 退出登录 void 作者: admin 创建时间: 2018年5月10日 下午2:45:59 修改人: 修改时间: 修改内容:
	 * 修改次数: 0
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
}
