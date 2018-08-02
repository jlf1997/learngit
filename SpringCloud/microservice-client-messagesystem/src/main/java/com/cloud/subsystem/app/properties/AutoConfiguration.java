package com.cloud.subsystem.app.properties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gexin.rp.sdk.http.IGtPush;

/**
 * 加载相关属性类
 * @author Administrator
 *
 */
@Configuration
public class AutoConfiguration {
	
	private static final String appId = KeyConstants.appid;
	private static final String appKey = KeyConstants.appKey;
	private static final String masterSecret = KeyConstants.masterSecret;
	private static final String host = KeyConstants.getuiHost;


	
	@Bean
    @ConditionalOnMissingBean
    public GetuiProperties getuiProperties() {
        return new GetuiProperties();
    }	
	
	@Bean
    @ConditionalOnMissingBean
    public IGtPush iGtPush() {
		 // https连接
	     IGtPush push = new IGtPush(appKey, masterSecret, true);		
        return push;
    }
	
	@Bean(name="executorServiceForPushApp")
    @ConditionalOnMissingBean
	public ExecutorService getExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		return executorService;
	}

}
