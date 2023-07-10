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
}
