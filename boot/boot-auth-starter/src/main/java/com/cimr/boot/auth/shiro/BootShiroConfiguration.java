package com.cimr.boot.auth.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.cimr.boot.auth.properties.AuthProperties;
import com.cimr.boot.auth.properties.BootShiroProperties;
import com.cimr.boot.auth.shiro.cas.CasShiroFactory;
import com.cimr.boot.auth.shiro.normal.NormalShiroFactory;

@Configuration
//@EnableConfigurationProperties(value = MongoOptionProperties.class)                                                                        
//@ConditionalOnClass(MongoTemplate.class)                                                                                                     
@ConditionalOnProperty(prefix = "boot.auth.shiro", value = "enable", matchIfMissing = true) 
public class BootShiroConfiguration {

	
	private static final Logger log = LoggerFactory.getLogger(BootShiroConfiguration.class);

	
	
	@Autowired
	private BootShiroProperties bootShiroProperties;
	
	
 
	/**
	 * 创建cas shiro
	 * @return
	 */
	@Bean(name="casShiroFactory")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "boot.auth.shiro", name = "type",havingValue  = "cas", matchIfMissing = false)
	public IotShiroFactory getCasShiroFactory() {
		log.info("cas shiroFactory init");
		return new CasShiroFactory();
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean(name="normalShiroFactory")
	@Primary
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "boot.auth.shiro",name = "type", havingValue  = "normal", matchIfMissing = true)
	public IotShiroFactory getShiroFactory() {
		log.info("normal shiroFactory init");
		return new NormalShiroFactory();
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
        log.info("======base=====");
        filterChainDefinitionMap.putAll(bootShiroProperties.getFilterChainDefinitionMap());
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
