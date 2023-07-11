package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyMapper {
  //  게시물 전체 목록 출력
  List<FreeDto> myFree(String id) throws Exception;
  List<NowDto> myNow(String id) throws Exception;
  List<DisDto> myDis(String id) throws Exception;
}
