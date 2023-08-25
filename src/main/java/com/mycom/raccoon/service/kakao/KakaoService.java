package com.mycom.raccoon.service.kakao;

import javax.servlet.http.HttpServletRequest;

public interface KakaoService {

  /**
   * 카카오 로그인 요청
   * @return String url
   */
  public String getKakaoLogin();

  /**
   * 카카오 로그인 토큰 얻기
   * @param HttpServletRequest request
   * @return
   */
  String getKakaoToken(HttpServletRequest request);
}
