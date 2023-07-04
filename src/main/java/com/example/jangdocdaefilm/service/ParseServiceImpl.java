package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.BoxOfficeDTO;
import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class ParseServiceImpl implements ParseService {
  @Override
  public List<DailyBoxOfficeDTO> getDailyBoxOfficeList(String strUrl) throws Exception {
    List<DailyBoxOfficeDTO> boxOfficeList = null;
    URL url = null;
    HttpURLConnection urlConn = null;
    BufferedReader reader = null;

    try {
      url = new URL(strUrl);
      urlConn = (HttpURLConnection) url.openConnection();
      urlConn.setRequestMethod("GET");

      reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }

      Gson gson = new Gson();

      BoxOfficeDTO boxOffice = gson.fromJson(sb.toString(), BoxOfficeDTO.class);
      boxOfficeList = boxOffice.getBoxOfficeResult().getDailyBoxOfficeList();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (reader != null) reader.close();
      if (urlConn != null) urlConn.disconnect();
    }
    return boxOfficeList;
  }
}
