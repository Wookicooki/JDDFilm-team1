package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.util.List;
@Data
public class BoxOfficeResultDto {
  private String boxOfficeType;
  private String showRange;
  private List<DailyBoxOfficeDto> dailyBoxOfficeList;
}
