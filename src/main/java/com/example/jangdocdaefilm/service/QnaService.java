package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.QnaDto;
import com.example.jangdocdaefilm.dto.QnaFileDto;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface QnaService {
//  게시물 전체 목록 출력
//  List<QnaDto> selectQnaList() throws Exception;
  Page<QnaDto> selectQnaListNewest(int pageNum) throws Exception;

//  게시물 상세 내용 출력
  QnaDto selectQnaDetail(int idx) throws Exception;

//  게시물 등록
  void writeQna(QnaDto qna, MultipartHttpServletRequest uploadFiles) throws Exception;

//  게시물 사진 detail 페이지에 출력
  List<QnaFileDto> selectQnaFile(int idx) throws Exception;

  //  게시물 내용 수정페이지로 이동
  QnaDto updateQnaView(int idx) throws Exception;

//  게시물 수정
  void updateQna(QnaDto qna, MultipartHttpServletRequest updateFiles) throws Exception;

//  게시물 삭제
  void deleteQna(int idx) throws Exception;

  //  게시물 일괄 삭제
  void qnaMultiDelete(Integer[] idx) throws Exception;
}
