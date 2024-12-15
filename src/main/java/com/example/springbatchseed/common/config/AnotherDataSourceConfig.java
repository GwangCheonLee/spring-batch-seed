package com.example.springbatchseed.common.config;

import com.example.springbatchseed.common.property.AnotherDataSourceProperties;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Another 데이터 소스와 관련된 JPA 설정 클래스입니다.
 * <p>
 * Another 데이터베이스에 대한 DataSource, EntityManagerFactory, TransactionManager를 설정합니다.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.springbatchseed.another.repository",
    entityManagerFactoryRef = "anotherEntityManagerFactory",
    transactionManagerRef = "anotherTransactionManager"
)
@RequiredArgsConstructor
public class AnotherDataSourceConfig {

    private final AnotherDataSourceProperties properties;

    /**
     * Another 데이터 소스 Bean 정의.
     *
     * @return Another 데이터 소스
     */
    @Bean(name = "anotherDataSource")
    public DataSource anotherDataSource() {
        return DataSourceBuilder.create()
            .driverClassName(properties.getDriverClassName())
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }

    /**
     * Another EntityManagerFactory Bean 정의.
     *
     * @param builder    EntityManagerFactoryBuilder
     * @param dataSource Another 데이터 소스
     * @return Another EntityManagerFactory
     */
    @Bean(name = "anotherEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean anotherEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("anotherDataSource") DataSource dataSource) {

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", properties.getHbm2ddlAuto());

        return builder
            .dataSource(dataSource)
            .packages("com.example.springbatchseed.another.entity")
            .persistenceUnit("another")
            .properties(jpaProperties)
            .build();
    }

    /**
     * Another TransactionManager Bean 정의.
     *
     * @param anotherEntityManagerFactory Another EntityManagerFactory
     * @return Another TransactionManager
     */
    @Bean(name = "anotherTransactionManager")
    public PlatformTransactionManager anotherTransactionManager(
        @Qualifier("anotherEntityManagerFactory") EntityManagerFactory anotherEntityManagerFactory) {
        return new JpaTransactionManager(anotherEntityManagerFactory);
    }
}
