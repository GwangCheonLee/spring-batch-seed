package com.example.springbatchseed.another.repository;

import com.example.springbatchseed.another.entity.Another;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Another 데이터를 관리하는 JPA 레포지토리 인터페이스입니다.
 */
public interface AnotherRepository extends JpaRepository<Another, Long>, AnotherRepositoryCustom {

}
