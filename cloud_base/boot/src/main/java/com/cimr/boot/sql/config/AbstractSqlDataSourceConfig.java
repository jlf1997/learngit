package com.cimr.boot.sql.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class AbstractSqlDataSourceConfig {
	
	protected abstract DataSource getDataSource();
	
	protected abstract String entityPath();
	
	protected abstract String persistenceUnit();
	
	protected abstract Map<String, String> getVendorProperties(DataSource dataSource);
	
	/**
	 * 设定指定的LocalContainerEntityManagerFactoryBean：连接工厂
	 * @param builder
	 * @return
	 */
	protected abstract LocalContainerEntityManagerFactoryBean setLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder);
	/**
	 * 设定指定的PlatformTransactionManager：事务管理器
	 * @param builder
	 * @return
	 */
	protected abstract PlatformTransactionManager setPlatformTransactionManager(EntityManagerFactoryBuilder builder);
	
	
	@Autowired
    protected JpaProperties jpaProperties;
	
	
	/**
    *
    * @param builder
    * @return
    */
	@Bean(name = "entityManagerFactory")
	@Primary
	protected LocalContainerEntityManagerFactoryBean entityManagerFactoryMgr(EntityManagerFactoryBuilder builder) {
	   DataSource ds = getDataSource();
       return builder
               //设置数据源
               .dataSource(ds)
               //设置数据源属性
               .properties(getVendorProperties(ds))
               //设置实体类所在位置.扫描所有带有 @Entity 注解的类
               .packages(entityPath())
               // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
               // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
               .persistenceUnit(persistenceUnit())
               .build();

   }

  

   /**
    * 配置事物管理器
    *
    * @param builder
    * @return
    */
   @Bean(name = "transactionManager")
   @Primary
   protected PlatformTransactionManager transactionManagerMgr(EntityManagerFactoryBuilder builder) {
       return new JpaTransactionManager(entityManagerFactoryMgr(builder).getObject());
   }
}
