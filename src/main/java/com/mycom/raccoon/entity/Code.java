package com.mycom.raccoon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CODE_TB")
@NoArgsConstructor
@DynamicUpdate
public class Code extends Common{

  @Id
  private String code; // 코드

  @Column(name = "code_nm")
  private String codeNm; // 코드명

  @Column(name = "up_code")
  private String upCode; // 상위코드
}
