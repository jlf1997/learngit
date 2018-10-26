package com.cimr.boot.redis.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.cimr.boot.redis.model.RedisPage;
import com.cimr.boot.utils.LogsUtil;


/**
 * hash类型
 * @author Administrator
 *
 */
public class BootHRedisTemplate<K,V> {

	
	private static final Logger log = LoggerFactory.getLogger(BootHRedisTemplate.class);

	

	/**
	 * 从哈希数据类型中获取数据 使用scan方式
	 * @param redisTemplate
	 * @param key
	 * @param pattern
	 * @param limit
	 * @param page
	 * @param count
	 * @return
	 */
	public RedisPage getHPage(K key,String pattern,int page,RedisTemplate<K, V> redisTemplate,int limit,int count) {
		try {
			Map<Object,Object> res = new HashMap<>();
			ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build();
			Cursor<Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
			int i = 0;
			while(cursor.hasNext()) {
	        	Map.Entry<Object, Object> keys = cursor.next();
	        	if(i>=limit*page && i<limit*page+limit) {
	    			 res.put(keys.getKey(),keys.getValue());
	     		}
	    		i++;
			}
			RedisPage redisPage = new RedisPage();
 	    	redisPage.setObj(res);
 	    	redisPage.setCount(i);
 	        return redisPage;
		}catch(Exception e) {
			String error = LogsUtil.getStackTrace(e);
			log.error(error);
			return null;
		}
	}
	
	/**
	 * 使用scan获取全部
	 * @param key
	 * @param pattern
	 * @return
	 */
	public Map<Object,Object> getHMapObject(K key,String pattern,RedisTemplate<K, V> redisTemplate,int limit,int count) {
		try {
			Map<Object,Object> res = new HashMap<>();
			ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build();
			Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
			int i = 0;
			while(cursor.hasNext()) {
	        	Map.Entry<Object, Object> keys = cursor.next();
	        	if(i<limit || limit<0) {
	        		res.put(keys.getKey(), keys.getValue());
	        	}
	    		i++;
			}
			return res;
		}catch(Exception e) {
			String error = LogsUtil.getStackTrace(e);
			log.error(error);
			return null;
		}
	}
	
	
	

}
