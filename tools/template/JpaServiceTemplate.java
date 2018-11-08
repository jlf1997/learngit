package com.readboy.ssm.appnsh.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import import com.${templateInfo.companyName}.ssm.${templateInfo.projectName}.jpa.${tableModel.domainName}Jpa;
import com.${templateInfo.companyName}.ssm.${templateInfo.projectName}.model.${tableModel.domainName};
import com.readboy.ssm.appnsh.util.Finder;

@Service
public class ${tableModel.domainName}Service extends Finder<${tableModel.domainName}, ${tableModel.pkClass}>{
	
	
	@Autowired
	private ${tableModel.domainName}Jpa jpa;

	@Override
	public JpaSpecificationExecutor<${tableModel.domainName}> specjpa() {
		// TODO Auto-generated method stub
		return jpa;
	}

	@Override
	public JpaRepository<${tableModel.domainName}, ${tableModel.pkClass}> jpa() {
		// TODO Auto-generated method stub
		return jpa;
	}

	@Override
	public void addWhere(${tableModel.domainName}[] t, List<Predicate> predicates, Root<${tableModel.domainName}> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(${tableModel.domainName} t) {
		// TODO Auto-generated method stub
		
	}

	
	
}
