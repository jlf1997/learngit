package com.cimr.api.history.dao;

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
import com.cimr.api.comm.configuration.SignalSetting;
import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.boot.mongodb.MongoDbBaseFinder;
import com.cimr.boot.utils.TimeUtil;


@Repository
public class RealDataSignalHistoryDao {
	@Autowired
	@Qualifier(value="history")
	protected MongoTemplate mongoTemplate;
	@Autowired
	private ProjectPropertities projectPropertities;
	
	@Autowired
	private SignalSetting setting;
	
	

	
//	private String getDbName(String signal,Date date) {
//		return "REALDATA_SIGNAL_"+projectPropertities.getProjectId()+"_"+signal+"_"+TimeUtil.getYearAndMonth(date);
//	}

	
	
	
	/**
	 * 获取时间范围内所有数据
	 * @param bTime
	 * @param eTime
	 * @param singal
	 * @return
	 */
	public List<Map<String,Object>> findAll(Date bTime,Date eTime,String singal){
		String gatherMsgTime = setting.getGatherMsgTime(singal);
		MongoDbBaseFinder finder = new MongoDbBaseFinder(mongoTemplate);
		Query query = new Query();
//		query.with(new Sort(new Order(Direction.ASC,gatherMsgTime)));
		Criteria criteria =null;
		if( eTime!=null) {
			criteria = Criteria.where(gatherMsgTime).lte(eTime);
		}
		if(bTime!=null) {
			if(criteria==null) {
				criteria = Criteria.where(gatherMsgTime);
			}
			criteria = criteria.gte(bTime);
		}
		if(criteria!=null) {
			query.addCriteria(criteria);
		}
		return finder.findAll(query,
				DbNameSetting.getRealDateSignalDbName(singal,bTime));
	}
	
	/**
	 * 查询总数
	 * @param bTime
	 * @param eTime
	 * @param singal
	 * @return
	 */
	public Long getCount(Date bTime,Date eTime,String singal) {
		String gatherMsgTime = setting.getGatherMsgTime(singal);
		MongoDbBaseFinder finder = new MongoDbBaseFinder(mongoTemplate);
		Query query = new Query();
//		query.with(new Sort(new Order(Direction.ASC,gatherMsgTime)));
		Criteria criteria =null;
		if( eTime!=null) {
			criteria = Criteria.where(gatherMsgTime).lte(eTime);
		}
		if(bTime!=null) {
			if(criteria==null) {
				criteria = Criteria.where(gatherMsgTime);
			}
			criteria = criteria.gte(bTime);
		}
		if(criteria!=null) {
			query.addCriteria(criteria);
		}
		return finder.getCount(query,
				DbNameSetting.getRealDateSignalDbName(singal,bTime));
	}
	
	
}
