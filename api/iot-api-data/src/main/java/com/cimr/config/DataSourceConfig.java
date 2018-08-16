package com.cimr.config;


import org.springframework.context.annotation.Configuration;


/**
 * 用于配置多数据源 ，暂不使用
 * @author Administrator
 *
 */
@Configuration 
public class DataSourceConfig {
//	@Bean(name = "aDataSource")
//    @Qualifier("aDataSource")
//    @Primary
//    @ConfigurationProperties(prefix="spring.datasource.aDataSource")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//    @Bean(name = "bDataSource")
//    @Qualifier("bDataSource")
//    @ConfigurationProperties(prefix="spring.datasource.bDataSource")
//    public DataSource orcleDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
