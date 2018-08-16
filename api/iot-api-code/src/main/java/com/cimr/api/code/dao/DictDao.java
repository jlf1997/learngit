package com.cimr.api.code.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.jpa.mgr.DictJpa;
import com.cimr.api.code.model.mgr.Dict;
import com.cimr.boot.jpafinder.Finder;

@Repository
public class DictDao extends Finder<Dict,Long>{
	
	
	@Autowired
	private DictJpa dictJpa;

	@Override
	public JpaSpecificationExecutor<Dict> specjpa() {
		// TODO Auto-generated method stub
		return dictJpa;
	}

	@Override
	public JpaRepository<Dict, Long> jpa() {
		// TODO Auto-generated method stub
		return dictJpa;
	}

	@Override
	public void addWhere(Dict[] t, List<Predicate> predicates, Root<Dict> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(Dict t) {
		// TODO Auto-generated method stub
		
	}

}
