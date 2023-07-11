package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.*;
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
  public void changeMember(String id, String pw, String name) throws Exception {
    memberMapper.changePassword(id, pw);
    memberMapper.changeUserName(id, name);
  }
  @Override
  public void changePassword(String id, String pw) throws Exception {
    memberMapper.changePassword(id, pw);
  }
  @Override
  public void changeUserName(String id,String name) throws Exception {
    memberMapper.changeUserName(id, name);
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
  @Override
  public int confirmPw(String id, String pw) throws Exception {
    return memberMapper.confirmPw(id, pw);
  }

  @Override
  public int confirmName(String name) throws Exception {
    return memberMapper.confirmName(name);
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

  // 좋아요 +1
  @Override
  public void saveLike(int reviewIdx, String memberId) throws Exception {
    memberMapper.saveLike(reviewIdx, memberId);
    memberMapper.likeUp(reviewIdx);
  }

  @Override
  public ReviewDto getMovieReview(int reviewIdx) throws Exception {
    return memberMapper.getMovieReview(reviewIdx);
  }

  // 좋아요 -1
  @Override
  public void removeLike(int reviewIdx, String memberId) throws Exception {
    memberMapper.removeLike(reviewIdx, memberId);
    memberMapper.likeDown(reviewIdx);
  }

  // 좋아요 체크
  @Override
  public int checkLike(int likeIdx, String memberId, int reviewIdx) throws Exception {
    return memberMapper.checkLike(likeIdx, memberId, reviewIdx);
  }

  @Override
  public void deleteMovieReview(int idx) throws Exception {
    memberMapper.deleteMovieReview(idx);
  }

  @Override
  public List<ReviewLikesDto> getReviewLike(String userId) throws Exception {
    return memberMapper.getReviewLike(userId);
  }


  @Override
  public String userScoreAvg(String movieId) throws Exception {
    return memberMapper.userScoreAvg(movieId);
  }

  @Override
  public void insertUserScoreAvg(UserScoreDto userScore) throws Exception {
    memberMapper.insertUserScoreAvg(userScore);
  }

  @Override
  public void updateUserScoreAvg(UserScoreDto userScore) throws Exception {
    memberMapper.updateUserScoreAvg(userScore);
  }

  @Override
  public void deleteUserScoreAvg(String movieId) throws Exception {
    memberMapper.deleteUserScoreAvg(movieId);
  }

  @Override
  public UserScoreDto getScoreAvgMovie(String movieId) throws Exception {
    return memberMapper.getScoreAvgMovie(movieId);
  }

  @Override
  public List<UserScoreDto> getJangDocDaeChart() throws Exception {
    return memberMapper.getJangDocDaeChart();
  }

}
