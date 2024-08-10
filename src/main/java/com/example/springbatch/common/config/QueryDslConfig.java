package com.example.springbatch.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    @PersistenceContext(unitName = "batch")
    private EntityManager batchEntityManager;

    @PersistenceContext(unitName = "band")
    private EntityManager bandEntityManager;

    @Bean
    public JPAQueryFactory batchJpaQueryFactory() {
        return new JPAQueryFactory(batchEntityManager);
    }

    @Bean
    public JPAQueryFactory bandJpaQueryFactory() {
        return new JPAQueryFactory(bandEntityManager);
    }
}
