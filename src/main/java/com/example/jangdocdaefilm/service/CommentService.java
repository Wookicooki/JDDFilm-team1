package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.CommentDto;

import java.util.List;

public interface CommentService {

//  자유게시판
  //  게시물 전체 목록 출력
  List<CommentDto> freeCommentList(int idx) throws Exception;

  //  게시물 등록
  void freeWriteComment(CommentDto comment) throws Exception;

  //  02개봉작수다게시판
  //  게시물 전체 목록 출력
  List<CommentDto> nowCommentList(int idx) throws Exception;

  //  게시물 등록
  void nowWriteComment(CommentDto comment) throws Exception;

  //  문의글 게시판
  //  게시물 전체 목록 출력
  List<CommentDto> qnaCommentList(int idx) throws Exception;

  //  게시물 등록
  void qnaWriteComment(CommentDto comment) throws Exception;

//  할인정보게시판
  //  게시물 전체 목록 출력
  List<CommentDto> disCommentList(int idx) throws Exception;

  //  게시물 등록
  void disWriteComment(CommentDto comment) throws Exception;

//  전체 댓글 삭제
  void commentDelete(int idx) throws Exception;

}
