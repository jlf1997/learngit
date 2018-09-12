package com.cimr.api.statistics.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.api.statistics.model.StatisticsDailyLog;
import com.cimr.boot.utils.TimeUtil;

@Repository
public class StatisticsDailyLogDao {

	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	/***
	 * 获取某天是否统计
	 * @return
	 */
	public StatisticsDailyLog getDate(String type,Date sDate) {
		Query query = new Query();
		Criteria criteria = Criteria.where("type").is(type);
		query.addCriteria(criteria);
		Date b = TimeUtil.getStartTime(sDate);
		Date e = TimeUtil.getEndTime(sDate);
		Criteria criteriaDate = Criteria.where("sDate").gte(b).lte(e);
		query.addCriteria(criteriaDate);
		StatisticsDailyLog log = statisticsTemp.findOne(query, StatisticsDailyLog.class, 
				DbNameSetting.getStatisticsDailyLogName());
		return log;
	}
	
	/**
	 * 查询最新的更新日期
	 * @param type
	 * @return
	 */
	public StatisticsDailyLog getDate(String type) {
		Query query = new Query();
		Sort sort = new Sort(Sort.Direction.ASC,"sDate");
		query.with(sort);
		Criteria criteria = Criteria.where("type").is(type);
		query.addCriteria(criteria);
		List<StatisticsDailyLog> logs = statisticsTemp.find(query, StatisticsDailyLog.class,
				DbNameSetting.getStatisticsDailyLogName());
		if(logs!=null && logs.size()>0) {
			return logs.get(0);
		}
		return null;
	}
	
	/**
	 * 更新日期
	 * @param date
	 */
	public void updateDate(String type,Date endDate) {
		Query query = new Query();
		Criteria criteria = Criteria.where("type").is(type);
		query.addCriteria(criteria);
		Update update = Update.update("updTime", new Date());
		update.set("sDate", endDate);
		update.setOnInsert("creTime", new Date());
		statisticsTemp.upsert(query, update, DbNameSetting.getStatisticsDailyLogName());
	}
	
	
}
