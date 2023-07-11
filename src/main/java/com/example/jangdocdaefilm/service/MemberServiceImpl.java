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

  @Override
  public int confirmPw(String id, String pw) throws Exception {
    return memberMapper.confirmPw(id, pw);
  }

  @Override
  public int confirmName(String name) throws Exception {
    return memberMapper.confirmName(name);
  }
}
