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

import com.cimr.boot.mongodb.MongoDbBaseFinder;


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
	
	private String singal = "503447856";
	
	private String projectId = "P00001";
	
	private static String falutTime = "gatherMsgTime";

	
	private String getDbName() {
		return "REALDATA_SIGNAL_"+projectId+"_"+singal;
	}

	
	/**
	 * 查询出所有存在的异常的数据
	 * @return
	 */
	public List<Map<String,Object>> findFaultList(Date faultStartTime,Date faultEndTime){
		MongoDbBaseFinder finder = new MongoDbBaseFinder(mongoTemplate);
		Query query = new Query();
		query.with(new Sort(new Order(Direction.ASC,falutTime)));
		Criteria criteria =null;
		if( faultEndTime!=null) {
			criteria = Criteria.where(falutTime).lte(faultEndTime);
		}
		if(faultStartTime!=null) {
			if(criteria==null) {
				criteria = Criteria.where(falutTime);
			}
			criteria = criteria.gte(faultStartTime);
		}
		if(criteria!=null) {
			query.addCriteria(criteria);
		}
		return finder.findAll(query,getDbName());
		
	}
	
	
}
