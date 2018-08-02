package com.cimr.api.statistics.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cimr.api.statistics.model.StatisticsDailyLog;
import com.cimr.boot.utils.TimeUtil;

@Repository
public class StatisticsDailyLogDao {

	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	/***
	 * 获取最后的错误统计时间
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
		StatisticsDailyLog log = statisticsTemp.findOne(query, StatisticsDailyLog.class, StatisticsDailyLog.getDbName());
		return log;
	}
	
	/**
	 * 更新日期
	 * @param date
	 */
	public void updateDate(String type,Date endDate) {
		StatisticsDailyLog log = new StatisticsDailyLog();
		log.setCreTime(new Date());
		log.setUpdTime(new Date());
		log.setsDate(endDate);
		log.setType(type);
		statisticsTemp.insert(log, StatisticsDailyLog.getDbName());
	}
}
