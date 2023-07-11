package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

  public int isMemberInfo(@Param("id") String id, @Param("pw") String pw) throws Exception;

  public MemberDto getMemberInfo(@Param("id") String userId) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  void changePassword(String id, String pw) throws Exception;
  void changeUserName(String id, String name) throws Exception;

  public int confirmId(@Param("id") String id) throws Exception;

  public int confirmPw(@Param("id") String id, @Param("pw") String pw) throws Exception;

  public int confirmName(@Param("name") String name) throws Exception;

  void insertMovieReview(ReviewDto review) throws Exception;

  List<ReviewDto> getMovieReviewList(String movieId) throws Exception;

  ReviewDto getMyMovieReview(String movieId, String userId) throws Exception;

  void updateMovieReview(ReviewDto review) throws Exception;
  void deleteMovieReview(int idx) throws Exception;

  // 리뷰 좋아요 +1
  void saveLike(int reviewIdx, String memberId) throws Exception;

  void likeUp(@Param("idx") int reviewIdx) throws Exception;

  ReviewDto getMovieReview(@Param("idx") int reviewIdx) throws Exception;

  // 좋아요 -1
  void removeLike(int reviewIdx, String memberId) throws Exception;
  void likeDown(@Param("idx") int reviewIdx) throws Exception;

  // 좋아요 체크
  int checkLike(int likeIdx, String memberId, int reviewIdx) throws Exception;


  List<ReviewLikesDto> getReviewLike(String memberId) throws Exception;

  // 평균 점수
  String userScoreAvg(String movieId) throws Exception;

  void insertUserScoreAvg(UserScoreDto userScore) throws Exception;

  void updateUserScoreAvg(UserScoreDto userScore) throws Exception;

  void deleteUserScoreAvg(String movieId) throws Exception;

  UserScoreDto getScoreAvgMovie(String movieId) throws Exception;
  List<UserScoreDto> getJangDocDaeChart() throws Exception;

}
