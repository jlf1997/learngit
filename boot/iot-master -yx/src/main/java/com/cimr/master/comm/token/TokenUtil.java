package com.cimr.master.comm.token;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.cimr.comm.session.CustomSessionManager;
import com.cimr.sysmanage.model.User;
import com.cimr.util.SpringContextUtil;

@SuppressWarnings("unused")
public class TokenUtil {
	
	public static final MyRealm realm = SpringContextUtil.getBean("myRealm", MyRealm.class);

	public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager", CustomSessionManager.class);

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
	 * 方法描述: 计划去掉的方法
	 * 
	 * @return String 作者: admin 创建时间: 2018年5月10日 下午2:50:06 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	private static String getYZM1() {
		String code = (String) getSession().getAttribute("CODE");
		getSession().removeAttribute("CODE");
		return code;
	}

	/**
	 * 方法描述: 计划去掉的方法
	 * 
	 * @param user
	 * @param rememberMe
	 * @return User 作者: admin 创建时间: 2018年5月10日 下午2:50:19 修改人: 修改时间: 修改内容: 修改次数:
	 *         0
	 */
	private static User login1(User user, Boolean rememberMe) {
		UserToken token = new UserToken(user.getUsername(), user.getPswd());
		token.setRememberMe(rememberMe.booleanValue());
		SecurityUtils.getSubject().login(token);
		return getToken();
	}

	/**
	 * 方法描述: 计划去掉的方法
	 * 
	 * @return boolean 作者: admin 创建时间: 2018年5月10日 下午2:50:32 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	private static boolean isLogin1() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 方法描述: 退出登录 void 作者: admin 创建时间: 2018年5月10日 下午2:45:59 修改人: 修改时间: 修改内容:
	 * 修改次数: 0
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 方法描述: 计划去掉的方法 void 作者: admin 创建时间: 2018年5月10日 下午2:50:45 修改人: 修改时间: 修改内容:
	 * 修改次数: 0
	 */
	private static void clearNowUserAuth1() {
//		realm.clearCachedAuthorizationInfo();
	}

	/**
	 * 方法描述: 计划去掉的方法
	 * 
	 * @param userIds
	 *            void 作者: admin 创建时间: 2018年5月10日 下午2:50:56 修改人: 修改时间: 修改内容:
	 *            修改次数: 0
	 */
	private static void clearUserAuthByUserId1(Long... userIds) {
		if ((null == userIds) || (userIds.length == 0))
			return;
		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);

		for (SimplePrincipalCollection simplePrincipalCollection : result) {
			realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
		}
	}

	/**
	 * 方法描述: 计划去掉的方法
	 * 
	 * @param userIds
	 *            void 作者: admin 创建时间: 2018年5月10日 下午2:51:48 修改人: 修改时间: 修改内容:
	 *            修改次数: 0
	 */
	private static void clearUserAuthByUserId1(List<Long> userIds) {
		if ((null == userIds) || (userIds.size() == 0)) {
			return;
		}
		clearUserAuthByUserId1((Long[]) userIds.toArray(new Long[0]));
	}
}
