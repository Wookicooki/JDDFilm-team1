package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.MemberDto;
import com.example.jangdocdaefilm.dto.ReviewDto;
import com.example.jangdocdaefilm.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberMapper memberMapper;

  @Override
  public int isMemberInfo(String userId, String userPw) throws Exception {
    return memberMapper.isMemberInfo(userId, userPw);
  }

  @Override
  public MemberDto getMemberInfo(String userId) throws Exception {
    return memberMapper.getMemberInfo(userId);
  }

  @Override
  public void signUpMember(MemberDto member) throws Exception {
    memberMapper.signUpMember(member);
  }

  @Override
  public int confirmId(String id) throws Exception {
    return memberMapper.confirmId(id);
  }

  // 리뷰 등록
  @Override
  public void insertMovieReview(ReviewDto review) throws Exception {
    memberMapper.insertMovieReview(review);
  }

  // 해당 영화 모든 리뷰
  @Override
  public List<ReviewDto> getMovieReviewList(String movieId) throws Exception {
    return memberMapper.getMovieReviewList(movieId);
  }

  // 해당 영화 내가 쓴 리뷰
  @Override
  public ReviewDto getMyMovieReview(String movieId, String userId) throws Exception {
    return memberMapper.getMyMovieReview(movieId, userId);
  }

  // 리뷰 수정
  @Override
  public void updateMovieReview(ReviewDto review) throws Exception {
    memberMapper.updateMovieReview(review);
  }

}
