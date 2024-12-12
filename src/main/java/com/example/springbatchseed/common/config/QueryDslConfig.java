package com.example.springbatchseed.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 설정 클래스입니다.
 * <p>
 * Batch와 Another 데이터 소스에 대해 각각의 JPAQueryFactory를 제공합니다.
 */
@Configuration
public class QueryDslConfig {

    @PersistenceContext(unitName = "batch")
    private EntityManager batchEntityManager;

    @PersistenceContext(unitName = "another")
    private EntityManager anotherEntityManager;

    /**
     * Batch 데이터 소스의 JPAQueryFactory Bean 정의.
     *
     * @return Batch JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory batchJpaQueryFactory() {
        return new JPAQueryFactory(batchEntityManager);
    }

    /**
     * Another 데이터 소스의 JPAQueryFactory Bean 정의.
     *
     * @return Another JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory anotherJpaQueryFactory() {
        return new JPAQueryFactory(anotherEntityManager);
    }
}
