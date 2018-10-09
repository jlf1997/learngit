package com.cimr.comm.session;

import java.io.Serializable;

public class SessionStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean onlineStatus = Boolean.TRUE;

	public Boolean isOnlineStatus() {
		return this.onlineStatus;
	}

	public Boolean getOnlineStatus() {
		return this.onlineStatus;
	}

	public void setOnlineStatus(Boolean onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
}
