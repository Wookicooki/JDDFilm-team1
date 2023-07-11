package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.DisFileDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DisMapper {
  //  게시물 전체 목록 출력
//  List<DisDto> selectDisList() throws Exception;
  Page<DisDto> selectDisListNewest() throws Exception;
  Page<DisDto> selectDisListViewed() throws Exception;

  //  게시물 상세 내용 출력
  DisDto selectDisDetail(int idx) throws Exception;

  //  게시물 등록
  void writeDis(DisDto dis) throws Exception;

  //  게시물 내용 수정페이지로 이동
  DisDto updateDisView(int idx) throws Exception;

  //  게시물 수정
  void updateDis(DisDto dis) throws Exception;

  //  게시물 삭제
  void deleteDis(int idx) throws Exception;

  //  게시물 조회수 카운트
  void updateDisHitCount(int idx) throws Exception;

  // 파일 업로드 수정
  void insertDisFileList(List<DisFileDto> fileList) throws Exception;

  void deleteDisFileList(int disIdx) throws Exception;

  List<DisFileDto> selectDisFile(int idx) throws Exception;
}
