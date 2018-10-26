package com.cimr.master.comm.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.comm.cache.SessionCache;
import com.cimr.util.LogsUtil;

public class ShiroSessionRepositoryMap implements ShiroSessionRepository {

	
	private static final Logger log = LoggerFactory.getLogger(ShiroSessionRepositoryMap.class);

	
	@Override
	public void saveSession(Session session) {
		if ((session == null) || (session.getId() == null)) {
			throw new NullPointerException("session is empty");
		}
		try {
			SessionCache.saveSession(session.getId().toString(), session);
		} catch (Exception e) {
			log.error("save session error，id:{}", new Object[] { session.getId() });
			log.error("发生异常:"+LogsUtil.getStackTrace(e));
		}
	}

	@Override
	public void deleteSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty");
		}
		try {
			SessionCache.delete(id.toString());
		} catch (Exception e) {
			log.error( "删除session出现异常，id:{}", new Object[] { id });
		}
	}

	@Override
	public Session getSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty");
		}
		Session session = null;
		try {
			session = SessionCache.getSession(id.toString());
		} catch (Exception e) {
			log.error("获取session异常，id:{}", new Object[] { id });
		}

		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		Collection<Session> sessions = null;
		try {
			sessions = SessionCache.getAllSession();
		} catch (Exception e) {
			log.error("获取全部session 异常", new Object[0]);
		}
		
		return sessions;
	}
}
