package com.mycom.raccoon.controller;

import com.mycom.raccoon.entity.Userinfo;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
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
public class UserController {

  /**
   * 회원가입 페이지 진입
   * @param userinfo
   * @param request
   * @param response
   * @param model
   * @return String view
   */
  @GetMapping("signUp")
  public String index(Userinfo userinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
    model.addAttribute("userinfo", userinfo);
    return "user/signUp";
  }

  /**
   * 단일 메시지 발송
   */
  @PostMapping("sendAuthSMS")
  public SingleMessageSentResponse sendOne(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    DefaultMessageService messageService = new DefaultMessageService("", "", "");

    Message message = new Message();
    // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
    message.setFrom("010-4185-0434");
    message.setTo("수신번호 입력");
    message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다.");

    SingleMessageSentResponse messageResponse = messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(response);

    return messageResponse;
  }
}
