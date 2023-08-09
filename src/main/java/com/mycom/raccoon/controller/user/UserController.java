package com.mycom.raccoon.controller.user;

import com.mycom.raccoon.common.UtilClass;
import com.mycom.raccoon.entity.Userinfo;
import com.mycom.raccoon.service.UserService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private DefaultMessageService messageService;

  UserService userService;

  @Value("#{keyPropertiesFactoryBean['coolsms.key']}")
  private String coolsmsKey; // sms발송 키
  
  @Value("#{keyPropertiesFactoryBean['coolsms.secret']}")
  private String coolsmsSecret; // sms발송 시크릿 키

  public UserController(@Value("#{keyPropertiesFactoryBean['coolsms.key']}")String coolsmsKey,
                        @Value("#{keyPropertiesFactoryBean['coolsms.secret']}")String coolsmsSecret) {
    // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
    this.messageService = NurigoApp.INSTANCE.initialize(coolsmsKey, coolsmsSecret, "https://api.coolsms.co.kr");
  }

  @GetMapping("signUpList")
  public String selectReg(HttpServletRequest request, HttpServletResponse response, ModelMap model){

    return "user/signUpList";
  }

  /**
   * 회원가입 페이지 진입
   * @param userinfo
   * @param request
   * @param response
   * @param model
   * @return String view
   */
  @GetMapping("signUp")
  public String signUp(Userinfo userinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
    model.addAttribute("userinfo", userinfo);
    return "user/signUp";
  }

  @PostMapping("signUpPost")
  public String signUpPost(Userinfo userinfo, HttpServletRequest request, HttpServletResponse response, ModelMap model){
    //userService
    return "/";
  }

  /**
   * 단일 메시지 발송
   */
  @GetMapping("sendAuthSMS")
  public String sendOne(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    Message message = new Message();
    String authCode = UtilClass.getRandomNumber(6);
    // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
    message.setFrom("01041850434"); // 발신번호
    message.setTo("01084676191"); // 수신번호
    message.setText("RACCOON [인증번호] : " + authCode);

    SingleMessageSentResponse singleMessageSentResponse = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(singleMessageSentResponse);

    return authCode;
  }
}
