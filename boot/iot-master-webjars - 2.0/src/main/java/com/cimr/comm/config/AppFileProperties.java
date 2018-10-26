package com.cimr.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config.file")
public class AppFileProperties {

	
	
	private static String serverPath;
	
	private static String basePath;
	
	private static String rootPath;

	
	private static String tempPath;
	
	private static String fdfsServer;

	public static String getServerPath() {
		return serverPath;
	}
	
	/**
	 * 设置文件服务器地址
	 * @param serverPath
	 */
	@Value("${app.config.file.serverPath}")
	public static void setServerPath(String serverPath) {
		AppFileProperties.serverPath = serverPath;
	}

	public static String getBasePath() {
		return basePath;
	}
	/**
	 * 设置文件跟路径
	 * @param basePath
	 */
	@Value("${app.config.file.basePath}")
	public static void setBasePath(String basePath) {
		AppFileProperties.basePath = basePath;
	}

	public static String getRootPath() {
		return rootPath;
	}

	/**
	 * 
	 * @param rootPath
	 */
	@Value("${app.config.file.rootPath}")
	public static void setRootPath(String rootPath) {
		AppFileProperties.rootPath = rootPath;
	}

	public static String getTempPath() {
		return tempPath;
	}
	@Value("${app.config.file.tempPath}")
	public static void setTempPath(String tempPath) {
		AppFileProperties.tempPath = tempPath;
	}

	public static String getFdfsServer() {
		return fdfsServer;
	}
	
	@Value("${app.config.file.fdfsServer}")
	public static void setFdfsServer(String fdfsServer) {
		AppFileProperties.fdfsServer = fdfsServer;
	}
	
	
	
	
	
	
	
}
