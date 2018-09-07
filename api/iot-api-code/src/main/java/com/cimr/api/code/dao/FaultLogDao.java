package com.cimr.api.code.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cimr.api.code.config.DbNameSetting;
import com.cimr.api.code.model.warnning.FaultLog;
import com.cimr.boot.mongodb.MongoDbBaseFinder;
import com.cimr.boot.utils.BootBeanUtils;
import com.cimr.boot.utils.TimeUtil;


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
			List<Map<String,Object>> l = finder.findAll(query, DbNameSetting.getFaultLogName(yearBase-i+""));
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
	 * 保存
	 * @param list
	 */
	public void save(List<FaultLog> faultList,String proid) {
		
		statisticsTemp.insert(faultList, DbNameSetting.getFaultLogName(proid));
			
		
	}
	
	/**
	 * 保存或者更新
	 * @param list
	 */
	public void update(List<FaultLog> faultList,String proid) {
		removeAll(faultList,DbNameSetting.getFaultLogName(proid));
		statisticsTemp.insert(faultList, DbNameSetting.getFaultLogName(proid));
		
	}
	
	
	/**
	 * 条件修改
	 * @param faultList
	 * @param proid
	 */
	public void updateFinished(FaultLog fault,String proid) {
		Query query = new Query();
		Criteria criteria1 = Criteria.where("warningKey").is(fault.getWarningKey());
		query.addCriteria(criteria1);
		Criteria criteria2 = Criteria.where("bTime").lte(fault.getbTime());
		query.addCriteria(criteria2);
		Criteria criteria3 = Criteria.where("terId").is(fault.getTerId());
		query.addCriteria(criteria3);
		Criteria criteria4 = Criteria.where("removed").exists(false);
		query.addCriteria(criteria4);
	    Update update = Update.update("removed", true);
	    update.set("endTime", fault.getbTime());
	    statisticsTemp.updateMulti(query, update,  DbNameSetting.getFaultLogName(proid));
		
	}
	
	public void removeAll(List<FaultLog> faultList,String collection) {
		for(FaultLog faultLog:faultList) {
			statisticsTemp.remove(faultLog, collection);
		}
	}


	


	/**
	 * 查询FaultLog 列表
	 * @param proid 项目id
	 * @param terId 终端id
	 * @param code 预警码
	 * @return
	 */
	public List<Map<String, Object>> getFaultList(String proid, String terId, String code) {
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		Query query = new Query();
		queryByTerId(query,terId);
		queryByCode(query,code);
		//不存在endTime
		queryByStatus(query,false);
		return finder.findAll(query, DbNameSetting.getFaultLogName(proid));
	}
}
