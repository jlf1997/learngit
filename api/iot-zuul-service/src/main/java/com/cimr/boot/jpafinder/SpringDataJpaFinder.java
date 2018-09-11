package com.cimr.boot.jpafinder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * 自定义查询条件
 * @author Administrator
 *
 * @param <T>
 */
public interface SpringDataJpaFinder<T> {

	public boolean where(List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,T...t);
}
