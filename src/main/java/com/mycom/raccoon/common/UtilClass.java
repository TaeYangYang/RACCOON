package com.mycom.raccoon.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class UtilClass {

  public static String getUserIp(HttpServletRequest request){
    String ip = "";

    return ip;
  }

  public static String nvl(String args){
    return null;
//    if(args == null){
//      return "";
//    } else if(args.equals("")){
//
//    } else{
//
//    }
  }

  /**
   * 랜덤 난수 생성
   * @param int length // 자릿수
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
        randomNumber += random.nextInt();
      }
      
      return randomNumber;
    }
  }

}
