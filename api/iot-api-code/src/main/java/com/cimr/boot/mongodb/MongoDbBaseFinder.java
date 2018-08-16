package com.cimr.boot.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

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
