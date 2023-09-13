package com.mycom.raccoon.controller.naver;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.service.naver.NaverService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "naver")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class NaverController {

  private final NaverService naverService;

  private final Environment environment;

  /**
   * 네이버 로그인 페이지 이동
   * @return String naverURL
   * @throws Exception
   */
  @GetMapping("login")
  public RedirectView signUpNaver() throws Exception{
    return new RedirectView(naverService.getNaverLogin());
  }

  /**
   * 네이버 로그인 후 응답 콜백 URL
   * @return
   * @throws Exception
   */
  @GetMapping("callback")
  public RedirectView callback(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception{
    ResponseDTO responseDTO = naverService.getNaverToken(request);
    redirectAttributes.addAttribute("resultVal", responseDTO.getResultVal());
    redirectAttributes.addAttribute("resultMsg", responseDTO.getResultMsg());
    if(Util.nvl(responseDTO.getResultVal()).isEmpty()){
      return new RedirectView("/");
    } else{
      return new RedirectView("/common/response?resultVal=" + responseDTO.getResultVal() + "&resultMsg=" + responseDTO.getResultMsg());
    }
  }

}
