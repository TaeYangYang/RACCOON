package com.mycom.raccoon.repository;

import com.mycom.raccoon.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Userinfo, Integer> {
  /**
   * Userinfo 조회
   * @param String userid
   * @return Userinfo
   */
  public Userinfo findByUserid(String userid);

  /**
   * Userinfo 전체 리스트 조회
   * @return List<Userinfo>
   */
  public List<Userinfo> findAll();

  /**
   * Userinfo 조회
   * @param String nickname
   * @return Userinfo
   */
  public Userinfo findByNickname(String nickname);

  /**
   * Userinfo 리스트 조회
   * @param String celno
   * @return List<Userinfo>
   */
  public List<Userinfo> findByCelnoOrderByUserid(String nickname);

  /**
   * userid, celno 사용해서 Userinfo 조회
   * @param String userid
   * @param String celno
   * @return Userinfo
   */
  public Userinfo findByUseridAndCelno(String userid, String celno);
}
