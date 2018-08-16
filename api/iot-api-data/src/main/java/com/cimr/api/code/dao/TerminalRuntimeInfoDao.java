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
public class TerminalRuntimeInfoDao {
	
    
    private NormalRedisDao normalRedisDao;
    
    @Autowired
	protected RedisProperties redisProperties;
    
	@Autowired
	protected RedisTemplateConfig redisTemplateConfig;
	
	@Resource(name="fastJsonredisTemplate")
	private RedisTemplate<String, Object> redisTemplate ;

    @PostConstruct
    public void postConstruct() {
    	normalRedisDao = new NormalRedisDao();
		 
    }
    
    
    /**
     * 查询终端的runntime redis信息
     * @param termimals 终端列表
     * @param type 查询类型
     * @param fields 需要查询或排除的字段
     * @return
     */
    public List<Map<String,Object>> getData(List<TerimalModel> termimals,String type, String... fields) {
    	redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getRuntimeIndex());
    	return normalRedisDao.getData(redisTemplate,RedisProperties.RUNNTIME, termimals, type, fields);
	} 
    
    
    /**
     * 查询终端的runntime redis信息
     * @param termimals 终端列表
     * @param includeType 查询类型
     * @param fields 需要查询或排除的字段
     * @param countIncludeType 统计类型
     * @param countFields 需要统计或者排除统计的字段
     * @return
     */
    public List<Map<String, Object>> getDateBoolean(List<TerimalModel> termimals,  String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
    	redisTemplate = RedisTemplateFactory.changeDataBase(redisTemplate, redisProperties.getRuntimeIndex());
    	return normalRedisDao.getDateBoolean(redisTemplate,RedisProperties.RUNNTIME, termimals, includeType, fields, countIncludeType, countFields);
	}
    
    
    
    
}
