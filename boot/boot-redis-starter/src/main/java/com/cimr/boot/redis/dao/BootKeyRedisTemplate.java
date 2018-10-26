package com.cimr.boot.redis.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.cimr.boot.redis.model.RedisPage;
import com.cimr.boot.utils.LogsUtil;

public class BootKeyRedisTemplate<K,V>  {

	
	private static final Logger log = LoggerFactory.getLogger(BootKeyRedisTemplate.class);
	
	
	
	/**
	 * redis 分页查询  使用scan方法 
	 * @param redisTemplate
	 * @param pattern
	 * @param limit
	 * @param page
	 * @param count 每次扫描的数目 
	 * @return
	 */
	 public RedisPage keyPages(String pattern,int page,RedisTemplate<K, V> redisTemplate,int limit,int count){
		try {
			 RedisPage execute = redisTemplate.execute(new RedisCallback<RedisPage>() {
		    	    @Override
		    	    public RedisPage doInRedis(RedisConnection connection) {
		    	        Set<String> binaryKeys = new HashSet<>();
		    	        int i =0;
		    	        if(connection instanceof RedisClusterConnection) {
		    	        	log.info("集群环境不支持scan，使用keys命令");
		    	        	Set<byte[]> keys = new HashSet<>();
		    	        	RedisClusterConnection rconnection = (RedisClusterConnection) connection ;
		    	        	Iterator<RedisClusterNode> iterator = rconnection.clusterGetNodes().iterator();
		    	        	while(iterator.hasNext()) {
		    	        		RedisClusterNode node = iterator.next();
		    	        		keys.addAll(rconnection.keys(node,pattern.getBytes()));
		    	        	}
		    	        	Set<String> strSet = new HashSet<>();
		    	        	keys.forEach(action->{
		    	        		strSet.add(new String(action));
		    	        	});
		    	        	Iterator<String> iteratorKeys = strSet.iterator();
		    	        	while(iteratorKeys.hasNext()) {
		    	        		String key = iteratorKeys.next();
			    	    		 if(i>=limit*page && i<limit*page+limit) {
			    	    			 binaryKeys.add(key);
			    	     		}else if(i>=limit*page+limit){
			    	     			break;//终止迭代
			    	     		}
			    	    		i++;
		    	        	}
		    	        }else {
		    	        	 Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build());
				    	        while(cursor.hasNext()) {
				    	        	 byte[] key = cursor.next();
				    	        	
				    	    		 if(i>=limit*page && i<limit*page+limit) {
				    	    			 binaryKeys.add(new String(key));
				    	     		}else if(i>=limit*page+limit){
				    	     			break;//终止scan迭代
				    	     		}
				    	     		i++;
				    	    	}
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
	 
	 

	 
}
