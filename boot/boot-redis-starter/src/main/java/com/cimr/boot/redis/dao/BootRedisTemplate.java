package com.cimr.boot.redis.dao;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.cimr.boot.redis.model.RedisPage;
import com.cimr.boot.utils.LogsUtil;

public class BootRedisTemplate {

	
	private static final Logger log = LoggerFactory.getLogger(BootRedisTemplate.class);

	/**
	 * redis 分页查询  使用scan方法 
	 * @param redisTemplate
	 * @param pattern
	 * @param limit
	 * @param page
	 * @param count 每次扫描的数目 
	 * @return
	 */
	 public RedisPage scanPage(RedisTemplate<String, Object> redisTemplate,String pattern,int limit,int page,int count){
		try {
			 RedisPage execute = redisTemplate.execute(new RedisCallback<RedisPage>() {
		    	    @Override
		    	    public RedisPage doInRedis(RedisConnection connection) {
		    	        Set<String> binaryKeys = new HashSet<>();
		    	        Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build());
		    	        int i =0;
		    	        while(cursor.hasNext()) {
		    	        	 byte[] key = cursor.next();
		    	    		 if(i>=limit*page && i<limit*page+limit) {
		    	    			 binaryKeys.add(new String(key));
		    	     		}
		    	     		i++;
		    	    	}
		    	        RedisPage redisPage = new RedisPage();
		    	    	redisPage.setKeys(binaryKeys);
		    	    	redisPage.setCount(i);
		    	        return redisPage;
		    	    }
		    	});
		    	
		    	return execute;
		}catch(Exception e) {
			String error = LogsUtil.getStackTrace(e);
			log.error(error);
			return null;
		}
		

	   }
	 
	 
	 public Object ping(RedisTemplate<String, Object> redisTemplate) {
	        return redisTemplate.execute(new RedisCallback() {
	            public String doInRedis(RedisConnection connection) throws DataAccessException {

	                return connection.ping();
	            }
	        });
	    }
	 
	 /**
	  * redis 分页查询  使用scan方法  默认每次扫描10000条
	  * @param redisTemplate
	  * @param pattern
	  * @param limit
	  * @param page
	  * @return
	  */
	 public RedisPage scanPage(RedisTemplate<String, Object> redisTemplate,String pattern,int limit,int page) {
		 return this.scanPage(redisTemplate, pattern, limit, page,10000);
	 }
	 
	 
}
