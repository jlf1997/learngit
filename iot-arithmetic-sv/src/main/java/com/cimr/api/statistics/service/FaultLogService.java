package com.cimr.api.statistics.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.jpa.FaultLogJpa;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.jpafinder.Finder;

@Service
public class FaultLogService  extends Finder<FaultLog, Long>{

	@Autowired
	private FaultLogJpa faultLogJpa;

	@Override
	public JpaSpecificationExecutor<FaultLog> specjpa() {
		// TODO Auto-generated method stub
		return faultLogJpa;
	}

	@Override
	public JpaRepository<FaultLog, Long> jpa() {
		// TODO Auto-generated method stub
		return faultLogJpa;
	}

	@Override
	public void addWhere(FaultLog[] t, List<Predicate> predicates, Root<FaultLog> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(FaultLog t) {
		// TODO Auto-generated method stub
		
	}
}
