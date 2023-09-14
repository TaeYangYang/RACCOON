package com.mycom.raccoon.repository.code;

import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends GenericRepository<Code, Integer> {

  public List<Code> findByUpCodeOrderByCode(String code);
}
