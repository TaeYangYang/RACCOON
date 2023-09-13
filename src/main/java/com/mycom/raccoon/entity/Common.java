package com.mycom.raccoon.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class Common {

  @Column(name = "inpt_dttm")
  @CreationTimestamp
  private LocalDateTime inptdttm; // 입력일시
  @Column(name = "updt_dttm")
  @UpdateTimestamp
  private LocalDateTime updtdttm; // 수정일시
  @Column(name="inpt_user")
  private String inptuser; // 입력 유저
  @Column(name="updt_user")
  private String updtuser; // 수정 유저

}
