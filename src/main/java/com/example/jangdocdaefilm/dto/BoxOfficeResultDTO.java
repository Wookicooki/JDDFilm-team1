package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.util.List;
@Data
public class BoxOfficeResultDTO {
  private String boxOfficeType;
  private String showRange;
  private List<DailyBoxOfficeDTO> dailyBoxOfficeList;
}
