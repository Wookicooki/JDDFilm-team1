package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.MemberDto;
import org.apache.ibatis.annotations.Param;

public interface MemberService {

//  사용자 정보가 있는지 없는지 확인
  public int isMemberInfo(String id, String pw) throws Exception;

//  사용자 정보 가져오기
  public MemberDto getMemberInfo(String id) throws Exception;

  void signUpMember(MemberDto member) throws Exception;

  public int confirmId(String id) throws Exception;
}
