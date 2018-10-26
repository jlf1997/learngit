package com.cimr.master.comm.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.DelegatingFilterProxy;

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
	
	
	
	private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

	@Autowired
	private ShiroProperties shiroProperties;
	
	
    /**
     * 注入 securityManager
     */
	@Bean(name="securityManager")
    public DefaultWebSecurityManager  securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm());
//        log.info("=====================SecurityManager");
        return securityManager;
    }
	
	
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
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
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
	

	
	
	
	@Bean(name="mySessionDAO")
	public SessionDAO customShiroSessionDAO() {
		CustomShiroSessionDAO dao = new CustomShiroSessionDAO();
		dao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		dao.setShiroSessionRepository(shiroSessionRepository());
		return dao;
	}
//	
	@Bean(name="myShiroSessionRepository")
	public ShiroSessionRepository shiroSessionRepository() {
		return new ShiroSessionRepositoryMap();
	}
//	
	@Bean(name="customSessionManager")
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

	@Bean(name="mySimpleCookie")
	public SimpleCookie sessionIdCookie() {
		return new SimpleCookie();
	}
//	
	@Bean(name="mySessionListener")
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
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
	@Bean(name="myRealm")
    public MyRealm myRealm() {
        return new MyRealm();
    }
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {  
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();  
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));   
        filterRegistration.setEnabled(true);  
        filterRegistration.addUrlPatterns("/**");   
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);  
        filterRegistration.setOrder(0);
        return filterRegistration;  
    }  
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(DefaultWebSecurityManager securityManager) {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
            	SecurityUtils.setSecurityManager(securityManager);
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/comm/error/401");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

                container.addErrorPages(error404Page,error500Page,error401Page);
            }
        };
    }
    
   
}
