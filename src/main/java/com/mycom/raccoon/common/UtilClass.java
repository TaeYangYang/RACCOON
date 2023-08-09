package com.mycom.raccoon.common;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class UtilClass {

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
    String authCode = UtilClass.getRandomNumber(6);
    // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
    message.setFrom(from); // 발신번호
    message.setTo(to); // 수신번호
    message.setText(text);

    SingleMessageSentResponse singleMessageSentResponse = messageService2.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(singleMessageSentResponse);

    return singleMessageSentResponse;
  }

}
