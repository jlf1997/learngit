package com.cimr.api.comm.jpaDataSource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBase",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerBase", //配置 事物管理器  transactionManager
        basePackages = {"com.cimr.api.*.jpa.base"}//设置dao（repo）所在位置
)
public class BaseConfig {
	@Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("baseDataSource")
    private DataSource baseDataSource;


    /**
     *
     * @param builder
     * @return
     */
    @Bean(name = "entityManagerFactoryBase")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBase(EntityManagerFactoryBuilder builder) {

        return builder
                //设置数据源
                .dataSource(baseDataSource)
                //设置数据源属性
                .properties(getVendorProperties(baseDataSource))
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.cimr.api.*.model.base")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("basePersistenceUnit")
                .build();

    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        Map map =  jpaProperties.getHibernateProperties(dataSource);
        map.put("hibernate.hbm2ddl.auto","update");
        return map;
    }

    /**
     * 配置事物管理器
     *
     * @param builder
     * @return
     */
    @Bean(name = "transactionManagerBase")
    @Primary
    PlatformTransactionManager transactionManagerBase(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryBase(builder).getObject());
    }
}
