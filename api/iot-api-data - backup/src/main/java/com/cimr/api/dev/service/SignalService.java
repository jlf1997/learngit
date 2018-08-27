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

import com.cimr.api.dev.jpa.mgr.SignalJpa;
import com.cimr.api.dev.model.mgr.Signal;
import com.cimr.boot.jpafinder.Finder;

@Service("signalService")
public class SignalService extends Finder<Signal,Long>{
	@Autowired
	private SignalJpa signalJpa;
	
	@Override
	public JpaSpecificationExecutor<Signal> specjpa() {
		// TODO Auto-generated method stub
		return signalJpa;
	}

	@Override
	public JpaRepository<Signal, Long> jpa() {
		// TODO Auto-generated method stub
		return signalJpa;
	}

	@Override
	public void addWhere(Signal[] t, List<Predicate> predicates, Root<Signal> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(Signal t) {
		// TODO Auto-generated method stub
		
	}

}
