package com.bitc.SpringBoard_sein.dto;

import lombok.Data;

@Data
public class BoardDto {
  private int boardIdx;
  private String title;
  private String contents;
  private String createId;
  private String createDt;
  private String updateId;
  private String updateDt;
  private int hitCnt;
}
