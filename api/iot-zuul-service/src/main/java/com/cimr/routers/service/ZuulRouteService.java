package com.cimr.routers.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.cimr.boot.jpafinder.Finder;
import com.cimr.routers.jpa.ZuulRouteJpa;
import com.cimr.routers.model.ZuulRoute;

@Service
public class ZuulRouteService extends Finder<ZuulRoute, String>{
	
	@Autowired
	private ZuulRouteJpa zuulRouteJpa;

	@Override
	public JpaSpecificationExecutor<ZuulRoute> specjpa() {
		// TODO Auto-generated method stub
		return zuulRouteJpa;
	}

	@Override
	public JpaRepository<ZuulRoute, String> jpa() {
		// TODO Auto-generated method stub
		return zuulRouteJpa;
	}

	@Override
	public void addWhere(ZuulRoute[] t, List<Predicate> predicates, Root<ZuulRoute> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelect(ZuulRoute t) {
		// TODO Auto-generated method stub
		
	}
	

}
