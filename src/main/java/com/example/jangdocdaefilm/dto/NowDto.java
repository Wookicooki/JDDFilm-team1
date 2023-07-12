package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class NowDto {
  private int no;

  private int idx;
  private String title;
  private String content;
  private Date createDt;
  private String mId;
  private String mTitle;
  private String id;
  private String userName;
  private int hitCnt;
}
