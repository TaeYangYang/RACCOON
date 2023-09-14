package com.mycom.raccoon.repository.common;

import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.repository.generic.GenericRepository;

import java.util.List;

public interface CommonRepository extends GenericRepository {
  public List<Code> findByUpCodeOrderByCode(String code);
}
