package com.mycom.raccoon.service.generic;

import java.util.List;

/**
 * JPA 리포지트로의 일반적인 기능을 공통적으로 사용하기 위한 서비스 인터페이스
 */
public interface GenericService<T>{
  /**
   * 리스트 전체 조회
   * @return List<T>
   */
  List<T> findAll();

  /**
   * ID(PK) 파라미터로 받아서 한 건 조회
   * @param Long id
   * @return T
   */
  T findById(Long id);

  /**
   * 엔티티 저장
   * @param T entity
   * @return T
   * @throws Exception
   */
  T save(T entity) throws Exception;

  /**
   * 엔티티 삭제
   * @param Long id
   * @throws Exception
   */
  void delete(Long id) throws Exception;
}
