package com.mycom.raccoon.service.common.impl;

import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.repository.common.CommonRepository;
import com.mycom.raccoon.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

  @Qualifier("commonRepository")
  private final CommonRepository commonRepository;
  @Override
  public List<Code> codelist(String code) {
    //return commonRepository.findByUpCodeOrderByCode(code);
    return null;
  }
}
