package com.cimr.comm.token;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cimr.sysmanage.model.User;

public class AuthUtil {
	
	@SuppressWarnings("resource")
	public static void setMockUser(User user) {
		String[] resource = { "spring-shiro.xml" };
		ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(resource);
		SecurityManager securityManager = (SecurityManager) appCtx.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);

		UserToken token = new UserToken(user.getUsername(), user.getPswd());
		SecurityUtils.getSubject().login(token);
	}
}
