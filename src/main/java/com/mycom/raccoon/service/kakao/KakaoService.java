package com.mycom.raccoon.service.kakao;

import com.mycom.raccoon.entity.ResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface KakaoService {

  /**
   * 카카오 로그인 요청
   * @return String url
   */
  public String getKakaoLogin();

  /**
   * 카카오 로그인 토큰 얻기, 회원가입 및 로그인 처리
   * @param HttpServletRequest request
   * @return ResponseDTO
   */
  public ResponseDTO getKakaoToken(HttpServletRequest request);
}
