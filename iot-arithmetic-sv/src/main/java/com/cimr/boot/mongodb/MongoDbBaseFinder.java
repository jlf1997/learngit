package com.cimr.boot.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.boot.utils.TimeUtil;
import com.mongodb.DBObject;
import com.mongodb.MongoException;


/**
 * mongodb 查询辅助类
 * @author yangxiang
 *
 */
public class MongoDbBaseFinder {

	public MongoDbBaseFinder(MongoTemplate template) {
		this.template = template;
	}
	
	private MongoTemplate template;
	
	
	/**
	 * 条件查询指定collection数据
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findAll(Query query, String collectionName){
		List<Map<String,Object>> list = new ArrayList<>();
		if(!template.collectionExists(collectionName)) {
			return list;
		}
		template.executeQuery(query, collectionName, new DocumentCallbackHandler() {

			@Override
			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
				// TODO Auto-generated method stub
			
				Map<String,Object> map = new HashMap<>();
				map.putAll(dbObject.toMap());
				list.add(map);
			}
			 
		 });
		 return list;
	}
	

	/**
	 * 分页查询
	 * @param query
	 * @param collectionName
	 * @param sort
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String,Object>> findPage(Query query, String collectionName,Sort sort,int pageNumber, int pageSize) {
		Pageable pageable = new PageRequest(pageNumber, pageSize, sort);
		query.with(pageable);
		List<Map<String,Object>> result = findAll(query,collectionName);
		long total = template.count(query, collectionName);
		Page<Map<String,Object>> page = new PageImpl<>(result,pageable,total);
		return page;
	}
	/**
	 * 获取查询总数
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public long getTotalNum(Query query,String collectionName) {
		return template.count(query, collectionName);
	}
	
	/**
	 * 查询一条记录
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public Map<String,Object> findOne(Query query,String collectionName) {
		List< Map<String,Object>> list = findAll(query,collectionName);
		if(list!=null && list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 判断当前页是否是最后一页
	 * @param query
	 * @param collectionName
	 * @param pageNumber 页号 从0开始
	 * @param pageSize
	 * @return
	 */
	public boolean isLastPage(Query query,String collectionName,int pageNumber,int pageSize) {
		long total =  getTotalNum(query,collectionName);
		if(pageSize==0) {
			return false;
		}
		if(total/pageSize<pageNumber+1) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 查询指定collection的所有数据
	 * @param collectionName
	 * @return
	 */
	public List<Map<String,Object>> findAll(String collectionName){
		Query query = new Query();
		return findAll(query,collectionName);
	}
	
	/**
	 * 增加分页条件
	 * @param skip
	 * @param pageNumber
	 * @param pageSize
	 * @param operations
	 */
	private void addPage(Integer skip,Integer pageNumber, Integer pageSize,List<AggregationOperation> operations) {
		operations.add(Aggregation.skip(pageNumber*pageSize+skip));
		operations.add(Aggregation.limit(pageSize));
	}
	/**
	 * 增加查询条件
	 * @param criterias
	 * @param aggregations
	 * @return
	 */
	private List<AggregationOperation> getOperationsAll(List<Criteria> criterias, List<AggregationOperation> aggregations) {
		List<AggregationOperation> operations = new ArrayList<>();
		for(Criteria criteria:criterias) {
			operations.add(Aggregation.match(criteria));
		}
		for(AggregationOperation aggregation :aggregations) {
			operations.add(aggregation);
		}
		return operations;
	}
		
	/**
	 * 查询所有
	 * @param criterias
	 * @param collectionName
	 * @param aggregations
	 * @return
	 */
	public List<Map<String,Object>> findByAgg(List<Criteria> criterias,String collectionName, List<AggregationOperation> aggregations) {
		List<AggregationOperation> operations = getOperationsAll(criterias,aggregations);
		Aggregation agg = Aggregation.newAggregation(  
				operations);  
		AggregationResults res = template.aggregate(agg,collectionName,Map.class);
		return res.getMappedResults();
		
	}
	/**
	 * 分页查询
	 * @param criterias
	 * @param collectionName
	 * @param skip
	 * @param pageNumber
	 * @param pageSize
	 * @param aggregations
	 * @return
	 */
	public PageModel<Map<String,Object>> findByAgg(int skip,int pageNumber, int pageSize,List<Criteria> criterias,String collectionName,List<AggregationOperation> aggregations) {
		List<AggregationOperation> operations = getOperationsAll(criterias,aggregations);
		Aggregation agg = Aggregation.newAggregation(  
				operations);  
		AggregationResults res= template.aggregate(agg, collectionName,Map.class);
		addPage(skip,pageNumber,pageSize,operations);
		long total = res.getMappedResults().size();
		agg = Aggregation.newAggregation(  
				operations);  
		res = template.aggregate(agg,collectionName,Map.class);
		PageModel<Map<String,Object>> pageModel = new PageModel<>();
		pageModel.setContent(res.getMappedResults());
		pageModel.setPageSize(pageSize);
		pageModel.setTotalCount(total);
		return pageModel;
	}
}
