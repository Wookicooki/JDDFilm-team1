package com.example.jangdocdaefilm.mapper;

import com.example.jangdocdaefilm.dto.RecomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    void insertRecoms(String title, String content) throws Exception;

    void insertRecom(String[] movies, int idx) throws Exception;

    int lastRecomsIdx() throws Exception;

    List<RecomDto> selectRecoms() throws Exception;
}
