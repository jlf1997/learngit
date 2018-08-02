package com.cloud.util;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * Memcached工具类
 * 
 * @author GaoHuanjie
 */
public class MemcachedUtils {

	public static MemcachedClient memCachedClient = null;
	private static final Logger logger = LoggerFactory.getLogger(MemcachedUtils.class);

	static {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"));
		try {
			memCachedClient = builder.build();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	private MemcachedUtils() {
	}
	
	public static void set(String id,int time,Object obj){
		try {
			memCachedClient.set(id, time, obj);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Object get(String id){
		Object obj = null;
		try {
			obj = memCachedClient.get(id);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

}
