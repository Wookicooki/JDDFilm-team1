package com.example.jangdocdaefilm.dto;

import lombok.Data;

@Data
public class ReviewDto {
  private int idx;
  private String content;
  private String createDt;
  private int likeCnt;
  private int userScore;
  private String userName;
  private String memberId;
  private String movieId;
  private String movieTitle;

  private int reviewLikeIdx;
}
