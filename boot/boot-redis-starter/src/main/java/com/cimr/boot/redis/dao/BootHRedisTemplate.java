package com.cimr.boot.redis.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.cimr.boot.redis.model.RedisPage;
import com.cimr.boot.utils.LogsUtil;

public class BootHRedisTemplate {

	
	
	private static final Logger log = LoggerFactory.getLogger(BootHRedisTemplate.class);
	
	
	private RedisTemplate<Object, Object> redisTemplate;
	
	private int count;
	
	private int limit;
	
	public BootHRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setKeySerializer(new FastJsonRedisSerializer(Object.class));
		this.redisTemplate.setHashKeySerializer(new FastJsonRedisSerializer(Object.class));
		this.redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
		this.redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));
		count = 10000;
		limit = 10;
	}
	
	public BootHRedisTemplate count(int count) {
		this.count = count;
		return this;
	}

	public BootHRedisTemplate limit(int limit) {
		this.limit = limit;
		return this;
	}
	
	public BootHRedisTemplate keySerializer(RedisSerializer redisSerializer) {
		redisTemplate.setKeySerializer(redisSerializer);
		return this;
	}
	
	public BootHRedisTemplate hashKeySerializer(RedisSerializer redisSerializer) {
		redisTemplate.setHashKeySerializer(redisSerializer);
		return this;
	}
	
	public BootHRedisTemplate valueSerializer(RedisSerializer redisSerializer) {
		redisTemplate.setValueSerializer(redisSerializer);
		return this;
	}
	
	public BootHRedisTemplate hashValueSerializer(RedisSerializer redisSerializer) {
		redisTemplate.setHashValueSerializer(redisSerializer);
		return this;
	}

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
	public RedisPage getHPage(Object key,String pattern,int page) {
		try {
			Map<Object,Object> res = new HashMap<>();
			ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build();
			Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, options);
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
	public Map<Object,Object> getHMapObject(Object key,String pattern) {
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
	
	
	public Object get(Object key,Object hashKey){
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	
	public List<Object> multiGet(Object key,Collection<Object> hashKeys){
		return redisTemplate.opsForHash().multiGet(key, hashKeys);
	}
	/**
	 * 存储
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void put(Object key,Object hashKey,Object value) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
		}catch(Exception e) {
			log.error("存储hash发生异常："+LogsUtil.getStackTrace(e));
		}
	}
	
	public void putAll(Object key,Map<Object,Object> m) {
		try {
			redisTemplate.opsForHash().putAll(key, m);
		}catch(Exception e) {
			log.error("存储hash发生异常："+LogsUtil.getStackTrace(e));
		}
	}
	
	
	@Deprecated
	private interface BootHRedisTemplateOpers<H,K>{
		public H getHKey(byte[] hkey);
		public K getValue(byte[] value);
	}
	
	@Deprecated
	private <H,K> Map<H,K> parseCur(Cursor<Map.Entry<byte[], byte[]>> cursor,BootHRedisTemplateOpers<H,K> opers) {
		 int i =0;
		 Map<H,K> map = new HashMap<>();
		 while(cursor.hasNext()) {
	        	Map.Entry<byte[], byte[]> key = cursor.next();
	        	if(i<limit || limit<0) {
	        		byte[] hkey = key.getKey();
	        		byte[] value = key.getValue();
	        		map.put(opers.getHKey(hkey), opers.getValue(value));
	        	}
	    		i++;
 	    }
		return map;
	}
	
	
	/**
	 * 获取指定key中匹配模式的值
	 * @param key
	 * @param pattern
	 * @return
	 */
	@Deprecated
	private Map<byte[],byte[]> getHList(String key,String pattern) {
		try {
			Map<byte[],byte[]> execute = redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
		    	    @Override
		    	    public Map<byte[],byte[]> doInRedis(RedisConnection connection) {
		    	    	Map<byte[],byte[]> binaryKeys = new HashMap<>();
		    	        int i =0;
	    	        	 Cursor<Map.Entry<byte[], byte[]>> cursor = connection.hScan(key.getBytes(), new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build());
		    	        while(cursor.hasNext()) {
		    	        	Map.Entry<byte[], byte[]> key = cursor.next();
		    	        	if(i<limit || limit<0) {
		    	        		binaryKeys.put(key.getKey(),key.getValue());
		    	        	}
		    	    		i++;
			    	    }
		    	        return binaryKeys;
		    	    }
		    	});
		    	return execute;
		}catch(Exception e) {
			String error = LogsUtil.getStackTrace(e);
			log.error(error);
			return null;
		}
	}

}
