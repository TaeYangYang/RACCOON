package com.mycom.raccoon.repository;

import com.mycom.raccoon.entity.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Userinfo, Integer> {
  public Userinfo findByUserid(String userid);

  public List<Userinfo> findAll();

  public Userinfo findByNickname(String nickname);
}
