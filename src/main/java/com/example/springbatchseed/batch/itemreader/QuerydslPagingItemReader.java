package com.example.springbatchseed.batch.itemreader;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import lombok.Setter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 * Querydsl 기반의 Spring Batch 페이징 ItemReader 구현체입니다.
 *
 * @param <T> 읽어올 데이터의 타입
 */
public class QuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

    /**
     * JPA 속성을 설정하기 위한 맵입니다.
     */
    protected final Map<String, Object> jpaPropertyMap = new HashMap<>();

    /**
     * EntityManagerFactory를 통해 EntityManager를 생성합니다.
     */
    protected EntityManagerFactory entityManagerFactory;

    /**
     * 데이터베이스 작업을 수행할 EntityManager입니다.
     */
    protected EntityManager entityManager;

    /**
     * Querydsl JPAQuery를 생성하는 함수입니다.
     */
    protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;

    /**
     * 트랜잭션 활성화 여부를 설정합니다. 기본값은 false입니다.
     */
    @Setter
    protected boolean transacted = false; // default value

    /**
     * 기본 생성자. Reader 이름을 클래스 이름으로 설정합니다.
     */
    protected QuerydslPagingItemReader() {
        setName(ClassUtils.getShortName(QuerydslPagingItemReader.class));
    }

    /**
     * QuerydslPagingItemReader 생성자.
     *
     * @param entityManagerFactory EntityManagerFactory 객체
     * @param pageSize             페이징 단위 크기
     * @param queryFunction        Querydsl 쿼리를 생성하는 함수
     */
    public QuerydslPagingItemReader(EntityManagerFactory entityManagerFactory,
        int pageSize,
        Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this(entityManagerFactory, pageSize, true, queryFunction);
    }

    /**
     * QuerydslPagingItemReader 생성자 (트랜잭션 설정 가능).
     *
     * @param entityManagerFactory EntityManagerFactory 객체
     * @param pageSize             페이징 단위 크기
     * @param transacted           트랜잭션 활성화 여부
     * @param queryFunction        Querydsl 쿼리를 생성하는 함수
     */
    public QuerydslPagingItemReader(EntityManagerFactory entityManagerFactory,
        int pageSize,
        boolean transacted,
        Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this();
        this.entityManagerFactory = entityManagerFactory;
        this.queryFunction = queryFunction;
        setPageSize(pageSize);
        setTransacted(transacted);
    }

    /**
     * Reader를 열고 EntityManager를 초기화합니다.
     *
     * @throws Exception Reader 열기 실패 시 예외 발생
     */
    @Override
    protected void doOpen() throws Exception {
        super.doOpen();
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    }

    /**
     * 현재 페이지의 데이터를 읽어옵니다.
     */
    @Override
    protected void doReadPage() {
        EntityTransaction tx = getTxOrNull();

        JPQLQuery<T> query = createQuery()
            .offset((long) getPage() * getPageSize())
            .limit(getPageSize());

        initResults();

        fetchQuery(query, tx);
    }

    /**
     * 트랜잭션이 활성화된 경우 트랜잭션을 반환합니다.
     *
     * @return EntityTransaction 객체 또는 null
     */
    protected EntityTransaction getTxOrNull() {
        if (transacted) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();

            entityManager.flush();
            entityManager.clear();
            return tx;
        }

        return null;
    }

    /**
     * Querydsl JPAQuery 객체를 생성합니다.
     *
     * @return JPAQuery 객체
     */
    protected JPAQuery<T> createQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFunction.apply(queryFactory);
    }

    /**
     * 결과를 저장할 리스트를 초기화합니다.
     */
    protected void initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
    }

    /**
     * 쿼리를 실행하고 결과를 처리합니다.
     *
     * @param query 실행할 Querydsl 쿼리
     * @param tx    활성화된 트랜잭션 (있을 경우)
     */
    protected void fetchQuery(JPQLQuery<T> query, EntityTransaction tx) {
        if (transacted) {
            results.addAll(query.fetch());
            if (tx != null) {
                tx.commit();
            }
        } else {
            List<T> queryResult = query.fetch();
            for (T entity : queryResult) {
                entityManager.detach(entity);
                results.add(entity);
            }
        }
    }

    /**
     * Reader를 닫고 EntityManager를 종료합니다.
     *
     * @throws Exception Reader 닫기 실패 시 예외 발생
     */
    @Override
    protected void doClose() throws Exception {
        entityManager.close();
        super.doClose();
    }
}
