package com.example.jangdocdaefilm.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {

    void insertRecoms(String title, String content) throws Exception;

    void insertRecom(String[] movies, int idx) throws Exception;

    int lastRecomsIdx() throws Exception;
}
