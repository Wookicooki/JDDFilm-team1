package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.*;

import java.util.List;

public interface MovieService {
    MoviesDto getSearchMovies(String url) throws Exception;

    MovieDto getMovieInfo(String url) throws Exception;

    List<DailyBoxOfficeDTO> getDailyBoxOfficeList(String url) throws Exception;
}
