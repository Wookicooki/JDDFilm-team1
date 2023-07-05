package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class FreeDto {
  private int idx;
  private String title;
  private String content;
  private String image;
  private String createDt;
  private String updateDt;
  private String id;
  private int hitCnt;
}
