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

}
