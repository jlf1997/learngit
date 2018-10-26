package com.cimr.master.comm.jwt;

import com.cimr.comm.config.AppProperties;

/**
 * 服务器信息
 * @author Administrator
 *
 */
public class CIMRServer {

	/**
	 * 路由地址
	 */
	public static final String server_ip = AppProperties.getApiIp();
	/**
	 * 路由端口
	 */
	public static final String server_port =AppProperties.getApiPort();
	
	/**
	 * 存放从服务端获取的token
	 */
	public static String token;
	
	
	
}
