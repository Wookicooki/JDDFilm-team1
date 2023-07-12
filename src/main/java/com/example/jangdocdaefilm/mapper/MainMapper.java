package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.dto.NowDto;
import com.example.jangdocdaefilm.dto.RecomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    //  게시물 전체 목록 출력
    List<FreeDto> mainFree() throws Exception;

    List<NowDto> mainNow() throws Exception;

    List<DisDto> mainDis() throws Exception;
}
