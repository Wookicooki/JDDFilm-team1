package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.QnaDto;
import com.example.jangdocdaefilm.dto.QnaFileDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {
  //  게시물 전체 목록 출력
//  List<QnaDto> selectQnaList() throws Exception;
  Page<QnaDto> selectQnaListNewest() throws Exception;

  //  게시물 상세 내용 출력
  QnaDto selectQnaDetail(int idx) throws Exception;

  //  게시물 등록
  void writeQna(QnaDto qna) throws Exception;

  //  게시물 내용 수정페이지로 이동
  QnaDto updateQnaView(int idx) throws Exception;

  //  게시물 수정
  void updateQna(QnaDto qna) throws Exception;

  //  게시물 삭제
  void deleteQna(int idx) throws Exception;

  // 파일 업로드 수정
  void insertQnaFileList(List<QnaFileDto> fileList) throws Exception;

  void deleteQnaFileList(int qnaIdx) throws Exception;

  List<QnaFileDto> selectQnaFile(int idx) throws Exception;
}
