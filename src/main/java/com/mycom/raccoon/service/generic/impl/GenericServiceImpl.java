package com.mycom.raccoon.service.generic.impl;

import com.mycom.raccoon.repository.GenericRepository;
import com.mycom.raccoon.service.generic.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public class GenericServiceImpl<T> implements GenericService<T> {

  @Autowired
  GenericRepository genericRepository;

  @Override
  public List<T> findAll() {
    return genericRepository.findAll();
  }

  @Override
  public T findById(Long id) {
    return (T) genericRepository.findById(id);
  }

  @Override
  public T save(T entity) throws Exception {
    return (T) genericRepository.save(entity);
  }

  @Override
  public void delete(Long id) throws Exception {
    genericRepository.delete(id);
  }
}
