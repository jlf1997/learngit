package com.cimr.api.statistics.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.cimr.api.statistics.model.FaultLog;


@Repository
public class FaultLogDao {

	
	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	private String getDbName(String year) {
		return "TEL_FAULT_"+year;
	}
	
	
	public void save(List<FaultLog> list) {
		statisticsTemp.insert(list, getDbName("2018"));
	}
}
