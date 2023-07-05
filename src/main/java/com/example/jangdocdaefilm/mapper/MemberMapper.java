package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.MemberDto;
import com.example.jangdocdaefilm.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

  public int isMemberInfo(@Param("id") String id, @Param("pw") String pw) throws Exception;

  public MemberDto getMemberInfo(@Param("id") String userId) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  public int confirmId(@Param("id") String id) throws Exception;

  void insertMovieReview(ReviewDto review) throws Exception;

  List<ReviewDto> getMovieReviewList(String movieId) throws Exception;

  ReviewDto getMyMovieReview(String movieId, String userId) throws Exception;
}
