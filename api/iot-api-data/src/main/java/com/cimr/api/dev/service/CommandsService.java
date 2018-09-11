package com.cimr.api.dev.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.api.dev.jpa.mgr.CommandsJpa;
import com.cimr.api.dev.model.mgr.Commands;
import com.cimr.boot.jpafinder.Finder;

@Service
public class CommandsService   extends Finder<Commands,Long>{

	
	

	
	@Resource
	private CommandsJpa commandsJpa;
	

	

	
	private static final Logger log = LoggerFactory.getLogger(CommandsService.class);

	
	
	/**
	 * 根据指令id获取指令内容 指令内容必须可查
	 * @param id
	 * @return
	 */
	public Commands getCommandsByNumber(String number) {
		Commands t = new Commands();
		t.setNumber(number);
//		t.setId(id);
//		t.setAppLicense("0");
		Commands res = find(t);
//		if(res!=null) {
//			return res.getContents();
//		}		
		return res;
		
	}



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
