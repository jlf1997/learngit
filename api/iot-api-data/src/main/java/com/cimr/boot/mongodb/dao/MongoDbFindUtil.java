package com.cimr.boot.mongodb.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.cimr.boot.mongodb.MongoDbBaseFinder;

public  class MongoDbFindUtil {

	protected MongoTemplate template;
	
	
	public MongoDbFindUtil(MongoTemplate template) {
		this.template = template;
	}
	
	/**
	 * 查询所有数据
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findAll(String collectionName){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(template);
		return finder.findAll(collectionName);
	}
	
	/**
	 * 分页查询数据
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findByPage(String collectionName){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(template);
		
		return finder.findAll(collectionName);
	}
}
