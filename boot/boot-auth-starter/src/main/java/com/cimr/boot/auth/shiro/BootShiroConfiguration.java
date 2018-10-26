package com.cimr.boot.auth.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.pac4j.core.config.Config;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.cimr.boot.auth.shiro.cas.Pac4JShiroFactory;
import com.cimr.boot.auth.shiro.properties.AuthProperties;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;

@Configuration
//@EnableConfigurationProperties(value = MongoOptionProperties.class)                                                                        
//@ConditionalOnClass(MongoTemplate.class)                                                                                                     
@ConditionalOnProperty(prefix = "boot.auth.shiro", value = "enable", matchIfMissing = true) 
public class BootShiroConfiguration {

	
	
	@Autowired
	private AuthProperties authProperties;
	
 
	@Bean
	@ConditionalOnMissingBean
	public IotShiroFactory getIotFilters() {
		return new Pac4JShiroFactory();
	}
    
	@Bean
	@ConditionalOnMissingBean
	public Realm getRealm(IotShiroFactory iotShiroFactory) {
		return  iotShiroFactory.getRealm();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SubjectFactory getSubjectFactory(IotShiroFactory iotShiroFactory) {
		return iotShiroFactory.subjectFactory();
	}
 
   
 
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(Realm realm,SubjectFactory subjectFactory) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setSubjectFactory(subjectFactory);
        return defaultWebSecurityManager;
    }
 
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistrationBean.addInitParameter("targetFilterLifecycle", "true");
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
 
    @Bean(name = "shiroFilter")
    protected ShiroFilterFactoryBean shiroFilterFactoryBean(IotShiroFactory iotShiroFactory,DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
 
        //过滤器设置
        Map<String, Filter> filters = new HashMap<String, Filter>();
       
        //自定义过滤器
        Map<String, Filter> deffilters =  iotShiroFactory.getFilters();
        if(deffilters!=null && !deffilters.isEmpty()) {
        	 filters.putAll(deffilters);
        }
        filterFactoryBean.setFilters(filters);
 
 
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
//        filterFactoryBean.setLoginUrl(authProperties.getLoginUrl());
//        // 设置无权限时跳转的 url;
//        filterFactoryBean.setUnauthorizedUrl(authProperties.getUnauthorizedUrl());
        
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        //默认配置
        filterChainDefinitionMap.put("/**", "anon");
        //自定义配置
        filterChainDefinitionMap.putAll(authProperties.getFilterChainDefinitionMap());
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactoryBean;
        
        
    }
 
 
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
 
    /**
     * 开启 shiro aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
	
	
	
	
}
