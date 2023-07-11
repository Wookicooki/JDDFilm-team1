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

  @Override
  public void commentDelete(int idx) throws Exception {
    commentMapper.commentDelete(idx);
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


}
