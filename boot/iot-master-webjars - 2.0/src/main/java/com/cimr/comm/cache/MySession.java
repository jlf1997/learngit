package com.cimr.comm.cache;

import java.util.Date;

import org.apache.shiro.session.Session;

public class MySession {

	private Session session;
	
	private Date saveTime;
	
	public MySession(Session session) {
		super();
		this.session = session;
		this.saveTime = new Date();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}
	
}
