package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.MemberDto;
import com.example.jangdocdaefilm.dto.ReviewDto;

import java.util.List;

public interface MemberService {

//  사용자 정보가 있는지 없는지 확인
  public int isMemberInfo(String id, String pw) throws Exception;

//  사용자 정보 가져오기
  public MemberDto getMemberInfo(String id) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  public int confirmId(String id) throws Exception;

  // 리뷰 등록
  void insertMovieReview(ReviewDto review) throws Exception;

  // 리뷰 조회
  List<ReviewDto> getMovieReviewList(String movieId) throws Exception;

  // 내가 쓴 리뷰 조회
  ReviewDto getMyMovieReview(String movieId, String userId) throws Exception;
}
