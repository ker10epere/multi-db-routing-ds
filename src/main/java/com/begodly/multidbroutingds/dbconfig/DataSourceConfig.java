package com.begodly.multidbroutingds.dbconfig;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.begodly.multidbroutingds.repository",
                "com.begodly.multidbroutingds.entity"
        },
        transactionManagerRef = "transcationManager",
        entityManagerFactoryRef = "entityManager"
)
@EnableTransactionManagement
@RequiredArgsConstructor
@DependsOn("dataSourceRouting")
public class DataSourceConfig {

    private final DataSourceRouting dataSourceRouting;

    @Bean
    @Primary
    public DataSource dataSource() {
        return dataSourceRouting;
    }

    @Primary
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder,
            @Value("${spring.jpa.hibernate.ddl-auto:update}")
            String ddlAuto
    ) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);

        // initialization works only for default database

//        builder.dataSource(dataSourceRouting.dataSourceTwoDataSource())
//                .packages(AnnotationUtils.findAnnotation(DataSourceConfig.class, EnableJpaRepositories.class).basePackages())
//                .properties(properties)
//                .build();
        return builder.dataSource(dataSource())
                .packages(AnnotationUtils.findAnnotation(DataSourceConfig.class, EnableJpaRepositories.class).basePackages())
                .properties(properties)
                .build();
    }

    @Bean(name = "entityManagerTwo")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanTwo(
            EntityManagerFactoryBuilder builder,
            @Value("${spring.jpa.hibernate.ddl-auto:update}")
            String ddlAuto
    ) {
        HashMap<String, Object> properties = new HashMap<>();
        // initialization works only for default database
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        return builder.dataSource(dataSourceRouting.dataSourceTwoDataSource())
                .packages(AnnotationUtils.findAnnotation(DataSourceConfig.class, EnableJpaRepositories.class).basePackages())
                .properties(properties)
                .build();
    }

    @Bean(name = "transcationManager")
    public JpaTransactionManager transactionManager(
            @Autowired @Qualifier("entityManager")
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
    ) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}