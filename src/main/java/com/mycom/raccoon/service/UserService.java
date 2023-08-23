package com.mycom.raccoon.service;

import com.mycom.raccoon.entity.ResponseDTO;
import com.mycom.raccoon.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {


  /**
   * 회원정보 입력
   * @param User to
   */
  public void insertUser(User to);

  /**
   * 회원여부 조회하여 로그인 프로세스 작동
   * @param HttpServletRequest request
   * @param User to
   * @return ResponseDTO
   */
  @Transactional(readOnly = true)
  public ResponseDTO selectLogin(HttpServletRequest request, User to);

  /**
   * 로그인 후 기본 세션 값 설정
   * @param HttpServletRequest request
   * @param User user
   */
  public void setSession(HttpServletRequest request, User user);

  /**
   * 로그아웃 처리
   * @param HttpServletRequest request
   */
  public void getLogout(HttpServletRequest request);

  /**
   * 세션 삭제
   * @param HttpServletRequest request
   */
  public void deleteSession(HttpServletRequest request);

  /**
   * 유저 아이디 조회
   * @param String userid
   * @return String
   * @throws Exception
   */
  @Transactional(readOnly = true)
  public String selectUseridByUserid(String userid) throws Exception;

  /**
   * 닉네임 존재여부 조회
   * @param String nickname
   * @return String
   */
  @Transactional(readOnly = true)
  public String selectNicknameByNickname(String nickname) throws Exception;

  /**
   * User 리스트 조회
   * @param String celno
   * @return List<User>
   */
  @Transactional(readOnly = true)
  public List<User> selectUserListByCelno(String celno);

  /**
   * userid, celno 정보로 유저 조회
   * @param String userid
   * @param String celno
   * @return
   */
  @Transactional(readOnly = true)
  public ResponseDTO selectUserByUseridAndCelno(String userid, String celno);

  /**
   * 패스워드 변경
   * @param User to
   * @return ResponseDTO
   */
  @Transactional(rollbackFor = Exception.class)
  ResponseDTO updateUser(User to);
}
