package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.common.NowFileUtils;
import com.example.jangdocdaefilm.dto.BoxOfficeDto;
import com.example.jangdocdaefilm.dto.DailyBoxOfficeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.NowFileDto;
import com.example.jangdocdaefilm.mapper.NowMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class NowServiceImpl implements NowService {

  @Autowired
  private NowMapper nowMapper;
  // common패키지의 NowFileUtils 내려받기
  @Autowired
  private NowFileUtils nowFileUtils;

  @Override
  public Page<NowDto> selectNowListNewest(int pageNum) throws Exception {
    PageHelper.startPage(pageNum, 10);
    return nowMapper.selectNowListNewest();
  }
  @Override
  public Page<NowDto> selectNowListViewed(int pageNum) throws Exception {
    PageHelper.startPage(pageNum, 10);
    return nowMapper.selectNowListViewed();
  }

  @Override
  public NowDto selectNowDetail(int idx) throws Exception {
    nowMapper.updateNowHitCount(idx);
    return nowMapper.selectNowDetail(idx);
  }

  @Override
  public void writeNow(NowDto now, MultipartHttpServletRequest uploadFiles) throws Exception {
    nowMapper.writeNow(now);
    // 파일 업로드 구현
    List<NowFileDto> fileList = nowFileUtils.parseNowFileInfo(now.getIdx(), now.getId(), uploadFiles);
    if (!CollectionUtils.isEmpty(fileList)) {
      nowMapper.insertNowFileList(fileList);
    }
  }

  // 파일 출력
  @Override
  public List<NowFileDto> selectNowFile(int idx) throws Exception {
    return nowMapper.selectNowFile(idx);
  }

  @Override
  public NowDto updateNowView(int idx) throws Exception {
    return nowMapper.updateNowView(idx);
  }

  @Override
  public void updateNow(NowDto now, MultipartHttpServletRequest updateFiles) throws Exception {
    nowMapper.updateNow(now);

    List<NowFileDto> fileList = nowFileUtils.parseNowFileInfo(now.getIdx(), now.getId(), updateFiles);
    int nowIdx = now.getIdx();
    if (!CollectionUtils.isEmpty(fileList)) {
      nowMapper.deleteNowFileList(nowIdx);
      nowMapper.insertNowFileList(fileList);
    }
  }

  @Override
  public void deleteNow(int idx) throws Exception {
    nowMapper.deleteNow(idx);
  }

  @Override
  public void nowMultiDelete(Integer[] idx) throws Exception {
    for (int i=0; i < idx.length; i++) {
      nowMapper.deleteNow(idx[i]);
    }
  }

  @Override
  public void updateNowHtiCount(int idx) throws Exception {

  }

  @Override
  public List<DailyBoxOfficeDto> getDailyBoxOfficeList(String url) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    BoxOfficeDto boxOffice = gson.fromJson(response.body(), BoxOfficeDto.class);
    List<DailyBoxOfficeDto> boxOfficeList = boxOffice.getBoxOfficeResult().getDailyBoxOfficeList();
    return boxOfficeList;
  }
}
