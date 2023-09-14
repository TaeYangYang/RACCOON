package com.mycom.raccoon.service.code;

import com.mycom.raccoon.entity.Code;

import java.util.List;

public interface CodeService {

  public List<Code> getLowLevelCode(String code);
}
