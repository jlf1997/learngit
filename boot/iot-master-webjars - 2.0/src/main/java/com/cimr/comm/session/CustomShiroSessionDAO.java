package com.cimr.comm.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomShiroSessionDAO extends AbstractSessionDAO {

	private static final Logger log = LoggerFactory.getLogger(CustomShiroSessionDAO.class);

	
	private ShiroSessionRepository shiroSessionRepository;

	public ShiroSessionRepository getShiroSessionRepository() {
		return this.shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

	public void update(Session session) throws UnknownSessionException {
		shiroSessionRepository.saveSession(session);
	}

	public void delete(Session session) {
		if (session == null) {
			log.error("Session 不能为null", new Object[0]);

			return;
		}
		Serializable id = session.getId();
		if (id != null)
			shiroSessionRepository.deleteSession(id);
	}

	public Collection<Session> getActiveSessions() {
		return shiroSessionRepository.getAllSessions();
	}

	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		shiroSessionRepository.saveSession(session);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId) {
		return getSession(sessionId);
	}

	public Session getSession(Serializable sessionId) {
		return shiroSessionRepository.getSession(sessionId);
	}
}
