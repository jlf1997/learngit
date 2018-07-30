package com.cimr.api.log.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.boot.mongodb.MongoDbBaseFinder;

@Repository
public class TelLogDao {

	
	@Autowired
	private ProjectPropertities projectPropertities;
	
	
	@Autowired
	@Qualifier(value="log")
	protected MongoTemplate logTemp;
	
	private String getDbName(String year) {
		return "TEL_FAULT_"+year;
	}
	
	/**
	 * 通过信号id 以及 终端编号查询对应历史记录
	 * @param singal
	 * @param terid
	 * @return
	 */
	public List<Map<String,Object>> findAllByDay(String year,Date faultStartTime,Date faultEndTime){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(logTemp);
		
		Query query = new Query();
		query.with(new Sort(new Order(Direction.ASC,"faultTime")));
		if(faultStartTime!=null && faultEndTime!=null) {
			Criteria criteria = Criteria.where("faultTime").gte(faultStartTime);
			criteria.lte(faultEndTime);
			query.addCriteria(criteria);
		}
		Criteria criteriaPj = Criteria.where("projectNo").is(projectPropertities.getProjectId());
		query.addCriteria(criteriaPj);
		return finder.findAll(query,getDbName(year));
	}
	
	
}
