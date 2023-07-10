package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.DisFileDto;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface DisService {
//  게시물 전체 목록 출력
//  List<DisDto> selectDisList() throws Exception;
  Page<DisDto> selectDisListNewest(int pageNum) throws Exception;
  Page<DisDto> selectDisListViewed(int pageNum) throws Exception;

//  게시물 상세 내용 출력
  DisDto selectDisDetail(int idx) throws Exception;

//  게시물 등록
  void writeDis(DisDto dis, MultipartHttpServletRequest uploadFiles) throws Exception;

//  게시물 사진 detail 페이지에 출력
  List<DisFileDto> selectDisFile(int idx) throws Exception;

  //  게시물 내용 수정페이지로 이동
  DisDto updateDisView(int idx) throws Exception;

//  게시물 수정
  void updateDis(DisDto dis, MultipartHttpServletRequest updateFiles) throws Exception;

//  게시물 삭제
  void deleteDis(int idx) throws Exception;

  //  게시물 일괄 삭제
  void disMultiDelete(Integer[] idx) throws Exception;

  //  게시물 조회수 카운트
  void updateDisHtiCount(int idx) throws Exception;
}
