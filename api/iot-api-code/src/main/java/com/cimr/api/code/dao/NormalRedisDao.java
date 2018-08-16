package com.cimr.api.code.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.cimr.api.comm.model.TerimalModel;
import com.cimr.util.MapResultUtils;


public class NormalRedisDao {
	
    
  
private static final Logger log = LoggerFactory.getLogger(NormalRedisDao.class);

    
    /**
     * 通用redis 查询类 
     * @param redisTemplate 
     * @param dbName redis 表名称 适用于dbName:终端id 类型的表的查询
     * @param termimals 待查询的终端列表
     * @param type 查询结果处理类型：0 1 -1
     * @param fields 需要查询或者排除字段名称
     * @return 以map列表的形式返回查询的数据
     */
	@Deprecated
    public List<Map<String,Object>> getData(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,int type, String... fields) {
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj  = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		
    		if(out!=null) {
    			out = MapResultUtils.getList(out, fields, type);
    			res.add(out);
    		}
    	}    	
    	return res;
	} 
    
    /**
     * 通用redis 查询类 
     * @param redisTemplate 
     * @param dbName redis 表名称 适用于dbName:终端id 类型的表的查询
     * @param termimals 待查询的终端列表
     * @param type 查询结果处理类型：include 或exclude
     * @param fields 需要查询或者排除字段名称
     * @return 以map列表的形式返回查询的数据
     */
    public List<Map<String,Object>> getData(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,String type, String... fields) {
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj  = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		
    		if(out!=null) {
    			out = MapResultUtils.getList(out, fields, type);
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	}
    
    
    /**
     * 通用redis 查询类 ,并统计其中的boolean类型数量
     * @param redisTemplate
     * @param dbName 表名称 适用于dbName:终端id 类型的表的查询
     * @param termimals 待查询的终端列表
     * @param includeType 查询结果处理类型：include 或exclude
     * @param fields 需要查询或者排除字段名称
     * @param countIncludeType boolean统计处理类型：include 或exclude
     * @param countFields 需要统计或者排除统计的字段名称
     * @return
     */
    public List<Map<String, Object>> getDateBoolean(RedisTemplate<String, Object> redisTemplate,String dbName,List<TerimalModel> termimals,  String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		List<Map<String,Object>> res = new ArrayList<>();
		Object obj;
    	for(TerimalModel ter:termimals) {
    		obj = redisTemplate.opsForValue().get(dbName+":"+ter.getTerId());
    		Map<String,Object> out =MapResultUtils.parseRedisObject(obj);
    		if(out!=null) {
    			out = MapResultUtils.parseBooleanCount(out, countIncludeType, countFields);
        		out = MapResultUtils.getList(out, fields, includeType);
    			res.add(out);
    		}
    	}    	
    	
    	return res;
	}
}
