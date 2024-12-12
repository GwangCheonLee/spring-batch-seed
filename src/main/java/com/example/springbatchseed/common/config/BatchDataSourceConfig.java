package com.example.springbatchseed.common.config;

import com.example.springbatchseed.common.property.BatchDataSourceProperties;
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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Batch 데이터 소스와 관련된 JPA 설정 클래스입니다.
 * <p>
 * Batch 데이터베이스에 대한 DataSource, EntityManagerFactory, TransactionManager를 설정합니다.
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.springbatchseed.batch",
    entityManagerFactoryRef = "batchEntityManagerFactory",
    transactionManagerRef = "batchTransactionManager"
)
@RequiredArgsConstructor
public class BatchDataSourceConfig {

    private final BatchDataSourceProperties properties;

    /**
     * Batch 데이터 소스 Bean 정의.
     *
     * @return Batch 데이터 소스
     */
    @Primary
    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create()
            .driverClassName(properties.getDriverClassName())
            .url(properties.getUrl())
            .username(properties.getUsername())
            .password(properties.getPassword())
            .build();
    }

    /**
     * Batch EntityManagerFactory Bean 정의.
     *
     * @param builder    EntityManagerFactoryBuilder
     * @param dataSource Batch 데이터 소스
     * @return Batch EntityManagerFactory
     */
    @Primary
    @Bean(name = "batchEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("batchDataSource") DataSource dataSource) {

        // Hibernate 설정 추가
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "validate");

        return builder
            .dataSource(dataSource)
            .packages("com.example.springbatchseed.batch")
            .persistenceUnit("batch")
            .properties(jpaProperties)
            .build();
    }

    /**
     * Batch TransactionManager Bean 정의.
     *
     * @param batchEntityManagerFactory Batch EntityManagerFactory
     * @return Batch TransactionManager
     */
    @Primary
    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager batchTransactionManager(
        @Qualifier("batchEntityManagerFactory") EntityManagerFactory batchEntityManagerFactory) {
        return new JpaTransactionManager(batchEntityManagerFactory);
    }
}
