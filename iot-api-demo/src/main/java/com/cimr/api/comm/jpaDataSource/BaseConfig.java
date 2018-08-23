package com.cimr.api.comm.jpaDataSource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cimr.boot.sql.config.AbstractSqlDataSourceConfig;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBase",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerBase", //配置 事物管理器  transactionManager
        basePackages = {"com.cimr.api.*.jpa.base"}//设置dao（repo）所在位置
)
public class BaseConfig extends AbstractSqlDataSourceConfig{
	
	@Bean(name = "baseDataSource")
    @Primary
    @Qualifier("baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

	@Override
	public String entityPath() {
		// TODO Auto-generated method stub
		return "com.cimr.api.*.model.base";
	}

	/**
    *
    * @param builder
    * @return
    */
   @Bean(name = "entityManagerFactoryBase")
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryMgr(EntityManagerFactoryBuilder builder) {
       return super.entityManagerFactoryMgr(builder);
   }
   
   @Bean(name = "transactionManagerBase")
   public PlatformTransactionManager transactionManagerMgr(EntityManagerFactoryBuilder builder) {
       return super.transactionManagerMgr(builder);
   }

   protected Map<String, String> getVendorProperties(DataSource dataSource) {
       Map map =  jpaProperties.getHibernateProperties(dataSource);
       map.put("hibernate.hbm2ddl.auto","update");
       return map;
   }

@Override
protected String persistenceUnit() {
	// TODO Auto-generated method stub
	return "basePersistenceUnit";
}

}
