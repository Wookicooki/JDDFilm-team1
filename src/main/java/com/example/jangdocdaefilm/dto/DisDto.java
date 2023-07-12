package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class DisDto {
  private int no;

  private int idx;
  private String title;
  private String content;
  private Date createDt;
  private String id;
  private String userName;
  private int hitCnt;
}
