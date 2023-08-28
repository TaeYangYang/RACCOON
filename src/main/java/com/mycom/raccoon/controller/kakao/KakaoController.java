package com.mycom.raccoon.controller.kakao;

import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.service.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "kakao")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class KakaoController {

  private final KakaoService kakaoService;

  private final Environment environment;

  /**
   * 카카오 로그인 페이지 이동
   * @return String kakaoURL
   * @throws Exception
   */
  @GetMapping("login")
  public RedirectView signUpKakao() throws Exception{
    return new RedirectView(kakaoService.getKakaoLogin());
  }

  /**
   * 카카오 로그인 후 응답 콜백 URL
   * @return
   * @throws Exception
   */
  @GetMapping("callback")
  public ResponseDTO callback(HttpServletRequest request, HttpServletResponse response) throws Exception{
    kakaoService.getKakaoToken(request);


    return null;
  }

}
