package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class CommentDto {
  private int idx;
  private String content;
  private String createDt;
  private String id;
  private String userName;
  private int nowIdx;
  private int freeIdx;
  private int qnaIdx;
  private int disIdx;
}
