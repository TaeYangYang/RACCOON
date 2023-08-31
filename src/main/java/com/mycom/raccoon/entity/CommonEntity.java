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
public class CommonEntity {

  @Column(name = "inpt_dttm")
  @CreationTimestamp
  private LocalDateTime inptdttm; // 입력일시
  @Column(name = "updt_dttm")
  @UpdateTimestamp
  private LocalDateTime updtdttm; // 수정일시
}
