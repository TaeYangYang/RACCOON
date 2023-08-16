package com.mycom.raccoon.service.impl;

import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.Userinfo;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void insertUserinfo(Userinfo to){
    to.setPassword(passwordEncoder.encode(to.getPassword()));
    userRepository.save(to);
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO selectLogin(HttpServletRequest request, Userinfo to){
    ResponseDTO responseDTO = new ResponseDTO();
    Userinfo userinfo = userRepository.findByUserid(to.getUserid());

    if(userinfo == null || !to.getUserid().equals(userinfo.getUserid()) || !passwordEncoder.matches(to.getPassword(), userinfo.getPassword())){
      responseDTO.setResultVal("UserNull");
    } else{
      setSession(request, userinfo);
      responseDTO.setResultVal("Success");
    }

    return responseDTO;
  }

  @Override
  public void setSession(HttpServletRequest request, Userinfo userinfo) {
    HttpSession session = request.getSession();

    session.setAttribute("userid", userinfo.getUserid()); // 아이디
    session.setAttribute("username", userinfo.getUsername()); // 유저명
    session.setAttribute("celno", userinfo.getCelno()); // 연락처
    session.setAttribute("nickname", userinfo.getNickname()); // 닉네임

  }

  @Override
  public void getLogout(HttpServletRequest request){
    deleteSession(request);
  }

  @Override
  public void deleteSession(HttpServletRequest request){
    HttpSession session = request.getSession();

    session.removeAttribute("userid");
    session.removeAttribute("username");
    session.removeAttribute("celno");
    session.removeAttribute("nickname");
  }

  @Override
  @Transactional(readOnly = true)
  public String selectUserid(String userid) throws Exception{
    if(userid == null || userid.isEmpty()){
      throw new Exception(); // 파라미터가 넘어오지 않는 경우
    }
    Userinfo userinfo = userRepository.findByUserid(userid);
    //아이디 존재하는지 조회해서 리턴
    if(userinfo == null){
      return null;
    } else{
      return userinfo.getUserid();
    }
  }
}
