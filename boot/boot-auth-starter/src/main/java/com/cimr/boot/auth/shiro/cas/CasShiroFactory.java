package com.cimr.boot.auth.shiro.cas;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.pac4j.core.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.boot.auth.shiro.IotShiroFactory;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;

public class CasShiroFactory implements IotShiroFactory{
	
	
	
	private static final Logger log = LoggerFactory.getLogger(CasShiroFactory.class);

	
	public static final String CAS_SECURITY_FILTER = "cas";
	
	public static final String CALL_BACK_FILTER = "callbackFilter";
	
	private Config pacConfig;
	
	public CasShiroFactory() {
		pacConfig = new Config();
	}
	
	

	@Override
	public Map<String, Filter> getFilters() {
		Map<String, Filter> filters = new HashMap<>();
		 //安全策略过滤器
        SecurityFilter securityFilter = new SecurityFilter();
        log.info("============filter===============");
        securityFilter.setClients("cas");
        securityFilter.setConfig(pacConfig);
        filters.put(CAS_SECURITY_FILTER, securityFilter);
        //回调过滤器，完成ticket认证
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(pacConfig);
        callbackFilter.setDefaultUrl("");
        filters.put(CALL_BACK_FILTER, callbackFilter);
		return filters;
	}

	@Override
	public Realm getRealm() {
		return new ShiroCasPac4jRealm();
	}

	@Override
	public SubjectFactory subjectFactory() {
		return new Pac4jSubjectFactory();
	}

}
