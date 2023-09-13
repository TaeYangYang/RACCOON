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
@Table(name = "SALESBOARD_TB")
@NoArgsConstructor
@DynamicUpdate
@SequenceGenerator(
        name = "SALESBOARD_TB_SEQ_GENERATOR",
        sequenceName = "SALESBOARD_TB_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class SalesBoard extends Common{

  @Id
  @GeneratedValue(
          strategy =GenerationType.SEQUENCE,
          generator = "SALESBOARD_TB_SEQ_GENERATOR"
  ) // 시퀀스
  private Integer id; // pk

  @NotNull
  private String title; // 제목

  private String content; // 내용

  @Column(name = "sale_category")
  private String saleCategoty; // 구매/판매여부

  private String celno; // 연락처
}
