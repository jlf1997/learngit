package com.cimr.boot.auth.shiro;

import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;


/**
 * 安全框架自定义的过滤器
 * @author Administrator
 *
 */
public interface IotShiroFactory {

	/**
	 * 获取设置的自定义过滤器
	 * @return
	 */
	public Map<String,Filter> getFilters();
	
	/**
	 * 获取realm
	 * @return
	 */
	public Realm getRealm();
	
	
	public SubjectFactory subjectFactory();
	
	
}
