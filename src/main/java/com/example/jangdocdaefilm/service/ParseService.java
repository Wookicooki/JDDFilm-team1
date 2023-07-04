package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;

import java.util.List;

public interface ParseService {
  List<DailyBoxOfficeDTO> getDailyBoxOfficeList(String url) throws Exception;
}
