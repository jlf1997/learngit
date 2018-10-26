package com.cimr.boot;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@ConditionalOnProperty(prefix = "boot.jpa", value = "enable", matchIfMissing = true) 
public class BootMysqlConfiguration {
	
	
		
		private static final Logger log = LoggerFactory.getLogger(BootMysqlConfiguration.class);


		/**
		 * 如果没指定 则默认使用此数据库连接池
		 * @return
		 */
		@Bean
		@ConditionalOnMissingBean(DataSource.class)
		@ConditionalOnProperty(prefix = "boot.jpa.config", value = "type",havingValue="com.alibaba.druid.pool.DruidDataSource"
		, matchIfMissing = true) 
	    public DataSource primaryDatasource() {
			log.info("初始化durid datasource");
			return DruidDataSourceBuilder.create().build();
	    }
}
