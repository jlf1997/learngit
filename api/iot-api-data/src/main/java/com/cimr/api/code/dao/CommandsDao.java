package com.cimr.api.code.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.jpa.mgr.CommandsJpa;
import com.cimr.api.code.model.mgr.Commands;
import com.cimr.boot.jpafinder.Finder;

@Repository
public class CommandsDao extends Finder<Commands,Long>{
	
	@Resource
	private CommandsJpa commandsJpa;

	@Override
	public JpaSpecificationExecutor<Commands> specjpa() {
		// TODO Auto-generated method stub
		return commandsJpa;
	}

	@Override
	public JpaRepository<Commands, Long> jpa() {
		// TODO Auto-generated method stub
		return commandsJpa;
	}

	@Override
	public void addWhere(Commands[] t, List<Predicate> predicates, Root<Commands> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(Commands t) {
		// TODO Auto-generated method stub
		
	}

}
