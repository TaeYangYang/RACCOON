package com.mycom.raccoon.service.kakao.impl;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.service.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class KakaoServiceImpl implements KakaoService {

  private final Environment environment;

  @Override
  public String getKakaoLogin() {
    String url = environment.getProperty("kakao.login.auth.url");
    String client_id = environment.getProperty("kakao.rest.api"); // 카카오 rest api 키
    String redirect_uri = environment.getProperty("kakao.redirect.url"); // 카카오 인가코드 전달받을 서비스 서버 URL
    String response_type = "code"; // 고정값

    return url + "?client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&response_type=" + response_type;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getKakaoToken(HttpServletRequest request){

    //로그인 요청에서 받아온 파라미터
    String code = Util.nvl(request.getParameter("code")); // 토큰 받기 요청에 필요한 인가 코드
    String error = Util.nvl(request.getParameter("error")); // 인증 실패 시 반환되는 에러 코드
    String error_description = Util.nvl(request.getParameter("error_description")); // 인증 실패 시 반환되는 에러 메시지
    String state = Util.nvl(request.getParameter("state")); //요청 시 전달한 state 값과 동일한 값
    
    //프로퍼티에서 셋팅값 가져와서 셋팅
    String url = environment.getProperty("kakao.login.token.url"); // 로그인 토큰 획득 url
    String client_id = environment.getProperty("kakao.rest.api"); // 카카오 rest api 키
    String redirect_uri = environment.getProperty("kakao.redirect.url"); // 카카오 인가코드 전달받을 서비스 서버 URL

    //파라미터 셋팅
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("code", code);
    paramMap.put("state", state);
    paramMap.put("client_id", client_id);
    paramMap.put("redirect_url", redirect_uri);
    paramMap.put("grant_type", "authorization_code"); //고정값

    try{
      String response = Util.postConnection(url, paramMap);
    } catch(Exception e){

    }

    return null;
  }
}
