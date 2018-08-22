package com.cimr.api.code.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.code.jpa.base.CallBackLogJpa;
import com.cimr.api.code.model.base.CallBackLog;
import com.cimr.boot.jpafinder.Finder;

@Service
public class CallBackLogService extends Finder<CallBackLog,Long>{
	
	
	@Autowired
	private CallBackLogJpa callBackLogJpa;

	@Override
	public JpaSpecificationExecutor<CallBackLog> specjpa() {
		// TODO Auto-generated method stub
		return callBackLogJpa;
	}

	@Override
	public JpaRepository<CallBackLog, Long> jpa() {
		// TODO Auto-generated method stub
		return callBackLogJpa;
	}

	@Override
	public void addWhere(CallBackLog[] t, List<Predicate> predicates, Root<CallBackLog> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(CallBackLog t) {
		// TODO Auto-generated method stub
		
	}

	
}
