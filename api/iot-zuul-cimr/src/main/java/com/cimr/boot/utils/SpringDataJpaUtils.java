package com.cimr.boot.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.Assert;

import com.cimr.boot.jpafinder.DBFinder;
import com.cimr.boot.jpafinder.SpringDateJpaOper;
import com.cimr.boot.model.BaseModel;

public class SpringDataJpaUtils {
	
	/**
	 * 判断对象 某些字段是否有数据库中存在，高并发情形下不能通过此方法判断，建议使用联合唯一索引限制
	 * @return
	 */
	public static <T> boolean isDuplicate(T t) {
		return false;
		
	}
	
	
	public static <T> boolean isDuplicate(T t,String...strings) {
		
		
		
		return false;
		
	}
	
	/**
	 * 默认查询
	 * @param predicates
	 * @param root
	 * @param query
	 * @param cb
	 * @param t
	 */
	public static <T > void where(List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,T...t) {
		if(t!=null&&t.length>0) {	
			Class<?> classT = t[0].getClass();
			Field[] fields = classT.getDeclaredFields();
			PropertyDescriptor property = null;
			for (Field field : fields) {
				DBFinder dbOper = RefUtil.getAnnotation(field, DBFinder.class);
				if(dbOper!=null && !dbOper.added()) {
					continue;
				}
				try {
					property = new PropertyDescriptor(field.getName(), classT);
				} catch (IntrospectionException e) {					
					e.printStackTrace();
				}
				Assert.notNull(property, "property must not be null");
				Method readMethod = RefUtil.getReadMethod(property);				
				if (readMethod != null ) {					
					try {
						List<Object> values = RefUtil.getValues(readMethod, t);
						int size = values.size();
						if(values!=null && size>0) {
							SpringDateJpaOper<T> springDateJpaOper = new SpringDateJpaOper<>(root,query,cb);
							
							if(dbOper!=null && dbOper.added()) {								
								switch(dbOper.opType()) {
								case EQ:
									springDateJpaOper.eq(predicates,field.getName(), values.get(0));									
									break;
								case LIKE:	
									springDateJpaOper.like(predicates,field.getName(), values.get(0));									
									break;
								case GE :
									springDateJpaOper.ge(predicates,field.getName(), values.get(0));								
									break;
								case LE:
									springDateJpaOper.le(predicates,field.getName(), values.get(0));									
									break;
								case LT:
									springDateJpaOper.lt(predicates,field.getName(), values.get(0));
									break;
								case GT:
									springDateJpaOper.gt(predicates,field.getName(), values.get(0));
									break;
								case BIT_EXIST_ANY:
									springDateJpaOper.bitExistAny(predicates,field.getName(), values.get(0));
									break;
								case BIT_NOT_EXIST_ALL:
									springDateJpaOper.bitNotExistALL(predicates,field.getName(), values.get(0));
									break;
								case BIT_EXIST_ALL:
									springDateJpaOper.bitExistALL(predicates,field.getName(), values.get(0));
									break;
								case NOT_EQUAL:
									springDateJpaOper.notEqual(predicates,field.getName(), values.get(0));
									break;
								case BETWEEN:
									Assert.isTrue(size>1, "between操作 必须包含2个操作数");									
									springDateJpaOper.between(predicates,field.getName(), values.get(0),values.get(1));
									break;
								case IS_NULL:
									springDateJpaOper.isNull(predicates,field.getName());
									break;		
								default:
									break;								
								}								
							}else if(dbOper==null){
								predicates.add(cb.equal(root.get(field.getName()),  values.get(0)));
							}
						}
					}
					catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
	}
}
