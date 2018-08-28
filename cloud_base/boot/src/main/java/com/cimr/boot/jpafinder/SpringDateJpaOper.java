package com.cimr.boot.jpafinder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * spring data jpa 操作辅助类
 * @author yangxiang
 *
 * @param <T>
 */
public class SpringDateJpaOper<T> {
	private Root<T> root;
	private CriteriaQuery<?> query;
	private CriteriaBuilder cb;
	
	
	/**
	 * 构造函数
	 * @param root
	 * @param query
	 * @param cb
	 */
	public SpringDateJpaOper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb){
		this.cb = cb;
		this.query = query;
		this.root = root;
	}
	
	/**
	 * 等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	public  void eq(List<Predicate>  predicates,String attributeName,Object value) {
		Predicate predicate =  cb.equal(root.get(attributeName),  value);
		addPredicate(predicates,predicate);
	}
	
	public  Predicate eq(String attributeName,Object value) {
		Predicate predicate =  cb.equal(root.get(attributeName),  value);
		return predicate;
	}
	
	
	/**
	 * 不等于
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public void notEqual(List<Predicate>  predicates,String attributeName, Object value) {
		// TODO Auto-generated method stub
		Predicate predicate =  cb.notEqual(root.get(attributeName),  value);
		addPredicate(predicates,predicate);
	}


	/**
	 * 模糊查询
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	public void like(List<Predicate>  predicates,String attributeName, Object value) {
		if(value instanceof String) {
			String str = (String)value;
			Predicate predicate =  cb.like(root.get(attributeName)
					,cb.literal("%"+str+"%"));
			addPredicate(predicates,predicate);
		}		
	}

	/**
	 * 大于等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void ge(List<Predicate>  predicates,String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			Predicate predicate = cb.greaterThanOrEqualTo(root.get(attributeName), comparable);
			addPredicate(predicates,predicate);
		}		
		
	}

	/**
	 * 小于等于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void le(List<Predicate>  predicates,String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			Predicate predicate = cb.lessThanOrEqualTo(root.get(attributeName), comparable);
			addPredicate(predicates,predicate);
		}			
	}

	/**
	 * 大于
	 * @param attributeName 对应实体类中字段
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void gt(List<Predicate>  predicates,String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			Predicate predicate = cb.greaterThan(root.get(attributeName), comparable);
			addPredicate(predicates,predicate);
		}		
	}

	/**
	 * 小于
	 * @param attributeName
	 * @param value 比较值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void lt(List<Predicate>  predicates,String attributeName, Object value) {
		if(value instanceof Comparable) {
			Comparable comparable = (Comparable) value;
			Predicate predicate = cb.lessThan(root.get(attributeName), comparable);
			addPredicate(predicates,predicate);
		}		
	}


	/**
	 * 按位与	    
	 * 二进制位 代表状态 存在某一个
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public void bitExistAny(List<Predicate>  predicates,String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			Predicate predicate = cb.gt(ex,  cb.literal(0));
			addPredicate(predicates,predicate);
		}		
	}
	
	
	/**
	 *  使用按位与
	 *  判断 存在   4 8 均存在 可传入4+8
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public void bitExistALL(List<Predicate>  predicates,String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			Predicate predicate = cb.gt(ex,  cb.literal(0));
			addPredicate(predicates,predicate);
		}		
	}
	
	/**
	 * 使用 按位与
	 * 判断 4,8 均不存在 可传入4+8
	 * @param attributeName
	 * @param value
	 * @return
	 */
	public void bitNotExistALL(List<Predicate>  predicates,String attributeName, Object value) {
		if(isBitFunctionValut(value)) {
			Number number = (Number) value;
			Expression<Number> ex = bitAnd(attributeName,number);
			Predicate predicate = cb.notEqual(ex,  cb.literal(0));
			addPredicate(predicates,predicate);
		}	
	}
	
	
	/**
	 * 
	 * @param predicates
	 * @param name
	 * @param value
	 */
	public void between(List<Predicate> predicates, String attributeName, Object value1,Object value2) {
		// TODO Auto-generated method stub
		Comparable comparable1 = (Comparable) value1;
		Comparable comparable2 = (Comparable) value2;
		Predicate p1=cb.greaterThanOrEqualTo(root.get(attributeName), comparable1);
		Predicate p2=cb.lessThanOrEqualTo(root.get(attributeName), comparable2);
		Predicate predicate = cb.and(p1, p2);
		addPredicate(predicates,predicate);
	}
	
	public void isNull(List<Predicate> predicates, String attributeName) {
		// TODO Auto-generated method stub
		Predicate predicate = cb.isNull(root.get(attributeName));
		addPredicate(predicates,predicate);
	}
	

	
	
	/**
	 * 获取按位与 表达式
	 * @param val1 数据库中字段
	 * @param val2 运算值
	 * @return
	 */
	private Expression<Number> bitAnd(String attributeName,Number val2) {
		Expression<Number> ex = cb.function("bitand", Number.class, root.get(attributeName),cb.literal(val2));
		return ex;
	}
	
	/**
	 * 获取按位或 表达式
	 * @param val1 数据库中字段
	 * @param val2 运算值
	 * @return
	 */
	private Expression<Number> bitOr(String attributeName,Number val2) {
		Expression<Number> ex = cb.function("bitor", Number.class, root.get(attributeName),cb.literal(val2));
		return ex;
	}
	
	/**
	 * 判断对象是否可以进行位运算
	 * @param obj
	 * @return
	 */
	private boolean isBitFunctionValut(Object obj) {		
		if(obj instanceof Long ||
		   obj instanceof Integer ||
		   obj instanceof Short ||
		   obj instanceof Byte) {
			return true;
		}
		return false;
	}
	
	
	private void addPredicate(List<Predicate> predicates,Predicate predicate) {
		if(predicate!=null) {
			if(predicates==null) predicates= new ArrayList<>();
			predicates.add(predicate);
		}
	}

	

	

	
	
	
}
