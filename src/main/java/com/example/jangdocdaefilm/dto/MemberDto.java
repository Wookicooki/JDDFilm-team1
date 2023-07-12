package com.example.jangdocdaefilm.dto;

import lombok.Data;

//  데이터 베이스의 User 테이블의 데이터를 주고 받기 위한 Dto 클래스
@Data
public class MemberDto {
  private String id;
  private String pw;
  private String userName;
  private String grade;
  //  파일정보용 프로필이미지 컬럼 추가
  private String originalFileName;
  private String storedFileName;
}
