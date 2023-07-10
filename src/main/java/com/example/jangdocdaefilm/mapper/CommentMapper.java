package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.CommentDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
//  자유게시판
  List<CommentDto> freeCommentList(int idx) throws Exception;

  //  게시물 등록
  void freeWriteComment(CommentDto comment) throws Exception;

  //  게시물 삭제
  void freeCommentDelete(int idx) throws Exception;

//  할인정보 게시판
  //  게시물 전체 목록 출력
  List<CommentDto> disCommentList(int idx) throws Exception;

  //  게시물 등록
  void disWriteComment(CommentDto comment) throws Exception;

  //  게시물 삭제
  void disCommentDelete(int idx) throws Exception;


}
