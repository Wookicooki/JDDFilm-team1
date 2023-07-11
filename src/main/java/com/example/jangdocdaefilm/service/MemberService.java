package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.*;

import java.util.List;

public interface MemberService {

  //  사용자 정보가 있는지 없는지 확인
  public int isMemberInfo(String id, String pw) throws Exception;

  //  사용자 정보 가져오기
  public MemberDto getMemberInfo(String id) throws Exception;


  void changeMember(String id, String pw, String name) throws Exception;

  void changePassword(String id, String pw) throws Exception;

  void changeUserName(String id, String name) throws Exception;

  public int confirmPw(String id, String pw) throws Exception;

  public int confirmName(String name) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  public int confirmId(String id) throws Exception;

  // 리뷰 등록
  void insertMovieReview(ReviewDto review) throws Exception;

  // 리뷰 조회
  List<ReviewDto> getMovieReviewList(String movieId) throws Exception;

  // 내가 쓴 리뷰 조회
  ReviewDto getMyMovieReview(String movieId, String userId) throws Exception;

  // 리뷰 수정
  void updateMovieReview(ReviewDto review) throws Exception;

  // 좋아요 +1
  void saveLike(int reviewIdx, String memberId) throws Exception;

  ReviewDto getMovieReview(int reviewIdx) throws Exception;

  // 좋아요 -1
  void removeLike(int reviewIdx, String memberId) throws Exception;

  int checkLike(int reviewIdx, String memberId) throws Exception;

  void deleteMovieReview(int idx) throws Exception;

  List<ReviewLikesDto> getReviewLike(String userId) throws Exception;
}
