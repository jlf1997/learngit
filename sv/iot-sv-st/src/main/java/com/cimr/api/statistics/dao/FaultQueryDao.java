package com.cimr.api.statistics.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.boot.mongodb.MongoDbBaseFinder;

/**
 * 未来移动到查询服务 ：提供查询服务
 * @author Administrator
 *
 */
@Repository
public class FaultQueryDao {

	
	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	
	private static final Logger log = LoggerFactory.getLogger(FaultQueryDao.class);
	
	private void findByTime(Long bTime,Long endTime,List<Criteria> criterias) {
		if(bTime!=null && endTime!=null) {
			Criteria criteria = Criteria.where("logTime").gte(new Date(bTime)).lte(new Date(endTime));
			criterias.add(criteria);
		}
	}

	/**
	 * 条件查询时间范围内的预警信息
	 * @param terIds
	 * @param bTime
	 * @param eTime
	 * @return
	 */
	public List<Map<String,Object>> findAll(List<String> terIds,Date bTime,Date eTime,List<AggregationOperation> aggregations){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		List<Criteria> criterias = new ArrayList<>();
		if(bTime!=null && eTime!=null) {
			Criteria criteria = Criteria.where("logTime").gte(bTime).lte(eTime);
			criterias.add(criteria);
		}
		if(terIds!=null && terIds.size()>0) {
			Criteria criteria = Criteria.where("terId").in(terIds);
			criterias.add(criteria);
		}
		if(aggregations==null) {
			aggregations = new ArrayList<>();
		}
		return finder.findByAgg(criterias,DbNameSetting.getFaultStatic() ,aggregations);
	}
	
//	private String getDbName() {
//		return DbNameSetting.getFaultStatic();
//	}

}
