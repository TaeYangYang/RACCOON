package com.mycom.raccoon.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "USER_TB")
@NoArgsConstructor
public class Userinfo {

  @Id
  @Column(name = "user_id")
  private String userid;
  @NotNull
  private String password;
  @NotNull
  @Column(name = "user_nm")
  private String username;
  private String celno;
  private String address1;
  private String address2;
  private String user_auth;

  @Column(name = "inpt_dttm", updatable = false)
  private LocalDateTime inptdttm;
  @Column(name = "updt_dttm")
  private LocalDateTime updtdttm;
  @NotNull
  private String nickname;

}
