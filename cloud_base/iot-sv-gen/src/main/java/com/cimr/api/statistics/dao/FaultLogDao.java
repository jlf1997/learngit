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
			List<Map<String,Object>> l = finder.findAll(query, DbNameSetting.getFaultLogName(yearBase-i+""));
			list1.addAll(l);
		}
		return list1;
//		return finder.findAll(query, "demo");
	}
	
	
	/**
	 * 获取未完结数据
	 * @param list1
	 * @return
	 */
	public Map<String,Map<String,FaultLog>> getPlcMap(List<Map<String,Object>> list1){
		Map<String,Map<String,FaultLog>>  rs = new HashMap<>();
		 for(Map<String,Object> masp :list1) {
			 String terId = masp.get("terId").toString();
			 String code = masp.get("code").toString();
			 Map<String,FaultLog> terMap = rs.get(terId);
			 if(terMap==null) {
				 terMap = new HashMap<>();
			 }
			FaultLog log = terMap.get(code);
			if(log==null) {
				log = new FaultLog(masp);
				log.setYMD(log.getbTime());
			}			 
			terMap.put(code, log);
			rs.put(terId, terMap);
		 }
		 return rs;
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
	 * 分页查询 跨年度表 可能出现记录在上下2页重复出现
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime 错误发生查询条件的开始时间
	 * @param endTime 错误发生查询条件开始的结束时间
	 * @param terid 终端id
	 * @return
	 * @throws Exception
	 */
	public PageModel<Map<String,Object>> findByPage(int pageNumber, int pageSize,Long bTime,Long endTime,String terId,String code,Boolean status) throws TimeTooLongException {
		MongoDbBaseFinder finder = new MongoDbBaseFinder(statisticsTemp);
		//判断开始结束时间，最大支持查询90天的数据
//		if(endTime-bTime>TimeUtil.DAY_1*90) {
//			throw new TimeTooLongException("最大支持查询90天数据");
//		}
		Sort sort = new Sort(Sort.Direction.ASC, "status")
				.and(new Sort(Sort.Direction.DESC,"bTime"));
		Query query = new Query();
		query.with(sort);
		queryByLogTime(query,bTime,endTime);
		queryByTerId(query,terId);
		queryByStatus(query,status);
		queryByCode(query,code);
		PageModel<Map<String,Object>> page1 =null;
		
		PageModel<Map<String,Object>> page2 = null;
		String preYear = DbNameSetting.getFaultLogName(TimeUtil.getYear(new Date(bTime)));
		String nextYear = DbNameSetting.getFaultLogName(TimeUtil.getYear(new Date(endTime)));
		//当前页的查询 间隔跨年
		long totalSize = finder.getTotalNum(query, preYear);
		int pageCount = PageModel.getTotlaPage(totalSize, pageSize);
		if(TimeUtil.getYearSpan(new Date(bTime), new Date(endTime))!=0 ) {
			
			if(pageCount>pageNumber+1) {//完全来自上一页
				page1 = new PageModel<>(finder.findPage(query, preYear, sort, pageNumber, pageSize));
				long totalSizePage2 = finder.getTotalNum(query, nextYear);
				int pageCountPage2 = PageModel.getTotlaPage(totalSizePage2,pageSize);
				page1.setTotalPage(page1.getTotalPage()+pageCountPage2);
				page1.setTotalCount(page1.getTotalCount()+totalSizePage2);
			}
			if(pageCount==pageNumber+1) {//当前页查询需要跨表
				page1 = new PageModel<>(finder.findPage(query, preYear, sort, pageNumber, pageSize));
				if(pageSize>page1.getContent().size()) {//需要跨表 且需要从下一个表补齐数据
					page2 = new PageModel<>(finder.findPage(query,
							nextYear, sort
							, pageNumber-pageCount+1, pageSize-page1.getContent().size()));					
				}
			}
			if(pageCount<pageNumber+1){//数据完全来自下一个表
				page2 = new PageModel<>(finder.findPage(query,
						nextYear, sort
						, pageNumber-pageCount, pageSize));
				page2.setTotalPage(page2.getTotalPage()+pageCount);
				page2.setTotalCount(page2.getTotalCount()+totalSize);
			}
		}else {
			page1 = new PageModel<>(finder.findPage(query, preYear, sort, pageNumber, pageSize));
		}
		PageModel<Map<String,Object>> res=  PageModel.addNewPageModel(pageSize,page1,page2);
		return res;
		
	}
	
	/**
	 * 按year分库保存
	 * @param list
	 */
	public void saveByYear(List<FaultLog> list) {
		Map<String,List<FaultLog>> yearMap = new HashMap<>();
		List<FaultLog> faultList;
		String time;
		for(FaultLog faultLog:list) {
			time = TimeUtil.getYear(faultLog.getbTime());
		
			faultList = yearMap.get(time);
			if(faultList==null) {
				faultList = new ArrayList<>();
			}
			if(faultLog.getId()==null) {
				faultLog.setCreTime(new Date());
			}
			faultLog.setUpdTime(new Date());
			faultList.add(faultLog);
			yearMap.put(time, faultList);
		}
		Iterator<String> iterator = yearMap.keySet().iterator();
		while(iterator.hasNext()) {
			time = iterator.next();
			statisticsTemp.insert(yearMap.get(time), DbNameSetting.getFaultLogName(time));
		}
		
	}
	
	/**
	 * 保存或者更新
	 * @param list
	 */
	public void updateYear(List<FaultLog> list) {
		Map<String,List<FaultLog>> yearMap = new HashMap<>();
		List<FaultLog> faultList;
		String year;
		for(FaultLog faultLog:list) {
			year = TimeUtil.getYear(faultLog.getbTime());
			faultList = yearMap.get(year);
			if(faultList==null) {
				faultList = new ArrayList<>();
			}
			if(faultLog.getId()==null) {
				faultLog.setCreTime(new Date());
			}
			faultLog.setUpdTime(new Date());
			faultList.add(faultLog);
			yearMap.put(year, faultList);
		}
		Iterator<String> iterator = yearMap.keySet().iterator();
		while(iterator.hasNext()) {
			year = iterator.next();
			List<FaultLog> listFin = yearMap.get(year);
			removeAll(listFin,DbNameSetting.getFaultLogName(year));
			statisticsTemp.insert(listFin, DbNameSetting.getFaultLogName(year));
		}
	}
	
	public void removeAll(List<FaultLog> list,String collection) {
		for(FaultLog faultLog:list) {
			statisticsTemp.remove(faultLog, collection);
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
		return finder.findAll(query, DbNameSetting.getFaultLogName(TimeUtil.getYear(new Date(bTime))));
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
		return finder.getCount(query, DbNameSetting.getFaultLogName(TimeUtil.getYear(bTime)));
	}
}
