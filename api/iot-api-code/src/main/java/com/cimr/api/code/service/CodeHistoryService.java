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

import com.cimr.api.code.jpa.base.CodeHistoryJpa;
import com.cimr.api.code.model.base.CodeHistory;
import com.cimr.boot.jpafinder.Finder;

@Service
public class CodeHistoryService extends Finder<CodeHistory,Long>{
	

	@Autowired
	private CodeHistoryJpa codeHistoryJpa;
	
	
	@Override
	public JpaSpecificationExecutor<CodeHistory> specjpa() {
		// TODO Auto-generated method stub
		return codeHistoryJpa;
	}

	@Override
	public JpaRepository<CodeHistory, Long> jpa() {
		// TODO Auto-generated method stub
		return codeHistoryJpa;
	}

	@Override
	public void addWhere(CodeHistory[] t, List<Predicate> predicates, Root<CodeHistory> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(CodeHistory t) {
		// TODO Auto-generated method stub
		
	}

	
	
}
