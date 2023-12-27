package com.mycom.raccoon.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER_TB")
@NoArgsConstructor
@DynamicUpdate
public class User extends Common {

  @Id
  @GeneratedValue(
          strategy = GenerationType.IDENTITY
  )  //시퀀스 생성기 선택
  private Integer id;

  @Column(name = "user_id")
  private String userid; // 아이디

  private String password; // 패스워드

  @NotNull
  @Column(name = "user_nm")
  private String username; // 사용자명

  private String celno; // 연락처

  private String address1; // 주소

  private String address2; // 상세주소

  @Column(name = "user_auth")
  private String userAuth; // 유저권한

  @NotNull
  private String nickname; // 닉네임

  @NotNull
  @Column(name = "signup_div")
  private String signupdiv; // 회원가입 구부(시스템 내 회원, 카카오, 네이버 ...)

  @Transient
  private String access_token; // 외부 로그인 토큰 값

}
