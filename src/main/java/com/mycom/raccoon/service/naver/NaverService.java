package com.mycom.raccoon.service.naver;

import com.mycom.raccoon.entity.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface NaverService {
  String getNaverLogin();

  @Transactional(rollbackFor = Exception.class)
  ResponseDTO getNaverToken(HttpServletRequest request);

  ResponseDTO getLogout(HttpServletRequest request);
}
