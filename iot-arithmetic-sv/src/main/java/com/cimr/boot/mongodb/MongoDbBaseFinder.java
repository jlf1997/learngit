package com.cimr.boot.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.cimr.api.statistics.model.FaultLog;
import com.mongodb.DBObject;
import com.mongodb.MongoException;


/**
 * mongodb 查询辅助类
 * @author yangxiang
 *
 */
public class MongoDbBaseFinder {

	public MongoDbBaseFinder(MongoTemplate template) {
		this.template = template;
	}
	
	private MongoTemplate template;
	
	
	/**
	 * 条件查询指定collection数据
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findAll(Query query, String collectionName){
		List<Map<String,Object>> list = new ArrayList<>();
		template.executeQuery(query, collectionName, new DocumentCallbackHandler() {

			@Override
			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
				// TODO Auto-generated method stub
			
				Map<String,Object> map = new HashMap<>();
				map.putAll(dbObject.toMap());
				list.add(map);
			}
			 
		 });
		 return list;
	}
	
	public Page<Map<String,Object>> findPage(Query query, String collectionName,Sort sort,int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
		query.with(pageable);
		List<Map<String,Object>> result = findAll(query,collectionName);
		long total = template.count(query, collectionName);
		Page<Map<String,Object>> page = new PageImpl<>(result,pageable,total);
		return page;
	}
	
	public long getTotalNum(Query query,String collectionName) {
		return template.count(query, collectionName);
	}
	
	/**
	 * 判断当前页是否是最后一页
	 * @param query
	 * @param collectionName
	 * @param pageNumber 页号 从0开始
	 * @param pageSize
	 * @return
	 */
	public boolean isLastPage(Query query,String collectionName,int pageNumber,int pageSize) {
		long total =  getTotalNum(query,collectionName);
		if(pageSize==0) {
			return false;
		}
		if(total/pageSize<pageNumber+1) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	/**
	 * 查询指定collection的所有数据
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findAll(String collectionName){
		Query query = new Query();
		return findAll(query,collectionName);
	}
		
	
}
