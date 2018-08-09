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

import com.cimr.api.statistics.jpa.FaultFieldJpa;
import com.cimr.api.statistics.model.FaultField;
import com.cimr.boot.jpafinder.FindBase;

@Service
public class FaultFieldSercvice extends FindBase<FaultField,Long>{

	@Autowired
	private FaultFieldJpa faultFieldJpa;
	@Override
	public JpaSpecificationExecutor<FaultField> specjpa() {
		// TODO Auto-generated method stub
		return faultFieldJpa;
	}

	@Override
	public JpaRepository<FaultField, Long> jpa() {
		// TODO Auto-generated method stub
		return faultFieldJpa;
	}

	@Override
	public void addWhere(FaultField[] t, List<Predicate> predicates, Root<FaultField> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(FaultField t) {
		// TODO Auto-generated method stub
		
	}

	

}
