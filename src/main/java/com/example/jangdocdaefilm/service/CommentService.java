package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.CommentDto;

import java.util.List;

public interface CommentService {

  //  게시물 전체 목록 출력
  List<CommentDto> freeCommentList() throws Exception;

  //  게시물 등록
  void writeComment(CommentDto comment) throws Exception;

  //  게시물 삭제
  void deleteComment(int idx) throws Exception;

}
