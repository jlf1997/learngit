package com.cimr.comm.cache;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.session.Session;

public class SessionCache {
	
	private static final long SESSION_VAL_TIME_SPAN = 1800000L;
	
	private static final ConcurrentMap<String, MySession> sessionMap = new ConcurrentHashMap<>();

	/**
	 * 方法描述: 设置缓存数据
	 * @param key
	 * @param obj
	 * @param cacheTime
	 * @param timeunit
	 * 		void
	 * 作者:    admin
	 * 创建时间: 2018年1月31日 下午2:56:49
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	public static void saveSession(String key, Session session) {
		MySession mySession = new MySession(session);
		sessionMap.put(key, mySession);
	}
	
	/**
	 * 方法描述: 获取缓存数据
	 * @param clazz
	 * @param key
	 * @return
	 * 		T
	 * 作者:    admin
	 * 创建时间: 2018年1月31日 下午2:56:57
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	public static Session getSession(String key) {
		MySession mySession = sessionMap.get(key);
		if (null != mySession) {
			Date saveTime = mySession.getSaveTime();
			if (new Date().getTime() - saveTime.getTime() > SESSION_VAL_TIME_SPAN) {
				sessionMap.remove(saveTime);
				return null;
			} else {
				return mySession.getSession();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 方法描述: 获取所有session
	 * @param pattern
	 * @return
	 * 		Set<String>
	 * 作者:    admin
	 * 创建时间: 2018年4月24日 上午10:27:14
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	public static Set<Session> getAllSession() {
		Set<Session> sessionSet = new HashSet<>();
		Iterator<MySession> it = sessionMap.values().iterator();
		while (it.hasNext()) {
			sessionSet.add(it.next().getSession());
		}
		return sessionSet;
	}
	
	/**
	 * 方法描述: 删除键值对
	 * @param key
	 * 		void
	 * 作者:    admin
	 * 创建时间: 2018年2月27日 下午5:21:32
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	public static void delete(String key) {
		sessionMap.remove(key);
	}
}
