package com.cimr.comm.session;

import java.io.Serializable;
import java.util.Collection;
import org.apache.shiro.session.Session;

public abstract interface ShiroSessionRepository {
	public abstract void saveSession(Session paramSession);

	public abstract void deleteSession(Serializable paramSerializable);

	public abstract Session getSession(Serializable paramSerializable);

	public abstract Collection<Session> getAllSessions();
}
