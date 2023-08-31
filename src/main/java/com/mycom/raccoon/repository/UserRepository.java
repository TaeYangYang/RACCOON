package com.mycom.raccoon.repository;

import com.mycom.raccoon.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User, Integer> {
  /**
   * User 조회
   * @param String userid
   * @return User
   */
  public User findByUserid(String userid);

  /**
   * User 조회
   * @param String nickname
   * @return User
   */
  public User findByNickname(String nickname);

  /**
   * User 리스트 조회
   * @param String celno
   * @return List<User>
   */
  public List<User> findByCelnoOrderByUserid(String nickname);

  /**
   * userid, celno 사용해서 User 조회
   * @param String userid
   * @param String celno
   * @return User
   */
  public User findByUseridAndCelno(String userid, String celno);

  /**
   * userid, signupdiv 이용해서 User 조회
   * @param String userid
   * @param String signupdiv
   * @return User
   */
  public User findByUseridAndSignupdiv(String userid, String signupdiv);
}
