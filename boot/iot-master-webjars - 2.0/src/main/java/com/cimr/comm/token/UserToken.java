package com.cimr.comm.token;

import java.io.Serializable;
import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pswd;

	public UserToken(String username, String pswd) {
		super(username, pswd);
		this.pswd = pswd;
	}

	public String getPswd() {
		return this.pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}
