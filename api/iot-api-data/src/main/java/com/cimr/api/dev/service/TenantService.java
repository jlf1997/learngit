package com.cimr.api.dev.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.dev.jpa.mgr.TenantJpa;
import com.cimr.api.dev.model.mgr.Tenant;
import com.cimr.boot.jpafinder.Finder;


@Service
public class TenantService extends Finder<Tenant,String>{
	
	@Autowired
	private TenantJpa tenantJpa;

	@Override
	public void addWhere(Tenant[] arg0, List<Predicate> arg1, Root<Tenant> arg2, CriteriaQuery<?> arg3,
			CriteriaBuilder arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JpaRepository<Tenant, String> jpa() {
		// TODO Auto-generated method stub
		return tenantJpa;
	}

	@Override
	public void setSelect(Tenant arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JpaSpecificationExecutor<Tenant> specjpa() {
		// TODO Auto-generated method stub
		return tenantJpa;
	}

	

}
