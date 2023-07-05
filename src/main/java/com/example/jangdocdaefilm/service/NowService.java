package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.NowDto;

import java.util.List;

public interface NowService {
//  게시물 전체 목록 출력
  List<NowDto> selectNowList() throws Exception;

//  게시물 상세 내용 출력
  NowDto selectNowDetail(int idx) throws Exception;

//  게시물 등록
  void writeNow(NowDto now) throws Exception;

//  게시물 수정
  void updateNow(NowDto now) throws Exception;

//  게시물 삭제
  void deleteNow(int idx) throws Exception;

//  게시물 조회수 카운트
  void updateNowHtiCount(int idx) throws Exception;
}
