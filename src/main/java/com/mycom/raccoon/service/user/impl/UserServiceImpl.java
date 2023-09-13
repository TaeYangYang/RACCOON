package com.mycom.raccoon.service.user.impl;

import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.User;
import com.mycom.raccoon.repository.UserRepository;
import com.mycom.raccoon.service.generic.impl.GenericServiceImpl;
import com.mycom.raccoon.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService{

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertUser(User to){
    to.setPassword(passwordEncoder.encode(to.getPassword()));
    to.setSignupdiv("raccoon"); // 시스템 내에서 회원가입
    try{
      save(to);
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseDTO selectLogin(HttpServletRequest request, User to){
    ResponseDTO responseDTO = new ResponseDTO();
    String signupdiv = "raccoon";
    User user = userRepository.findByUseridAndSignupdiv(to.getUserid(), signupdiv);

    if(user == null || !to.getUserid().equals(user.getUserid()) || !passwordEncoder.matches(to.getPassword(), user.getPassword())){
      responseDTO.setResultVal("UserNull");
    } else{
      setSession(request, user);
      responseDTO.setResultVal("Success");
    }

    return responseDTO;
  }

  @Override
  public void setSession(HttpServletRequest request, User user) {
    HttpSession session = request.getSession();

    session.setAttribute("userid", user.getUserid()); // 아이디
    session.setAttribute("username", user.getUsername()); // 유저명
    session.setAttribute("celno", user.getCelno()); // 연락처
    session.setAttribute("nickname", user.getNickname()); // 닉네임
    session.setAttribute("signup_div", user.getSignupdiv()); // 회원가입경로 구분(카카오, 네이버, 라쿤 등...)
    session.setAttribute("access_token", user.getAccess_token()); // 외부 로그인연동 토큰 값
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
    session.removeAttribute("signup_div");
    session.removeAttribute("access_token");
  }

  @Override
  @Transactional(readOnly = true)
  public String selectUseridByUserid(String userid) throws Exception{
    if(userid == null || userid.isEmpty()){
      throw new Exception(); // 파라미터가 넘어오지 않는 경우
    }
    String signupdiv = "raccoon";
    User user = userRepository.findByUseridAndSignupdiv(userid, signupdiv);
    //아이디 존재하는지 조회해서 리턴
    if(user == null){
      return null;
    } else{
      return user.getUserid();
    }
  }

  @Override
  public String selectNicknameByNickname(String nickname) throws Exception{
    if(nickname == null || nickname.isEmpty()){
      throw new Exception(); // 파라미터가 넘어오지 않는 경우
    }
    User user = userRepository.findByNickname(nickname);
    //아이디 존재하는지 조회해서 리턴
    if(user == null){
      return null;
    } else{
      return user.getNickname();
    }
  }

  @Override
  public List<User> selectUserListByCelno(String celno) {
    return userRepository.findByCelnoOrderByUserid(celno);
  }

  @Override
  public ResponseDTO selectUserByUseridAndCelno(String userid, String celno) {
    User user = userRepository.findByUseridAndCelno(userid, celno);
    ResponseDTO responseDTO = new ResponseDTO();
    if(user == null){
      responseDTO.setResultVal("Null");
    } else{
      responseDTO.setResultVal("NotNull");
    }

    return responseDTO;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseDTO updateUser(User to){
    ResponseDTO responseDTO = new ResponseDTO();
    String signupdiv = "raccoon";
    try{
      User user = userRepository.findByUseridAndSignupdiv(to.getUserid(), signupdiv);
      user.setPassword(passwordEncoder.encode(to.getPassword()));
      userRepository.save(user);
      responseDTO.setResultVal("Success");
    } catch(Exception e){
      e.printStackTrace();
      responseDTO.setResultVal("Fail");
    }

    return responseDTO;
  }
}
