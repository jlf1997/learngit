package com.cimr.api.comm.jpaDataSource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cimr.boot.sql.config.AbstractSqlDataSourceConfig;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMgr",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerMgr", //配置 事物管理器  transactionManager
        basePackages = {"com.cimr.api.*.jpa.mgr"}//设置dao（repo）所在位置
)
public class MgrConfig extends AbstractSqlDataSourceConfig{
	

   

  //管控平台数据源
    @Bean(name = "mgrDataSource")
    @Qualifier("mgrDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mgr")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

	@Override
	public String entityPath() {
		// TODO Auto-generated method stub
		return "com.cimr.api.*.model.mgr";
	}

	   @Bean(name = "entityManagerFactoryMgr")
	   public LocalContainerEntityManagerFactoryBean entityManagerFactoryMgr(EntityManagerFactoryBuilder builder) {
	       return super.entityManagerFactoryMgr(builder);
	   }
	   
	   @Bean(name = "transactionManagerMgr")
	   public PlatformTransactionManager transactionManagerMgr(EntityManagerFactoryBuilder builder) {
	       return super.transactionManagerMgr(builder);
	   }
	   
	   protected Map<String, String> getVendorProperties(DataSource dataSource) {
	       Map map =  jpaProperties.getHibernateProperties(dataSource);
	       map.put("hibernate.hbm2ddl.auto","create");
	       return map;
	   }

	@Override
	protected String persistenceUnit() {
		// TODO Auto-generated method stub
		return "mgrPersistenceUnit";
	}
}
