package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.CommentDto;
import com.example.jangdocdaefilm.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentMapper commentMapper;

  @Override
  public List<CommentDto> freeCommentList(int idx) throws Exception {
    return commentMapper.freeCommentList(idx);
  }

  @Override
  public void freeWriteComment(CommentDto comment) throws Exception {
    commentMapper.freeWriteComment(comment);
  }

  // 02개봉작수다 댓글
  @Override
  public List<CommentDto> nowCommentList(int idx) throws Exception {
    return commentMapper.nowCommentList(idx);
  }

  @Override
  public void nowWriteComment(CommentDto comment) throws Exception {
    commentMapper.nowWriteComment(comment);
  }

  @Override
  public List<CommentDto> disCommentList(int idx) throws Exception {
    return commentMapper.disCommentList(idx);
  }

  @Override
  public void disWriteComment(CommentDto comment) throws Exception {
    commentMapper.disWriteComment(comment);
  }


  @Override
  public List<CommentDto> qnaCommentList(int idx) throws Exception {
    return commentMapper.qnaCommentList(idx);
  }

  @Override
  public void qnaWriteComment(CommentDto comment) throws Exception {
    commentMapper.qnaWriteComment(comment);
  }


  @Override
  public void commentDelete(int idx) throws Exception {
    commentMapper.commentDelete(idx);
  }

}
