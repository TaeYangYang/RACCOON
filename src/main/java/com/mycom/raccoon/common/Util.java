package com.mycom.raccoon.common;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Util {

  public static String getUserIp(HttpServletRequest request){
    String ip = "";

    return ip;
  }

  public static String nvl(String args){
    if(args == null){
      return "";
    } else if(args.length() == 0){
      return "";
    } else{
      return args;
    }
  }

  /**
   * 랜덤 난수 생성
   * @param int 자릿수
   * @return String
   */
  public static String getRandomNumber(int length){
    if(length < 1){
      return "";
    } else{
      String randomNumber = "";
      Random random = new Random(); //랜덤 객체 생성(디폴트 시드값 : 현재시간)
      random.setSeed(System.currentTimeMillis()); //시드값 설정을 따로 할수도 있음

      for(int i = 0 ; i < length ; i++){
        randomNumber = randomNumber + String.valueOf(random.nextInt(9));
      }
      
      return randomNumber;
    }
  }

  /**
   * coolsms 이용하여 sms발송
   * @param key 키
   * @param secret 시크릿 키
   * @param from 발신번호
   * @param to 수신번호
   * @param text 내용
   * @return SingleMessageSentResponse
   */
  public static SingleMessageSentResponse sendCoolSms(String key, String secret, String from, String to, String text){

    DefaultMessageService messageService2 = NurigoApp.INSTANCE.initialize(key, secret, "https://api.coolsms.co.kr");

    Message message = new Message();
    String authCode = Util.getRandomNumber(6);
    // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
    message.setFrom(from); // 발신번호
    message.setTo(to); // 수신번호
    message.setText(text);

    SingleMessageSentResponse singleMessageSentResponse = messageService2.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(singleMessageSentResponse);

    return singleMessageSentResponse;
  }

  /**
   * POST 요청 보내기
   * @param String paramUrl // 요청 url
   * @param Map<String, String> paramMap // 요청 파라미터
   * @return Strin
   */
  public static String postConnection(String paramUrl, Map<String, String> paramMap) throws Exception {
    if(nvl(paramUrl).isEmpty()){
      throw new Exception();
    }

    URL url = new URL(paramUrl); // URL 설정
    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 커넥션

    // 전송 모드 설정
    conn.setDefaultUseCaches(false);
    conn.setDoInput(true); // 서버에서 읽기
    conn.setDoOutput(true); // 서버로 쓰기
    conn.setRequestMethod("POST"); // 요청방식

    // 헤더 설정
    conn.setRequestProperty("content-type", "Content-type: application/x-www-form-urlencoded;charset=utf-8");

    // 값 넘기기
    StringBuffer buffer = new StringBuffer();

    if(!paramMap.isEmpty()){
      Set<String> key = paramMap.keySet();

      for (Object obj : key) {
        String keyName = (String) obj;
        String valueName = paramMap.get(keyName);
        buffer.append("&").append(keyName).append("=").append(valueName);
      }
    }

    OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

    PrintWriter writer = new PrintWriter(outStream);
    writer.write(buffer.toString());
    writer.flush();

    // 응답
    int responseCode = conn.getResponseCode();
    InputStreamReader inputReader = new InputStreamReader(conn.getInputStream(), "UTF-8");
    BufferedReader bufferdReader = new BufferedReader(inputReader);

    StringBuilder stringBuilder = new StringBuilder();
    String str;

    while((str = bufferdReader.readLine()) != null){
      stringBuilder.append(str);
    }
    bufferdReader.close();

    String response = stringBuilder.toString();
    return response;
  }

}
