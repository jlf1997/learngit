package com.cimr.api.statistics.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.mongodb.MongoDbBaseFinder;
import com.cimr.boot.utils.TimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


@Repository
public class FaultLogDao {

	
	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	/**
	 * 查询所有未结束的错误
	 * @return
	 */
	public List<Map<String,Object>> getUnfininshLog(Date pointTime,Integer falutType){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Query query = new Query();
		Criteria criteria1 = Criteria.where("endTime").exists(false);
		query.addCriteria(criteria1);
		Criteria criteria2 = Criteria.where("faultType").is(falutType);
		query.addCriteria(criteria2);
		String year = TimeUtil.getYear(pointTime);
		List<Map<String,Object>> list1 = new ArrayList<>();
		int yearBase = Integer.parseInt(year);
		for(int i=0;i<12;i++) {
			List<Map<String,Object>> l = finder.findAll(query, DbNameSetting.getFaultLogName());
			list1.addAll(l);
		}
		return list1;
//		return finder.findAll(query, "demo");
	}
	
	
	
	
	private void queryByLogTime(Query query,Long bTime,Long eTime) {
		Assert.notNull(query,"query is not null");
		if(bTime!=null && eTime!=null) {
			Criteria criteria = Criteria.where("bTime").gte(new Date(bTime)).lte(new Date(eTime));
			query.addCriteria(criteria);
		}
	}
	
	private void queryByTerId(Query query,String terId) {
		Assert.notNull(query,"query is not null");
		if(terId!=null) {
			Criteria criteria = Criteria.where("terId").is(terId);
			query.addCriteria(criteria);
		}
	}
	
	private void queryByStatus(Query query,Boolean status) {
		Assert.notNull(query,"query is not null");
		if(status!=null ) {
			Criteria criteria = Criteria.where("endTime").exists(status);
			query.addCriteria(criteria);
		}
	}
	
	private void queryByCode(Query query,String code) {
		Assert.notNull(query,"query is not null");
		if(code!=null ) {
			Criteria criteria = Criteria.where("code").is(code);
			query.addCriteria(criteria);
		}
	}
	
	


	/**
	 * 根据条件查询所有预警信息
	 * @param bTime
	 * @param endTime
	 * @param terId
	 * @param code
	 * @param status
	 * @return
	 * @throws TimeTooLongException
	 */
	public List<Map<String, Object>> findAll(Long bTime, Long endTime, String terId, String code, Boolean status) throws TimeTooLongException {
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
			if(endTime-bTime>TimeUtil.DAY_1) {
			throw new TimeTooLongException("最大支持查询1天数据");
		}
		Sort sort = new Sort(Sort.Direction.ASC, "status")
				.and(new Sort(Sort.Direction.DESC,"bTime"));
		Query query = new Query();
		query.with(sort);
		queryByLogTime(query,bTime,endTime);
		queryByTerId(query,terId);
		queryByStatus(query,status);
		queryByCode(query,code);
		return finder.findAll(query, DbNameSetting.getFaultLogName());
	}


	/**
	 * 获取错误总数
	 * @param bTime
	 * @param eTime
	 * @return
	 */
	public Long getCount(Date bTime, Date eTime) {
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Query query = new Query();
		queryByLogTime(query,bTime.getTime(),eTime.getTime());
		return finder.getCount(query, DbNameSetting.getFaultLogName());
	}



}
