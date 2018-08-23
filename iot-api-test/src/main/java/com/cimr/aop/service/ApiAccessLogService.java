package com.cimr.aop.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.aop.jpa.ApiAccessLogJpa;
import com.cimr.aop.model.ApiAccessLog;
import com.cimr.boot.jpafinder.FindBase;

@Service
public class ApiAccessLogService extends FindBase<ApiAccessLog,Long>{
	
	@Autowired
	private ApiAccessLogJpa apiAccessLogJpa;

	@Override
	public JpaSpecificationExecutor<ApiAccessLog> specjpa() {
		// TODO Auto-generated method stub
		return apiAccessLogJpa;
	}

	@Override
	public JpaRepository<ApiAccessLog, Long> jpa() {
		// TODO Auto-generated method stub
		return apiAccessLogJpa;
	}

	@Override
	public void addWhere(ApiAccessLog[] t, List<Predicate> predicates, Root<ApiAccessLog> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(ApiAccessLog t) {
		// TODO Auto-generated method stub
		
	}

}
