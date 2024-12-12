package com.example.springbatchseed.another.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * AnotherRepositoryImpl은 QueryDSL을 사용하여 커스텀 로직을 구현한 클래스입니다.
 */
@Slf4j
@Repository
public class AnotherRepositoryImpl implements AnotherRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AnotherRepositoryImpl(
        @Qualifier("anotherJpaQueryFactory") JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
