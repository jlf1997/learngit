package com.cimr.api.statistics.dao;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.boot.mongodb.MongoDbBaseFinder;

@Repository
public class StaticsticsLogDao {
	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	/***
	 * 获取最后的错误统计时间
	 * @return
	 */
	public Date getDate(String type) {
		Query query = new Query();
		Criteria criteria = Criteria.where("type").is(type);
		query.addCriteria(criteria);
		StaticsticsLog log = statisticsTemp.findOne(query, StaticsticsLog.class, StaticsticsLog.getDbName());
		if(log!=null) {
			return log.getsDate();
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
		Update update ;
		if(endDate==null) {
			 update = Update.update("updTime",new Date());
		}else {
			 update = Update.update("sDate",endDate).set("updTime", new Date());
		}
		
		statisticsTemp.upsert(query, update, StaticsticsLog.class,  StaticsticsLog.getDbName());
	}
	
	
}
