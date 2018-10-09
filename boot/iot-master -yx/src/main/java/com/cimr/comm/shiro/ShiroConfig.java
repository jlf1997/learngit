package com.cimr.comm.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.catalina.startup.UserConfig;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cimr.comm.filter.LoginFilter;
import com.cimr.comm.filter.PermissionFilter;
import com.cimr.comm.listener.MySessionListener;
import com.cimr.comm.session.CustomSessionManager;
import com.cimr.comm.session.CustomShiroSessionDAO;
import com.cimr.comm.session.ShiroSessionRepository;
import com.cimr.comm.session.ShiroSessionRepositoryMap;
import com.cimr.comm.token.MyRealm;

@Configuration
public class ShiroConfig {
	
	@Autowired
	private ShiroProperties shiroProperties;

	@Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = shiroProperties.getFilterChainDefinitionMap();
        
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilters(filters);
        filters.put("login", new LoginFilter());
        filters.put("permission", new PermissionFilter());
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
	
	@Bean
	public SessionDAO customShiroSessionDAO() {
		CustomShiroSessionDAO dao = new CustomShiroSessionDAO();
		dao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		dao.setShiroSessionRepository(shiroSessionRepository());
		return dao;
	}
//	
	@Bean
	public ShiroSessionRepository shiroSessionRepository() {
		return new ShiroSessionRepositoryMap();
	}
//	
	@Bean
	public CustomSessionManager customSessionManager() {
		CustomSessionManager sessionmanager = new CustomSessionManager();
//		sessionmanager.setGlobalSessionTimeout(1800000);
//		sessionmanager.setSessionValidationInterval(1800000);
//		sessionmanager.setSessionDAO(customShiroSessionDAO());
//		List<SessionListener> listeners = new ArrayList<>();
//		listeners.add(mySessionListener());
//		sessionmanager.setSessionListeners(listeners);
////		sessionmanager.setSessionValidationScheduler(sessionValidationScheduler());
//		sessionmanager.setSessionValidationSchedulerEnabled(true);
//		sessionmanager.setDeleteInvalidSessions(true);
//		sessionmanager.setSessionIdCookie(sessionIdCookie());
		return sessionmanager;
	}

	@Bean
	public SimpleCookie sessionIdCookie() {
		return new SimpleCookie();
	}
//	
	@Bean
	public SessionListener mySessionListener() {
		MySessionListener mySessionListener = new MySessionListener();
		mySessionListener.setShiroSessionRepository(shiroSessionRepository());
		return mySessionListener;
	}
//	
//	@Bean
//	public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
//		ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
//		sessionValidationScheduler.setInterval(1800000);
//		sessionValidationScheduler.setSessionManager(mySessionManager());
//		return sessionValidationScheduler;
//	}

    /**
     * 注入 securityManager
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }
    
   
}
