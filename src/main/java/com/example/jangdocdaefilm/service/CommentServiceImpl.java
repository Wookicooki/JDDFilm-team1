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
  public void freeCommentDelete(int idx) throws Exception {
    commentMapper.freeCommentDelete(idx);
  }


}
