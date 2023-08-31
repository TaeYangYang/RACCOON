package com.mycom.raccoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 공통 리포지토리 메소드 사용을 위한 인터페이스
 */
@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {

}
