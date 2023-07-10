package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.*;

import java.util.List;

public interface MovieService {
    MoviesDto getSearchMovies(String url) throws Exception;

    MovieDetailDto getMovieDetail(String url) throws Exception;

    List<DailyBoxOfficeDto> getDailyBoxOfficeList(String url) throws Exception;

    CreditsDto getCredits(String url) throws Exception;

    int insertRecoms(String title, String content) throws Exception;

    void insertRecom(String[] movies, int idx) throws Exception;

    List<RecomDto> getRecoms() throws Exception;

    String setPosterPath(String url) throws Exception;

    List<String> getRecomsLike(String userName) throws Exception;

    void deleteRecomsLike(String idx, String userName) throws Exception;

    int isRecomsLike(String idx, String userName) throws Exception;

    void insertRecomsLike(String idx, String userName) throws Exception;

    void updateRecomsLike(String idx, String userName) throws Exception;

    RecomDto selectRecom(int idx) throws Exception;

    List<String> selectMovieIds(int idx) throws Exception;

    RecomMovieDto getMovie(String url) throws Exception;
}
