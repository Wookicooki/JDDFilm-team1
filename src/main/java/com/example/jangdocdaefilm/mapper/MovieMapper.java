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

    List<String> selectRecomsLike(String userName) throws Exception;

    void deleteRecomsLike(String idx, String userName) throws Exception;

    int isRecomsLike(String idx, String userName) throws Exception;

    void insertRecomsLike(String idx, String userName) throws Exception;

    void updateRecomsLike(String idx, String userName) throws Exception;

    RecomDto selectRecom(int idx) throws Exception;

    List<String> selectMovieIds(int idx) throws Exception;

    void deleteRecoms(int idx) throws Exception;

    void deleteRecom(int idx) throws Exception;

    void deleteRecomsLikes(int idx) throws Exception;
}
