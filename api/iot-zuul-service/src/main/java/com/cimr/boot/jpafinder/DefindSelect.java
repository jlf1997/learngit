package com.cimr.boot.jpafinder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public  interface DefindSelect<T> {
	public void find(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,List<Predicate>  predicates ,SpringDateJpaOper<T> sdjo,T...t);
}
