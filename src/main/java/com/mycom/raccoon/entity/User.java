package com.mycom.raccoon.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "USER_TB")
@NoArgsConstructor
@DynamicUpdate
public class User {

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

  @Column(name = "inpt_dttm")
  @CreationTimestamp
  private LocalDateTime inptdttm;
  @Column(name = "updt_dttm")
  @UpdateTimestamp
  private LocalDateTime updtdttm;
  @NotNull
  private String nickname;

}
