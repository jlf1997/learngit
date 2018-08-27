package com.cimr.api.code.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.config.RedisProperties;
import com.cimr.api.code.util.RedisTemplateFactory;
import com.cimr.api.comm.model.TerimalModel;
import com.cimr.boot.redis.RedisTemplateConfig;


@Repository
public class TerRealDataDao {
	
	
	
	/**
	 * redis 查询通用辅助工具类
	 */
	private NormalRedisDao normalRedisDao;
    

	
	@Autowired
	private RedisProperties redisProperties;
	
	@Autowired
	protected RedisTemplateConfig redisTemplateConfig;
	
	@Resource(name="fastJsonredisTemplate")
	private RedisTemplate<String, Object> redisTemplate ;
	
    @PostConstruct
    public void postConstruct() {
	  normalRedisDao = new NormalRedisDao();
    }
	

	/**
	 * 获取redis中实时数据 不建议使用 ，改为使用type 为string 的版本
	 * @param termimals
	 * @param signal
	 * @param fields
	 * @return
	 */
	@Deprecated
	public List<Map<String,Object>> getData(List<TerimalModel> termimals, String signal,String projectId,int type, String... fields) {
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		 String dbName = getDbName(projectId,signal);
		 return normalRedisDao.getData(redisTemplate,RedisProperties.NEW_DATA+projectId+"_"+signal, termimals, type,fields);
	}
	
	/**
	 * 获取redis中实时数据
	 * @param termimals
	 * @param signal
	 * @param type
	 * @param fields
	 * @return
	 */
	public List<Map<String,Object>> getData(List<TerimalModel> termimals, String signal,String projectId,String type, String... fields) {
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		 String dbName = getDbName(projectId,signal);
		 return normalRedisDao.getData(redisTemplate,dbName, termimals, type, fields);
	}


	
	/**
	 * 获取redis中实时数据 统计其中boolean类型数量
	 * @param termimals
	 * @param signal
	 * @param includeType
	 * @param fields
	 * @param countIncludeType
	 * @param countFields
	 * @return
	 */
	public List<Map<String, Object>> getDataBoolean(List<TerimalModel> termimals, String signal, String projectId,String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		 redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getNewdataIndex());
		 String dbName = getDbName(projectId,signal);
		 return normalRedisDao.getDateBoolean(redisTemplate,dbName, termimals, includeType, fields, countIncludeType, countFields);
	}
	
	
	/**
	 * 定义redis key
	 * @param projectId
	 * @param signal
	 * @return
	 */
	private String getDbName(String projectId,String signal) {
		return RedisProperties.NEW_DATA+projectId+"_"+signal;
	}
	
	
	
	
}
