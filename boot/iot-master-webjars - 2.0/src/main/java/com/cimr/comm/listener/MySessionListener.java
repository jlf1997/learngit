package com.cimr.comm.listener;

import com.cimr.comm.session.ShiroSessionRepository;
import java.io.PrintStream;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class MySessionListener implements SessionListener {
	private ShiroSessionRepository shiroSessionRepository;

	public void onStart(Session session) {
		System.out.println("on start");
	}

	public void onStop(Session session) {
		System.out.println("on stop");
	}

	public void onExpiration(Session session) {
		this.shiroSessionRepository.deleteSession(session.getId());
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return this.shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
}
