package com.cimr.api.statistics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StaticsticsLogDao {
	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	
	
	public void save(List<Object> list,String collectionName) {
		
		statisticsTemp.insert(list, collectionName);
	}
	
	
}
