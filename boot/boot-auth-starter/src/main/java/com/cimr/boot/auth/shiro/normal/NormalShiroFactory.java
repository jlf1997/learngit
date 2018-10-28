package com.cimr.boot.auth.shiro.normal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;

import com.cimr.boot.auth.shiro.IotShiroFactory;

public class NormalShiroFactory implements IotShiroFactory{

	@Override
	public Map<String, Filter> getFilters() {
		// TODO Auto-generated method stub
		Map<String, Filter> filters = new HashMap<>();
		return filters;
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return new NormalRealm();
	}

	@Override
	public SubjectFactory subjectFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
