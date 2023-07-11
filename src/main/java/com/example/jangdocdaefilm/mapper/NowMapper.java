package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.NowFileDto;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NowMapper {
  //  게시물 전체 목록 출력
  Page<NowDto> selectNowListNewest() throws Exception;
  Page<NowDto> selectNowListViewed() throws Exception;

  //  게시물 상세 내용 출력
  NowDto selectNowDetail(int idx) throws Exception;

  //  게시물 등록
  void writeNow(NowDto now) throws Exception;

  //  게시물 내용 수정페이지로 이동
  NowDto updateNowView(int idx) throws Exception;

  //  게시물 수정
  void updateNow(NowDto now) throws Exception;

  //  게시물 삭제
  void deleteNow(int idx) throws Exception;

  //  게시물 조회수 카운트
  void updateNowHitCount(int idx) throws Exception;

  // 파일 업로드 수정
  void insertNowFileList(List<NowFileDto> fileList) throws Exception;

  void deleteNowFileList(int nowIdx) throws Exception;

  List<NowFileDto> selectNowFile(int idx) throws Exception;
}
