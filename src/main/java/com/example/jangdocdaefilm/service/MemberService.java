package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.*;

import java.util.List;

public interface MemberService {

  //  사용자 정보가 있는지 없는지 확인
  public int isMemberInfo(String id, String pw) throws Exception;

  //  사용자 정보 가져오기
  public MemberDto getMemberInfo(String id) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  void changeMember(String id, String pw, String name) throws Exception;

  void changePassword(String id, String pw) throws Exception;

  void changeUserName(String id, String name) throws Exception;

  public int confirmId(String id) throws Exception;
  public int confirmPw(String id, String pw) throws Exception;

  public int confirmName(String name) throws Exception;
}
