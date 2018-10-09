package com.cimr.comm.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.sysmanage.bo.UserOnlineBo;
import com.cimr.sysmanage.model.User;
import com.cimr.util.StringUtil;

public class CustomSessionManager {

	
	private static final Logger log = LoggerFactory.getLogger(CustomSessionManager.class);

	
	public static final String SESSION_STATUS = "cimr-frame-online-status";

	private CustomShiroSessionDAO customShiroSessionDAO;
	
	public CustomShiroSessionDAO getCustomShiroSessionDAO() {
		return customShiroSessionDAO;
	}
	
	public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
		this.customShiroSessionDAO = customShiroSessionDAO;
	}

	public List<UserOnlineBo> getAllUser() {
		Collection<Session> sessions = this.customShiroSessionDAO.getActiveSessions();
		List<UserOnlineBo> list = new ArrayList();

		for (Session session : sessions) {
			UserOnlineBo bo = getSessionBo(session);
			if (null != bo) {
				list.add(bo);
			}
		}
		return list;
	}

	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long... userIds) {
		Set<Long> idset = (Set<Long>) StringUtil.array2Set(userIds);

		Collection<Session> sessions = this.customShiroSessionDAO.getActiveSessions();

		List<SimplePrincipalCollection> list = new ArrayList();
		for (Session session : sessions) {
			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if ((null != obj) && ((obj instanceof SimplePrincipalCollection))) {
				SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;

				obj = spc.getPrimaryPrincipal();
				if ((null != obj) && ((obj instanceof User))) {
					User user = (User) obj;

					if ((null != user) && (idset.contains(user.getId()))) {
						list.add(spc);
					}
				}
			}
		}
		return list;
	}

	public UserOnlineBo getSession(String sessionId) {
		Session session = this.customShiroSessionDAO.getSession(sessionId);
		UserOnlineBo bo = getSessionBo(session);
		return bo;
	}

	private UserOnlineBo getSessionBo(Session session) {
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if (null == obj) {
			return null;
		}

		if ((obj instanceof SimplePrincipalCollection)) {
			SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;

			obj = spc.getPrimaryPrincipal();
			if ((null != obj) && ((obj instanceof User))) {
				UserOnlineBo userBo = new UserOnlineBo((User) obj);

				userBo.setLastAccess(session.getLastAccessTime());

				userBo.setHost(session.getHost());

				userBo.setSessionId(session.getId().toString());

				userBo.setLastLoginTime(session.getLastAccessTime());

				userBo.setTimeout(session.getTimeout());

				userBo.setStartTime(session.getStartTimestamp());

				SessionStatus sessionStatus = (SessionStatus) session.getAttribute("cimr-frame-online-status");
				boolean status = Boolean.TRUE.booleanValue();
				if (null != sessionStatus) {
					status = sessionStatus.getOnlineStatus().booleanValue();
				}
				userBo.setSessionStatus(status);
				return userBo;
			}
		}
		return null;
	}

	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		Map<String, Object> map = new HashMap();
		try {
			String[] sessionIdArray = null;
			if (sessionIds.indexOf(",") == -1) {
				sessionIdArray = new String[] { sessionIds };
			} else {
				sessionIdArray = sessionIds.split(",");
			}
			for (String id : sessionIdArray) {
				Session session = this.customShiroSessionDAO.getSession(id);
				SessionStatus sessionStatus = new SessionStatus();
				sessionStatus.setOnlineStatus(status);
				session.setAttribute("cimr-frame-online-status", sessionStatus);
				this.customShiroSessionDAO.update(session);
			}
			map.put("status", Integer.valueOf(200));
			map.put("sessionStatus", Integer.valueOf(status.booleanValue() ? 1 : 0));
			map.put("sessionStatusText", status.booleanValue() ? "踢出" : "激活");
			map.put("sessionStatusTextTd", status.booleanValue() ? "有效" : "已踢出");
		} catch (Exception e) {
			log.error("改变Session状态错误，sessionId{}", new Object[] { sessionIds });

			map.put("status", Integer.valueOf(500));
			map.put("message", "改变失败，有可能Session不存在，请刷新再试！");
		}
		return map;
	}

	public void forbidUserById(String id, Long status) {
		for (UserOnlineBo bo : getAllUser()) {
			String userId = bo.getId();

			if (userId.equals(id)) {
				Session session = this.customShiroSessionDAO.getSession(bo.getSessionId());

				SessionStatus sessionStatus = (SessionStatus) session.getAttribute("cimr-frame-online-status");

				sessionStatus.setOnlineStatus(Boolean.valueOf(status.intValue() == 1));

				this.customShiroSessionDAO.update(session);
			}
		}
	}

	
}
