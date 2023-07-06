package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.CommentDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
  //  게시물 전체 목록 출력
  List<CommentDto> freeCommentList(int idx) throws Exception;

  //  게시물 등록
  void freeWriteComment(CommentDto comment) throws Exception;

  //  게시물 삭제
  void freeCommentDelete(int idx) throws Exception;

}
