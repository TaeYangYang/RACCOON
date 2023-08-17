package com.mycom.raccoon.controller.user;

import com.mycom.raccoon.common.UtilClass;
import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.Userinfo;
import com.mycom.raccoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "user")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class UserController {

  //private DefaultMessageService messageService;

  private final UserService userService;

  private final Environment environment;

//  @Autowired
//  public UserController(@Value("#{keyPropertiesFactoryBean['coolsms.key']}")String coolsmsKey,
//                        @Value("#{keyPropertiesFactoryBean['coolsms.secret']}")String coolsmsSecret) {
//    // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
//    this.messageService = NurigoApp.INSTANCE.initialize(coolsmsKey, coolsmsSecret, "https://api.coolsms.co.kr");
//  }

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
  public String signUpPost(Userinfo to, HttpServletRequest request, HttpServletResponse response, ModelMap model){
    userService.insertUserinfo(to);
    model.addAttribute("userinfo", to);
    return "user/signUpFinish";
  }

  /**
   * 단일 메시지 발송
   */
  @PostMapping("sendAuthSMS")
  @ResponseBody
  public String sendAuthSMS(@RequestBody String celno, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    String coolsmsKey = environment.getProperty("coolsms.key"); // sms발송 키
    String coolsmsSecret = environment.getProperty("coolsms.secret"); // sms발송 시크릿 키
    String authCode = UtilClass.getRandomNumber(6); // 6자리 인증코드

    //SMS 발송 메소드
    //UtilClass.sendCoolSms(coolsmsKey, coolsmsSecret, "01041850434", celno, "RACCOON [인증번호] : " + authCode);

    //return authCode; // 인증코드 return
    return "000000";
  }

  /**
   * 로그인 페이지 진입
   */
  @GetMapping("login")
  public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    model.addAttribute("userinfo", new Userinfo());
    return "user/login";
  }

  /**
   * 아이디, 패스워드로 회원존재여부 조회
   * @param Userinfo
   * @param request
   * @param response
   * @param model
   * @return ResponseDTO
   */
  @GetMapping("loginAxios")
  @ResponseBody
  public ResponseDTO loginAxios(Userinfo to, HttpServletRequest request, HttpServletResponse response, ModelMap model){
    return userService.selectLogin(request, to);
  }

  /**
   * 로그아웃
   */
  @GetMapping("logout")
  public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    userService.getLogout(request);
    return "/index";
  }

  @GetMapping("selectUseridAxios")
  @ResponseBody
  public String selectUseridAxios(String userid, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return userService.selectUserid(userid);
  }

  @GetMapping("selectNicknameAxios")
  @ResponseBody
  public String selectNicknameAxios(String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return userService.selectNickname(nickname);
  }
}
