package com.cimr.api.comm.jpaDataSource;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SqlDataSourceConfig {
	//自身底层数据源
	@Bean(name = "baseDataSource")
    @Primary
    @Qualifier("baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    public DataSource primaryDatasource() {
        return DataSourceBuilder.create().build();
    }


    
 
}
