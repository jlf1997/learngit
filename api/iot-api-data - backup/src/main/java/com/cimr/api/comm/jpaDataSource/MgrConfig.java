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
        entityManagerFactoryRef = "entityManagerFactoryMgr",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "transactionManagerMgr", //配置 事物管理器  transactionManager
        basePackages = {"com.cimr.api.*.jpa.mgr"}//设置dao（repo）所在位置
)
public class MgrConfig {
	@Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("mgrDataSource")
    private DataSource mgrDataSource;


    /**
     *
     * @param builder
     * @return
     */
    @Bean(name = "entityManagerFactoryMgr")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMgr(EntityManagerFactoryBuilder builder) {

        return builder
                //设置数据源
                .dataSource(mgrDataSource)
                //设置数据源属性
                .properties(getVendorProperties(mgrDataSource))
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.cimr.api.*.model.mgr")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("mgrPersistenceUnit")
                .build();

    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        Map map =  jpaProperties.getHibernateProperties(dataSource);
        map.put("hibernate.hbm2ddl.auto","none");
        return map;
    }

    /**
     * 配置事物管理器
     *
     * @param builder
     * @return
     */
    @Bean(name = "transactionManagerMgr")
    PlatformTransactionManager transactionManagerMgr(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryMgr(builder).getObject());
    }
}
