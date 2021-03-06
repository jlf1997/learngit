package com.cimr.api.history.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cimr.api.history.config.HistoryMongoConfig;
import com.cimr.boot.mongodb.MongoDbBaseFinder;
import com.cimr.boot.utils.TimeUtil;
import com.cimr.util.MapResultUtils;


@Repository
public class RealdataSignalHistoryDao {
	/**
	 * 
	 */
	private final String COLLECTION_NAME = "REALDATA_SIGNAL";
	
	
	private final String TERMINALNO = "terminalNo";
	
	private final String CREATETIME = "gatherMsgTime";
	
	@Autowired
	@Qualifier("history")
	protected MongoTemplate histroyTemp;
	
//	/**
//	 * 通过信号id 以及 终端编号查询对应历史记录
//	 * @param singal
//	 * @param terid
//	 * @return
//	 */
//	public List<Map<String,Object>> findAllByTerId(String singal,String projectId,String terid){
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);
//		Query query = new Query();
//		if(terid!=null || "".equals(terid)) {
//			Criteria criteria = Criteria.where(TERMINALNO).regex(terid+"*");
//			query.addCriteria(criteria);
//		}
//		return finder.findAll(query,getDbName(singal,projectId));
//	}
	
//	/**
//	 * 通过信号id 开始时间 结束时间 查询对应历史记录
//	 * @param singal
//	 * @param beg
//	 * @param beg
//	 * @return
//	 */
//	public List<Map<String,Object>> findAllByTime(String singal,String projectId,Long beg,Long end){
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);
//		Query query = new Query();
//		Criteria criteria = Criteria.where(CREATETIME).gte(beg).lte(end);
//		query.addCriteria(criteria);
//		return finder.findAll(query,getDbName(singal,projectId));
//	} 
	
	
//	/**
//	 * 通过信号查询对应历史数据
//	 * @param singal
//	 * @return
//	 */
//	public List<Map<String,Object>> findAllBySingal(String singal,String projectId){
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);		
//		return finder.findAll(getDbName(singal,projectId));
//	}
	
	
//	/**
//	 * 
//	 * @param terminalId
//	 * @param beg
//	 * @param end
//	 * @return
//	 */
//	public List<Map<String,Object>> findLocationByTerminalId(String terminalId,Long beg,Long end){
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);	
//		Query query = new Query();
//		Criteria criteria = Criteria.where(CREATETIME).gte(beg).lte(end);
//		query.addCriteria(criteria);
//		//按照时间排序
//		query.with(new Sort(Sort.Direction.ASC, CREATETIME));
//		return finder.findAll(query,COLLECTION_NAME_LOCATION);
//	}
	
	/**
	 * 查询原始历史记录 最大支持查询一月数据
	 * @param singal
	 * @param projectId
	 * @param terid
	 * @param beg
	 * @param end
	 * @param sortBy
	 * @param sortType
	 * @param type
	 * @param fields
	 * @return
	 */
	public List<Map<String, Object>> findAllDataByTimeAndSingal(String singal, String projectId,String terid, Long beg, Long end,String[] sortBy,String sortType,int type,String... fields) {
		if(beg==null||beg==0L) {
			beg = TimeUtil.getStartTime(new Date()).getTime();
		}
		if(end==null || end<beg) {
			end = TimeUtil.getEndTime(new Date()).getTime();
		}
		//最大支持查询40天数据
		if(end-beg>TimeUtil.DAY_1*40) {
			return null;
		}
		//查询的数据分为2个月
		if(TimeUtil.getMonthSpan(new Date(beg), new Date(end))>=1) {
			List<Map<String, Object>> m1 = findAllDataByTimeAndSingalOneDay(singal,projectId,terid,
					beg,TimeUtil.getTheLastDayOfMonth(new Date(beg)).getTime(),sortBy,sortType,type,fields);
			List<Map<String, Object>> m2 = findAllDataByTimeAndSingalOneDay(singal,projectId,terid,
					TimeUtil.getTheFirstDayOfMonth(new Date(end)).getTime(),end,sortBy,sortType,type,fields);
			List<Map<String, Object>> res = new ArrayList<>();
			res.addAll(m1);
			res.addAll(m2);
			return res;
		}else {
			return findAllDataByTimeAndSingalOneDay(singal,projectId,terid,beg,end,sortBy,sortType,type,fields);
		}
	} 
	
	public List<Map<String, Object>> findTersAllRealDataBooleanCount(String singal, String projectId,String terid, Long beg, Long end,
			String[] sortBy, String sortType, String includeType, String[] fields, String countIncludeType,
			String[] countFields) {
		if(beg==null||beg==0L) {
			beg = TimeUtil.getStartTime(new Date()).getTime();
		}
		if(end==null || end<beg) {
			end = TimeUtil.getEndTime(new Date(beg)).getTime();
		}
		//最大支持查询40天数据
		if(end-beg>TimeUtil.DAY_1*40) {
			return null;
		}
		//查询的数据分为2个月
		if(TimeUtil.getMonthSpan(new Date(beg), new Date(end))>=1) {
			List<Map<String, Object>> m1 = findTersAllRealDataBooleanCountOneDay( singal,  projectId, terid, 
					beg,TimeUtil.getTheLastDayOfMonth(new Date(beg)).getTime(),
					sortBy, sortType, includeType, fields,  countIncludeType,countFields);
			List<Map<String, Object>> m2 = findTersAllRealDataBooleanCountOneDay( singal,  projectId, terid, 
					TimeUtil.getTheFirstDayOfMonth(new Date(end)).getTime(),end,
					sortBy, sortType, includeType, fields,  countIncludeType,countFields);
			List<Map<String, Object>> res = new ArrayList<>();
			res.addAll(m1);
			res.addAll(m2);
			return res;
		}else {
			return findTersAllRealDataBooleanCountOneDay( singal,  projectId, terid,  beg,  end,
					sortBy, sortType, includeType, fields,  countIncludeType,
					countFields);
		}
	}
	
	
	/**
	 * 查询一天的数据
	 * @param singal
	 * @param projectId
	 * @param terid
	 * @param beg
	 * @param end
	 * @param sortBy
	 * @param sortType
	 * @param type
	 * @param fields
	 * @return
	 */
	public List<Map<String, Object>> findAllDataByTimeAndSingalOneDay(String singal, String projectId,String terid, Long beg, Long end,String[] sortBy,String sortType,int type,String... fields) {
		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);	
		Query query = new Query();
		Criteria criteriaTime =null;
		if(terid!=null || "".equals(terid)) {
			Criteria criteria = Criteria.where(TERMINALNO).is(terid);
			query.addCriteria(criteria);
		}
		//时间查询
		if(beg==null || beg<0) {
			beg = 0L;
		}
		
		criteriaTime = Criteria.where(CREATETIME).gte(new Date(beg));
		if(end!=null && end>0) {
			criteriaTime.lte(new Date(end));
		}	
		if(criteriaTime!=null) {
			query.addCriteria(criteriaTime);
		}
		
		//按照时间排序
		if(sortBy!=null && sortBy.length>0) {
			Direction direction = null;
			if("DESC".equals(sortType)) {
				direction = Sort.Direction.DESC;
			}else {
				direction = Sort.Direction.ASC;
			}
			query.with(new Sort(direction, sortBy));
		}
		
		List<Map<String, Object>> res = finder.findAll(query,getDbName(singal,projectId,new Date(beg)));
		List<Map<String, Object>> out = new ArrayList<>();
		res.forEach(action->{
			out.add(MapResultUtils.getList(action, fields, type));
		});
		
		return out;
		
		
	}

	
	/**
	 * 查询一天的数据
	 * @param singal
	 * @param projectId
	 * @param terid
	 * @param beg
	 * @param end
	 * @param sortBy
	 * @param sortType
	 * @param includeType
	 * @param fields
	 * @param countIncludeType
	 * @param countFields
	 * @return
	 */
	public List<Map<String, Object>> findTersAllRealDataBooleanCountOneDay(String singal, String projectId,String terid, Long beg, Long end,
			String[] sortBy, String sortType, String includeType, String[] fields, String countIncludeType,
			String[] countFields) {
		// TODO Auto-generated method stub
		MongoDbBaseFinder finder = new MongoDbBaseFinder(histroyTemp);	
		Query query = new Query();
		Criteria criteriaTime =null;
		if(terid!=null || "".equals(terid)) {
			Criteria criteria = Criteria.where(TERMINALNO).is(terid);
			query.addCriteria(criteria);
		}
		
//		criteriaTime = Criteria.where("_id").regex("^"+terid+"_"+);
		//时间查询
		if(beg==null || beg<0) {
			beg = 0L;
		}
		
		criteriaTime = Criteria.where(CREATETIME).gte(new Date(beg));
		if(end!=null && end>0) {
			criteriaTime.lte(new Date(end));
		}	
		if(criteriaTime!=null) {
			query.addCriteria(criteriaTime);
		}
		
		//按照时间排序
		if(sortBy!=null && sortBy.length>0) {
			Direction direction = null;
			if("DESC".equals(sortType)) {
				direction = Sort.Direction.DESC;
			}else {
				direction = Sort.Direction.ASC;
			}
			query.with(new Sort(direction, sortBy));
		}
		
		List<Map<String, Object>> res = finder.findAll(query,getDbName(singal,projectId,new Date(beg)));
		List<Map<String, Object>> out = new ArrayList<>();
		res.forEach(action->{
			Map<String, Object>  map = MapResultUtils.getList(action, fields, includeType);
			map = MapResultUtils.parseBooleanCount(map, countIncludeType, countFields);
			out.add(map);
		});
		
		return out;
	}
	
	private String getDbName(String singal,String projectId,Date btime) {
		String year = TimeUtil.getYearAndMonth(btime);
		System.out.println(COLLECTION_NAME+"_"+projectId+"_"+singal+"_"+year);
		return COLLECTION_NAME+"_"+projectId+"_"+singal+"_"+year;
	}
	
	
}
