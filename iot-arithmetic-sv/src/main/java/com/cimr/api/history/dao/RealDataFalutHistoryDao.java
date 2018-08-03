package com.cimr.api.history.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.comm.configuration.Setting;


/**
 * 查询错误日志历史记录
 * @author Administrator
 *
 */
@Repository
public class RealDataFalutHistoryDao {
	
	@Autowired
	@Qualifier(value="history")
	protected MongoTemplate mongoTemplate;
	@Autowired
	private ProjectPropertities projectPropertities;
	
	@Autowired
	private Setting setting;
	
	
	

	
//	/**
//	 * 查询出所有存在的异常的数据
//	 * @return
//	 */
//	public List<Map<String,Object>> findList(Date faultStartTime,Date faultEndTime){
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(mongoTemplate);
//		Query query = new Query();
//		query.with(new Sort(new Order(Direction.ASC,gatherMsgTime)));
//		Criteria criteria =null;
//		if( faultEndTime!=null) {
//			criteria = Criteria.where(gatherMsgTime).lte(faultEndTime);
//		}
//		if(faultStartTime!=null) {
//			if(criteria==null) {
//				criteria = Criteria.where(gatherMsgTime);
//			}
//			criteria = criteria.gte(faultStartTime);
//		}
//		if(criteria!=null) {
//			query.addCriteria(criteria);
//		}
//		return finder.findAll(query,getDbName(projectPropertities.getSingalFault()));
//		
//	}
//	
//	
//	/**
//	 * 获取时间范围内所有数据
//	 * @param bTime
//	 * @param eTime
//	 * @param singal
//	 * @return
//	 */
//	public List<Map<String,Object>> findAll(Date bTime,Date eTime,String singal){
//		String gatherMsgTime = setting.getGatherMsgTime(singal);
//		MongoDbBaseFinder finder = new MongoDbBaseFinder(mongoTemplate);
//		Query query = new Query();
//		query.with(new Sort(new Order(Direction.ASC,gatherMsgTime)));
//		Criteria criteria =null;
//		if( eTime!=null) {
//			criteria = Criteria.where(gatherMsgTime).lte(eTime);
//		}
//		if(bTime!=null) {
//			if(criteria==null) {
//				criteria = Criteria.where(gatherMsgTime);
//			}
//			criteria = criteria.gte(bTime);
//		}
//		if(criteria!=null) {
//			query.addCriteria(criteria);
//		}
//		return finder.findAll(query,getDbName(singal));
//	}
	
	
}
