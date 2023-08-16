package com.mycom.raccoon.service;

import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.Userinfo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface UserService {


  void insertUserinfo(Userinfo to);


  @Transactional(readOnly = true)
  ResponseDTO selectLogin(HttpServletRequest request, Userinfo to);

  void setSession(HttpServletRequest request, Userinfo userinfo);

  void getLogout(HttpServletRequest request);

  void deleteSession(HttpServletRequest request);

  String selectUserid(String userid);
}
