package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.NowFileDto;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface NowService {
  //  게시물 전체 목록 출력
  Page<NowDto> selectNowListNewest(int pageNum) throws Exception;
  Page<NowDto> selectNowListViewed(int pageNum) throws Exception;

  //  게시물 상세 내용 출력
  NowDto selectNowDetail(int idx) throws Exception;

  //  게시물 등록
  void writeNow(NowDto now, MultipartHttpServletRequest uploadFiles) throws Exception;

  //  게시물 사진 detail 페이지에 출력
  List<NowFileDto> selectNowFile(int idx) throws Exception;

  //  게시물 내용 수정페이지로 이동
  NowDto updateNowView(int idx) throws Exception;

  //  게시물 수정
  void updateNow(NowDto now, MultipartHttpServletRequest updateFiles) throws Exception;

  //  게시물 삭제
  void deleteNow(int idx) throws Exception;

  //  게시물 일괄 삭제
  void nowMultiDelete(Integer[] idx) throws Exception;

  //  게시물 조회수 카운트
  void updateNowHtiCount(int idx) throws Exception;
  // 현재 상영작 데이터 조회
  List<DailyBoxOfficeDto> getDailyBoxOfficeList(String url) throws Exception;
}
