package com.bitc.SpringBoard_sein.service;

import com.bitc.SpringBoard_sein.dto.BoardDto;

import java.util.List;

// 글 목록
public interface BoardService {
  List<BoardDto> selectBoardList() throws Exception;

  // 글 상세보기
  BoardDto selectBoardDetail(int boardIdx) throws Exception;

  // 글 등록
  void insertBoard(BoardDto board) throws Exception;

  //  글 수정
  void updateBoard(BoardDto board) throws Exception;

  //  글 삭제
  void deleteBoard(int boardIdx) throws Exception;
}
