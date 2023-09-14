/*
package com.mycom.raccoon.repository.generic.impl;

import com.mycom.raccoon.repository.generic.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenericRepositoryImpl<T> implements GenericRepository {

  @Qualifier("genericRepositoryImpl")
  private final GenericRepository genericRepository;

  @Override
  public Object save(Object entity) throws Exception {
    return (T) genericRepository.save(entity);
  }

  @Override
  public List<T> findAll() {
    return genericRepository.findAll();
  }

  @Override
  public T findById(Long id) {
    return (T) genericRepository.findById(id);
  }
  @Override
  public void delete(Long id) throws Exception {
    genericRepository.delete(id);
  }
}
*/
