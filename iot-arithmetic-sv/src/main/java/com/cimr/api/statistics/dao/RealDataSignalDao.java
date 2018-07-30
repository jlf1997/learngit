package com.cimr.api.statistics.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cimr.api.comm.configuration.Setting;
import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.boot.mongodb.MongoDbBaseFinder;

@Repository
public class RealDataSignalDao {

	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	@Autowired
	private Setting setting;
	
	public void save(String signal,List<Object> realDate) {
		
		statisticsTemp.insert(realDate, setting.getRealDateStatisticsDbName(signal));
	}
	
	public PageModel<Map<String,Object>> findByPageAgg(String signal,int pageNumber, int pageSize,Long bTime,Long endTime,String terid,AggregationOperation... aggregations){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Sort sort = new Sort(Sort.Direction.ASC, "bTime");
		List<Criteria> criterias = new ArrayList<>();
		if(terid!=null) {
			Criteria criteria = Criteria.where("terminalNo").is(terid);
			criterias.add(criteria);
		}
		if(bTime!=null && endTime!=null) {
			Criteria criteria = Criteria.where("bTime").gte(new Date(bTime)).lte(new Date(endTime));
			criterias.add(criteria);
		}
		
		
		PageModel<Map<String,Object>>	res = finder.findByPage(criterias, setting.getRealDateStatisticsDbName(signal), sort, 0,pageNumber, pageSize,aggregations);
		
		return res;
	}
	
	public List<Map<String,Object>> findAllAgg(String signal,Long bTime,Long endTime,String terid,AggregationOperation... aggregations){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Sort sort = new Sort(Sort.Direction.ASC, "bTime");
		List<Criteria> criterias = new ArrayList<>();

		if(terid!=null) {
			Criteria criteria = Criteria.where("terminalNo").is(terid);
			criterias.add(criteria);
		}
		if(bTime!=null && endTime!=null) {
			Criteria criteria = Criteria.where("bTime").gte(new Date(bTime)).lte(new Date(endTime));
			criterias.add(criteria);
		}
		
		
		return finder.findAll(criterias, setting.getRealDateStatisticsDbName(signal),sort,aggregations);
		
	}
	
	public List<Map<String,Object>> findAll(String signal,Long bTime,Long endTime,String terid){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Sort sort = new Sort(Sort.Direction.ASC, "bTime");
		Query query = new Query();
		query.with(sort);
		if(terid!=null) {
			Criteria criteria = Criteria.where("terminalNo").is(terid);
			query.addCriteria(criteria);
		}
		if(bTime!=null && endTime!=null) {
			Criteria criteria = Criteria.where("bTime").gte(new Date(bTime)).lte(new Date(endTime));
			query.addCriteria(criteria);
		}
		
		
		return finder.findAll(query, setting.getRealDateStatisticsDbName(signal));
		
	}

}
