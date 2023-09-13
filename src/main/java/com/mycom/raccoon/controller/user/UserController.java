package com.mycom.raccoon.controller.user;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.User;
import com.mycom.raccoon.service.kakao.KakaoService;
import com.mycom.raccoon.service.naver.NaverService;
import com.mycom.raccoon.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "user")
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class UserController {

  private final UserService userService;

  private final Environment environment;

  private final KakaoService kakaoService;

  private final NaverService naverService;

  /**
   * 로그인 리스트 화면
   */
  @GetMapping("signUpList")
  public String signUpList(HttpServletRequest request, HttpServletResponse response, ModelMap model){

    return "user/signUpList";
  }

  /**
   * 회원가입 페이지 진입
   * @param User user
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return String view
   */
  @GetMapping("signUp")
  public String signUp(User user, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    model.addAttribute("user", user);
    return "user/signUp";
  }

  /**
   * 회원가입(유저 정보 insert)
   * @param User to
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return String
   */
  @PostMapping("signUpPost")
  public String signUpPost(User to, HttpServletRequest request, HttpServletResponse response, ModelMap model){

    //캐시 무효화
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    userService.insertUser(to);
    model.addAttribute("user", to);
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
    String authCode = Util.getRandomNumber(6); // 6자리 인증코드

    //SMS 발송 메소드
    //Util.sendCoolSms(coolsmsKey, coolsmsSecret, "01041850434", celno, "RACCOON [인증번호] : " + authCode);

    //return authCode; // 인증코드 return
    return "000000";
  }

  /**
   * 로그인 페이지 진입
   */
  @GetMapping("login")
  public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    model.addAttribute("user", new User());
    return "user/login";
  }

  /**
   * 아이디, 패스워드로 회원존재여부 조회
   * @param User to
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return ResponseDTO
   */
  @GetMapping("loginAxios")
  @ResponseBody
  public ResponseDTO loginAxios(User to, HttpServletRequest request, HttpServletResponse response, ModelMap model){
    return userService.selectLogin(request, to);
  }

  /**
   * 로그아웃
   */
  @GetMapping("logout")
  public RedirectView logout(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    HttpSession session = request.getSession();

    String redirect_url = "/index";
    if(Util.nvl(session.getAttribute("signup_div")).equals("kakao")){
      redirect_url = kakaoService.getLogout(request); // 카카오 로그아웃
    } else if(Util.nvl(session.getAttribute("signup_div")).equals("naver")){
      naverService.getLogout(request); // 네이버 로그아웃
    } else{
      userService.getLogout(request);
    }

    return new RedirectView(redirect_url);
  }

  /**
   * axios요청 - 아이디
   * @param String userid
   * @return String
   * @throws Exception
   */
  @GetMapping("selectUseridAxios")
  @ResponseBody
  public String selectUseridAxios(String userid, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return userService.selectUseridByUserid(userid);
  }

  /**
   * axios요청 - 아이디
   * @param String userid
   * @return String
   * @throws Exception
   */
  @GetMapping("selectNicknameAxios")
  @ResponseBody
  public String selectNicknameAxios(String nickname, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return userService.selectNicknameByNickname(nickname);
  }

  /**
   * ID찾기 페이지
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return String
   * @throws Exception
   */
  @GetMapping("findId")
  public String findId(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return "user/findID";
  }

  /**
   * PW찾기 페이지
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return String
   * @throws Exception
   */
  @GetMapping("findPw")
  public String findPw(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return "user/findPw";
  }

  /**
   * axios요청 - user 조회
   * @param String celno
   * @return String
   * @throws Exception
   */
  @GetMapping("selectUserList")
  @ResponseBody
  public List<User> selectUserList(String celno, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    return userService.selectUserListByCelno(celno);
  }


  /**
   * userid, celno 정보로 유저 조회
   * @param String userid
   * @param String celno
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @param ModelMap model
   * @return ResponseDTO
   */
  @GetMapping("selectUserByUseridAndCelno")
  @ResponseBody
  public ResponseDTO selectUserByUseridAndCelno(String userid, String celno, HttpServletRequest request, HttpServletResponse response, ModelMap model){
    return userService.selectUserByUseridAndCelno(userid, celno);
  }

  /**
   * 패스워드 변경
   * @param User to
   * @return ResponseDTO
   * @throws Exception
   */
  @PutMapping("modifyPassword")
  @ResponseBody
  public ResponseDTO modifyPassword(@RequestBody User to, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
    //캐시 무효화
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    return userService.updateUser(to);
  }

}
