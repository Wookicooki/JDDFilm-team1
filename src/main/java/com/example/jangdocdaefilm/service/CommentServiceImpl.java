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
  public List<CommentDto> freeCommentList() throws Exception {
    return commentMapper.freeCommentList();
  }

  @Override
  public void writeComment(CommentDto comment) throws Exception {
    commentMapper.writeComment(comment);
  }

  @Override
  public void deleteComment(int idx) throws Exception {
    commentMapper.deleteComment(idx);
  }
}
