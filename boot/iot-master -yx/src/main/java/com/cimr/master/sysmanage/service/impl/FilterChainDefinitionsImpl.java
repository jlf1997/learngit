package com.cimr.master.sysmanage.service.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.cimr.sysmanage.service.FilterChainDefinitions;
import com.cimr.util.INI4j;

public class FilterChainDefinitionsImpl implements FilterChainDefinitions {

	
	private static final Logger log = LoggerFactory.getLogger(FilterChainDefinitionsImpl.class);

	
	private static final String CRLF = "\r\n";

	@Resource
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer();
		sb.append(getFixedAuthRule());
		return sb.toString();
	}

	private String getFixedAuthRule() {
		String fileName = "shiro_base_auth.ini";
		ClassPathResource cp = new ClassPathResource(fileName);
		INI4j ini = null;
		try {
			ini = new INI4j(cp.getFile());
		} catch (IOException e) {
			log.error( "加载文件出错。file:{}", new Object[] { fileName });
		}

		String section = "base_auth";
		Set<String> keys = ini.get(section).keySet();
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			String value = ini.get(section, key);
			sb.append(key).append(" = ").append(value).append("\r\n");
		}

		return sb.toString();
	}

	public synchronized void reCreateFilterChains() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) this.shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			log.error( "getShiroFilter from shiroFilterFactoryBean error!", new Object[0]);

			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();

		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

		manager.getFilterChains().clear();

		this.shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		this.shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());

		Map<String, String> chains = this.shiroFilterFactoryBean.getFilterChainDefinitionMap();

		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = (String) entry.getKey();
			String chainDefinition = ((String) entry.getValue()).trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}
	}

	public void setShiroFilterFactoryBean(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		this.shiroFilterFactoryBean = shiroFilterFactoryBean;
	}
}
