package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.MemberDto;
import com.example.jangdocdaefilm.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
