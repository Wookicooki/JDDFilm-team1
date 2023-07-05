package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.FreeDto;

import java.util.List;

public interface FreeService {
//  게시물 전체 목록 출력
  List<FreeDto> selectFreeList() throws Exception;

//  게시물 상세 내용 출력
  FreeDto selectFreeDetail(int idx) throws Exception;

//  게시물 등록
  void writeFree(FreeDto free) throws Exception;

//  게시물 수정
  void updateFree(FreeDto free) throws Exception;

//  게시물 삭제
  void deleteFree(int idx) throws Exception;

  //  게시물 일괄 삭제
  void freeMultiDelete(Integer[] idx) throws Exception;

  //  게시물 조회수 카운트
  void updateFreeHtiCount(int idx) throws Exception;
}
