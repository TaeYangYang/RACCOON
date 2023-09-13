package com.mycom.raccoon.service.kakao.impl;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.User;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.generic.impl.GenericServiceImpl;
import com.mycom.raccoon.service.kakao.KakaoService;
import com.mycom.raccoon.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/properties/key.properties")
public class KakaoServiceImpl extends GenericServiceImpl implements KakaoService {

  private final Environment environment;

  private final UserRepository userRepository;

  private final UserService userService;

  @Override
  public String getKakaoLogin() {
    String url = environment.getProperty("kakao.login.auth.url"); // 카카오 로그인 요청 URL
    String client_id = environment.getProperty("kakao.rest.api"); // 카카오 rest api 키
    String redirect_uri = environment.getProperty("kakao.redirect.uri"); // 카카오 인가코드 전달받을 서비스 서버 URL
    String response_type = "code"; // 고정값

    String resultUrl =  url + "?client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&response_type=" + response_type;
    return resultUrl;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseDTO getKakaoToken(HttpServletRequest request){
    ResponseDTO responseDTO = new ResponseDTO();

    //로그인 요청에서 받아온 파라미터
    String code = Util.nvl(request.getParameter("code")); // 토큰 받기 요청에 필요한 인가 코드
    String error = Util.nvl(request.getParameter("error")); // 인증 실패 시 반환되는 에러 코드
    String error_description = Util.nvl(request.getParameter("error_description")); // 인증 실패 시 반환되는 에러 메시지
    String state = Util.nvl(request.getParameter("state")); //요청 시 전달한 state 값과 동일한 값

    //프로퍼티에서 셋팅값 가져와서 셋팅
    String url = environment.getProperty("kakao.login.token.url"); // 로그인 토큰 획득 url
    String client_id = environment.getProperty("kakao.rest.api"); // 카카오 rest api 키
    String redirect_uri = environment.getProperty("kakao.redirect.uri"); // 카카오 인가코드 전달받을 서비스 서버 URL
    String client_secret = environment.getProperty("kakao.login.client_secret");
    String getUserInfoUrl = environment.getProperty("kakao.token.getUserInfo.url"); // #카카오 토큰으로 유저 정보 가져오기

    //파라미터 셋팅
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("code", code);
    paramMap.put("state", state);
    paramMap.put("client_id", client_id);
    paramMap.put("redirect_url", redirect_uri);
    paramMap.put("grant_type", "authorization_code"); //고정값
    paramMap.put("client_secret", client_secret);

    try{
      String responseStr = Util.postConnection(url, paramMap); // POST요청으로 로그인 토큰 획득

      // JSON 파싱
      if(!Util.nvl(responseStr).isEmpty()){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);
        String access_token = (String) jsonObject.get("access_token");

        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                getUserInfoUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser infoJsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) infoJsonParser.parse(response.getBody());
        JSONObject kakao_account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) kakao_account.get("profile");

        long id = (long) jsonObj.get("id");
        String email = String.valueOf(kakao_account.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));

        // 받아온 정보로 회원가입 및 로그인 구현
        User user = userRepository.findByUseridAndSignupdiv(email, "kakao");
        if(user == null){
          // 회원 정보 없으면 회원가입 처리
          User insertUser = new User();
          insertUser.setUserid(email);
          insertUser.setUsername(nickname);
          insertUser.setSignupdiv("kakao");
          insertUser.setNickname(nickname);
          save(insertUser);
        }

        User sessionUser = userRepository.findByUseridAndSignupdiv(email, "kakao");
        sessionUser.setAccess_token(access_token);
        userService.setSession(request, sessionUser);
      } else{
        // 응답데이터가 없는 경우
        responseDTO.setResultMsg("카카오 통신 에러");
        responseDTO.setResultVal("ERROR");
      }
    } catch(Exception e){
      e.printStackTrace();
    }

    return responseDTO;
  }

  @Override
  public String getLogout(HttpServletRequest request){
    ResponseDTO responseDTO = new ResponseDTO();
    HttpSession session = request.getSession();

    String url = environment.getProperty("kakao.logout.url"); // 로그아웃 요청 url
    String client_id = environment.getProperty("kakao.rest.api"); // 카카오 rest api 키
    String logout_redirect_uri = environment.getProperty("kakao.logout.logout_redirect_uri");

    return url + "?client_id=" + client_id + "&logout_redirect_uri=" + logout_redirect_uri + "&state=raccoon";
  }
}
