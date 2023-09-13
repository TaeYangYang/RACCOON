package com.mycom.raccoon.service.naver.impl;

import com.mycom.raccoon.common.Util;
import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.User;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.generic.impl.GenericServiceImpl;
import com.mycom.raccoon.service.naver.NaverService;
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
public class NaverServiceImpl extends GenericServiceImpl implements NaverService {

  private final Environment environment;

  private final UserRepository userRepository;

  private final UserService userService;

  @Override
  public String getNaverLogin() {
    String url = environment.getProperty("naver.login.auth.url"); // 네이버 로그인 요청 URL
    String client_id = environment.getProperty("naver.client.id"); // naver client_id 값
    String redirect_uri = environment.getProperty("naver.redirect.uri"); // 네이버 콜백 URL
    String state = environment.getProperty("naver.login.state"); // 사이트 간 요청 위조(cross-site request forgery) 공격을 방지하기 위해 애플리케이션에서 생성한 상태 토큰값으로 URL 인코딩을 적용한 값을 사용
    String response_type = "code"; // 고정값

    String resultUrl =  url + "?client_id=" + client_id + "&redirect_uri=" + redirect_uri + "&state=" + state + "&response_type=" + response_type;
    return resultUrl;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseDTO getNaverToken(HttpServletRequest request){
    ResponseDTO responseDTO = new ResponseDTO();

    //로그인 요청에서 받아온 파라미터
    String code = Util.nvl(request.getParameter("code")); // 토큰 받기 요청에 필요한 인가 코드
    String error = Util.nvl(request.getParameter("error")); // 인증 실패 시 반환되는 에러 코드
    String error_description = Util.nvl(request.getParameter("error_description")); // 인증 실패 시 반환되는 에러 메시지
    String state = Util.nvl(request.getParameter("state")); //요청 시 전달한 state 값과 동일한 값

    //프로퍼티에서 셋팅값 가져와서 셋팅
    String url = environment.getProperty("naver.token.url"); // 로그인 토큰 획득 url
    String client_id = environment.getProperty("naver.client.id"); // 네이버 client id 키
    String client_secret = environment.getProperty("naver.client.secret");
    String getUserInfoUrl = environment.getProperty("naver.token.getUserInfo.url"); // 네이버 토큰으로 유저 정보 가져오기

    //기타 파라미터 셋팅
    String grant_type = "authorization_code"; // 인증 과정에 대한 구분값 1) 발급:'authorization_code'    2) 갱신:'refresh_token'    3) 삭제: 'delete'

    //파라미터 셋팅
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("code", code);
    paramMap.put("state", state);
    paramMap.put("client_id", client_id);
    paramMap.put("client_secret", client_secret);
    paramMap.put("grant_type", grant_type);

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
        JSONObject responseObj = (JSONObject) jsonObj.get("response");

        String id = String.valueOf(responseObj.get("id"));
        String email = String.valueOf(responseObj.get("email"));
        String nickname = String.valueOf(responseObj.get("nickname"));
        String name = String.valueOf(responseObj.get("name"));

        // 받아온 정보로 회원가입 및 로그인 구현
        User user = userRepository.findByUseridAndSignupdiv(id, "naver");
        if(user == null){
          // 회원 정보 없으면 회원가입 처리
          User insertUser = new User();
          insertUser.setUserid(id);
          insertUser.setUsername(name);
          insertUser.setSignupdiv("naver");
          insertUser.setNickname(nickname);
          save(insertUser);
        }

        User sessionUser = userRepository.findByUseridAndSignupdiv(id, "naver");
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
  public ResponseDTO getLogout(HttpServletRequest request){
    ResponseDTO responseDTO = new ResponseDTO();
    HttpSession session = request.getSession();
    String url = environment.getProperty("naver.token.url"); // 로그아웃 요청 url
    String client_id = environment.getProperty("naver.client.id"); // 네이버 client id 키
    String client_secret = environment.getProperty("naver.client.secret");
    String access_token = (String) session.getAttribute("access_token");
    String grant_type = "delete"; // 로그아웃 : delete

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("client_id", client_id);
    paramMap.put("client_secret", client_secret);
    paramMap.put("access_token", access_token);
    paramMap.put("grant_type", grant_type);
    paramMap.put("service_provider", "NAVER");

    String result = "";

    try{
      String responseStr = Util.postConnection(url, paramMap); // POST요청으로 로그아웃처리

      if(!Util.nvl(responseStr).isEmpty()){
        // JSON 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);
        result = (String) jsonObject.get("result");
      }

      if(Util.nvl(result).equals("success")){
        userService.getLogout(request);
        responseDTO.setResultVal("Success");
      } else{
        responseDTO.setResultVal("Fail");
      }
    } catch(Exception e){
      e.printStackTrace();
      responseDTO.setResultVal("Fail");
    }
    return responseDTO;
  }
}
