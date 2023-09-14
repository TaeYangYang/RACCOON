package com.mycom.raccoon.service.code.impl;

import com.mycom.raccoon.entity.Code;
import com.mycom.raccoon.repository.code.CodeRepository;
import com.mycom.raccoon.service.code.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

  private final CodeRepository codeRepository;
  @Override
  public List<Code> getLowLevelCode(String code) {
    return codeRepository.findByUpCodeOrderByCode(code);
  }
}
