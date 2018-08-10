package com.cimr.api.statistics.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.config.DbNameSetting;
import com.cimr.boot.mongodb.MongoDbBaseFinder;

@Repository
public class RealDataSignalDao {

	@Autowired
	@Qualifier(value="statistics")
	protected MongoTemplate statisticsTemp;
	
	public void save(String signal,List<Object> realDate) {
		
		statisticsTemp.insert(realDate,
				DbNameSetting.getRealDateStatisticsDbName(signal));
	}
	
	private void findByTime(String signal,Long bTime,Long endTime,List<Criteria> criterias) {
		if(bTime!=null && endTime!=null) {
			Criteria criteria = Criteria.where("logTime").gte(new Date(bTime)).lte(new Date(endTime));
			criterias.add(criteria);
		}
	}
	/**
	 *查询终端id
	 */
	private void findByTerminal(String signal,String terid,List<Criteria> criterias) {
		if(terid!=null) {
			Criteria criteria = Criteria.where("terId").is(terid);
			criterias.add(criteria);
		}
	}
	
	/**
	 * 根据终端id 以及 时间查询
	 * @param signal
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @param aggregations
	 * @return
	 */
	public PageModel<Map<String,Object>> findByTerAndTimeWithPage(String signal,int pageNumber, int pageSize,Long bTime,Long endTime,String terid,List<AggregationOperation> aggregations){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		if(aggregations==null) {
			aggregations = new ArrayList<>();
		}
		
		List<Criteria> criterias = new ArrayList<>();		
		findByTime(signal,bTime,endTime,criterias);
		findByTerminal(signal,terid,criterias);
		
		
		PageModel<Map<String,Object>>	res = finder.findByAgg(0,pageNumber,pageSize,criterias, 
				DbNameSetting.getRealDateStatisticsDbName(signal),aggregations);
		
		return res;
	}
	
	/**
	 * 
	 * @param signal
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @param aggregations
	 * @return
	 */
	public List<Map<String,Object>> findByTerAndTimeWithPage(String signal,Long bTime,Long endTime,String terid,List<AggregationOperation> aggregations){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		
		List<Criteria> criterias = new ArrayList<>();
		findByTime(signal,bTime,endTime,criterias);
		findByTerminal(signal,terid,criterias);
		if(aggregations==null) {
			aggregations = new ArrayList<>();
		}
		return finder.findByAgg(criterias, 
				DbNameSetting.getRealDateStatisticsDbName(signal),aggregations);
		
	}

	public List<Map<String, Object>> findAll(String signal,List<String> terIds, Date bTime, Date eTime,
			List<AggregationOperation> aggregations) {
		// TODO Auto-generated method stub
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
		return finder.findByAgg(criterias,DbNameSetting.getRealDateStatisticsDbName(signal) ,aggregations);
	}
	
	

}
