package com.cimr.boot.jpafinder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.boot.model.BaseModel;
import com.cimr.boot.utils.SpringDataJpaUtils;



public abstract class FindBase<T extends BaseModel,ID extends Serializable>  {
	
	/**
	 * JpaSpecificationExecutor对象
	 */
	public abstract JpaSpecificationExecutor<T> specjpa();
	/**
	 * JpaRepository 对象
	 */
	public abstract JpaRepository<T,ID> jpa();
	
	/**
	 * 追加查询条件
	 */
	public abstract void addWhere(T[] t,List<Predicate>  predicates,Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
	

	/**
	 * 对查询结果进行处理
	 * @param page
	 */
	public abstract void setSelect(T t);
	
	
	/**
	 * 处理查询出的对象
	 * @param list
	 */
	public  void setSelect(List<T> list) {
		list.forEach(arg0->{
			setSelect(arg0);
		});
	}
	
	public  void setSelect(Page<T> page) {
		setSelect(page.getContent());
	}
	
	/**
	 * 自定义查询条件
	 */
	
	
	
	////////////////////findAll/////////////////////////////


	/**
	 * 条件查询所有
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(T... t) {
		return findAll((SpringDataJpaFinder<T>)null,(Sort)null,t);
	}
	
	/**
	 * 条件查询所有
	 * @param sdjFinder
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(SpringDataJpaFinder<T> sdjFinder,T...t) {
		return findAll(sdjFinder,(Sort)null,t);
	}
	
	/**
	 * 条件查询所有
	 * @param sortStr
	 * @param direction
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String sortStr,
			Direction direction,T...t) {			
		return findAll((SpringDataJpaFinder<T>) null,sortStr,direction,t);
	}
	
	/**
	 * 条件查询所有
	 * @param sdjFinder
	 * @param sortStr
	 * @param direction
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(SpringDataJpaFinder<T> sdjFinder,String sortStr,
			Direction direction,T...t) {
		Sort sort = null;
		if(sortStr!=null && direction!=null) {
			String[] sorStrArray = sortStr.split(",");
			sort = new Sort(direction, sorStrArray);
		}		
		return findAll(sdjFinder,sort,t);
		
	}
	/**
	 * 条件查询所有属性
	 * @param t
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(SpringDataJpaFinder<T> sdjFinder,Sort sort,T... t){		
		List<T> list =  specjpa().findAll(getSpecification(sdjFinder,t),sort);
		setSelect(list);
		return list;
	}
	
	////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 分页条件查询
	 * @param sdjFinder
	 * @param pageSize
	 * @param pageIndex
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAllPage(SpringDataJpaFinder<T> sdjFinder, Integer pageSize, Integer pageIndex,T...t) {		
		return findAllPage((SpringDataJpaFinder<T>)null,(String)null,(Direction)null,pageSize,pageIndex,t);
	}
	
	
	/**
	 * 分页条件查询
	 * @param sdjFinder
	 * @param sortStr
	 * @param direction
	 * @param pageSize
	 * @param pageIndex
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAllPage(SpringDataJpaFinder<T> sdjFinder,String sortStr,
			Direction direction, Integer pageSize, Integer pageIndex,T...t) {
		Sort sort = null;
		if(sortStr!=null && direction!=null) {
			String[] sorStrArray = sortStr.split(",");
			 sort = new Sort(direction, sorStrArray);
		}
		PageRequest pageRequest = new PageRequest(pageIndex,pageSize,sort);			
		return findAllPage(sdjFinder,pageRequest,t);
	}
	
	/**
	 * 分页条件查询
	 * @param sortStr
	 * @param direction
	 * @param pageSize
	 * @param pageIndex
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")	
	public Page<T> findAllPage(String sortStr,
			Direction direction, Integer pageSize, Integer pageIndex,T...t) {				
		return findAllPage((SpringDataJpaFinder<T>)null,sortStr,direction,pageSize,pageIndex,t);
	}	
	
	/**
	 * 分页条件查询
	 * @param pageRequest
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAllPage(PageRequest pageRequest,T...t) {		
		return findAllPage((SpringDataJpaFinder<T>)null,pageRequest,t);
	}
	
	/**
	 * 分页条件查询
	 * @param sdjFinder
	 * @param pageRequest
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findAllPage(SpringDataJpaFinder<T> sdjFinder, PageRequest pageRequest,T...t) {
		Page<T> page =  specjpa().findAll(getSpecification(sdjFinder,t),pageRequest);	
		setSelect(page);
		return page;
		
	}
	
	
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T find(T... t) {
		return find((SpringDataJpaFinder<T>)null ,t);
	}
	
	/***
	 * 
	 * @param sdjFinder
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T find(SpringDataJpaFinder<T> sdjFinder,T... t) {
		List<T> list = specjpa().findAll(getSpecification(sdjFinder,t));
		if(list==null || list.size()!=1) {
			return null;
		}else {
			setSelect(list);
			return list.get(0);
		}
	}	
	
	
	/**
	 * 保存或更新：根据是否有主键自动判断
	 * @param t
	 * @return
	 */
//	@Transactional
	public T save(T t) {
		try {		
			return jpa().save(t);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 
	}
	
	/**
	 * 批量保存或更新，更新失败返回null
	 * @param entities
	 * @return
	 */
	@Transactional
	public List<T> save(Iterable<T> entities) {
		try {
			return jpa().save(entities);
		}catch (Exception e) {
			return null;
		}
		 
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T findOne(ID id) {
		T t =  jpa().findOne(id);
 		if(t!=null && (t.getDeleted()==null  || 1!=t.getDeleted()) ) {
			setSelect(t);
			return t;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 删除
	 * @param t
	 * @return
	 */
	@Transactional
	public List<T> delete(T t) {
		List<T> list = findAll(t);
		list.stream().forEach(action->{
			action.setDeleted(1);
			action.setDelTime(new Date().getTime());
		});		
		return jpa().save(list);
	}
	
	/**
	 * 更新对应字段，val为空则更新所有字段
	 * @param t 需要更新的值
	 * @param id 主键id
	 * @param val 需要更新的值
	 * @return
	 */
	@Transactional
	public T updateIncludeValue(T t,ID id,String... val) {
		
			if(val==null) {
				return jpa().save(t);
			}else {			
				T db = findOne(id);
				if(db==null) {
					return null;
				}
				setDbValue(t,db,t.getClass(),val);
				return save(db);
			}	
	}
	
	/**
	 * 递归获取父类属性值，并比较其中是否存在需要更新字段，有则更新对应字段值
	 * @param t
	 * @param db
	 * @param classZ
	 * @param val
	 */
	private void setDbValue(T t,T db,Class<?> classZ,String... val) {
		Field[] fields = classZ.getDeclaredFields();
		setValue(t,db,fields,t.getClass(),val);
		Class<?> sup = classZ.getSuperclass();
		if(sup.equals(Object.class)) {
			System.out.println("out");
		}else {
			setDbValue(t,db,sup,val);
		}
	}
		
	
	/**
	 * 设置属性 值
	 * @param t
	 * @param db
	 * @param fields
	 * @param classZ
	 * @param val
	 */
	private void setValue(T t,T db,Field[] fields,Class<?> classZ,String... val) {
		 Field.setAccessible(fields,   true); 
		for(Field field: fields) {
			String name = field.getName();
			if(nameInStrs(name,val)) {
				Object obj = getFieldValueByName(name,t,classZ);
//				if(obj!=null)
				{
					try {
						field.set(db, obj);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	
	/**
	 * 保存所有非空的值，null值不更新
	 * @param t
	 * @param db
	 * @param fields
	 * @param classZ
	 */
	@SuppressWarnings("unused")
	private void setValue(T t,T db,Field[] fields,Class<?> classZ) {
		 Field.setAccessible(fields,   true); 
		for(Field field: fields) {
			String name = field.getName();		 
			Object obj = getFieldValueByName(name,t,classZ);
			if(obj!=null){
				try {
					field.set(db, obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}					
		}	
	}
	
	/**
	 * 判断属性值是否在数组中
	 * @param name
	 * @param val
	 * @return
	 */
	private boolean nameInStrs(String name ,String... val) {
		if(name==null||val==null) {
			return false;
		}
		for(String str:val) {
			if(name.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取属性名称对应的值，通过get方法
	 * @param fieldName
	 * @param t
	 * @param classZ
	 * @return
	 */
	  private Object getFieldValueByName(String fieldName, T t,Class<?> classZ) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = classZ.getMethod(getter, new Class[] {});    
	           Object value = method.invoke(t, new Object[] {});    
	           return value;    
	       } catch (Exception e) {   
	          e.printStackTrace();
	           return null;    
	       }    
	   }  
	  
	/**
	 * 构造查询条件
	 * @param t
	 * @param cretTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	 private  Specification<T> getSpecification(SpringDataJpaFinder<T> sdjFinder,T... t){
			return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate>  predicates = getPredicates(root, query, cb,t);
				boolean isGroup = false;
				if(t!=null) {
					if(sdjFinder!=null) {
						isGroup = sdjFinder.where(predicates,root, query, cb,t);
					}else {
						SpringDataJpaUtils.where(predicates,root,query,cb,t);
					}
				}
				//追加查询
				addWhere(t,predicates,root,query,cb);

				 query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));  
				 if(isGroup) {
					 return query.getGroupRestriction();
				 }
				 return query.getRestriction(); 
//				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	/**
	 * 默认查询条件
	 * @param root
	 * @param query
	 * @param cb
	 * @param createTimeEntity
	 * @param updTimeEntity
	 * @return
	 */
	private List<Predicate> getPredicates(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,T...t) {
		List<Predicate>  predicates = new ArrayList<>();		
		if(t!=null) {
			if(t.length>0 && t[0]!=null) {
				Date creTimeBegin = t[0].getCreTime();
				Date updTimeBegin = t[0].getUpdTime();
				if(creTimeBegin!=null) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("creTime"), creTimeBegin));
				}
				if(updTimeBegin!=null) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("updTime"), updTimeBegin));
				}					
			}
			if(t.length>1 && t[1]!=null) {
				Date creTimeEnd = t[1].getCreTime();
				Date updTimeEnd = t[1].getUpdTime();
				if(creTimeEnd!=null) {
					predicates.add(cb.lessThanOrEqualTo(root.get("creTime"), creTimeEnd));
				}
				if(updTimeEnd!=null) {
					predicates.add(cb.lessThanOrEqualTo(root.get("updTime"), updTimeEnd));
				}
			}
			//删除标识
			
		}			
		if(t!=null && t.length>0 && new Integer("-1").equals(t[0].getDeleted())) {
			
		}else {
			predicates.add(cb.or(cb.equal(root.get("deleted"), 0),cb.isNull(root.get("deleted"))));	
		}
			
		return predicates;
		}
	
	
	
}
