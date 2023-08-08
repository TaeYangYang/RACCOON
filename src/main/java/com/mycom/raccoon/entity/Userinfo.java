package com.mycom.raccoon.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "USER_TB")

public class Userinfo {

  public Userinfo() {
  }

  @Id
  private String user_id;
  @NotNull
  private String password;
  @NotNull
  private String user_nm;
  private String celno;
  private String address1;
  private String address2;
  private String user_auth;
  private String inpt_dttm;
  private String updt_dttm;
  @NotNull
  private String nickname;

}
