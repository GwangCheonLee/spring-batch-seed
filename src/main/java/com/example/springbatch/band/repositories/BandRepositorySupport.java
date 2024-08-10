package com.example.springbatch.band.repositories;

import com.example.springbatch.band.entities.Band;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.springbatch.band.entities.QBand.band;


@Repository
public class BandRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public BandRepositorySupport(@Qualifier("bandJpaQueryFactory") JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Band> findAllBand() {
        return jpaQueryFactory.select(band).from(band).fetch();
    }
}
