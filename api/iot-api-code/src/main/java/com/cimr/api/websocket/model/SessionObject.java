package com.cimr.api.websocket.model;

import java.util.Date;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public class SessionObject {

	private String terid;
	
	private StompHeaderAccessor sa;
	
	private Date deadline;

	public String getTerid() {
		return terid;
	}

	public void setTerid(String terid) {
		this.terid = terid;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public StompHeaderAccessor getSa() {
		return sa;
	}

	public void setSa(StompHeaderAccessor sa) {
		this.sa = sa;
	}
	
	
}
