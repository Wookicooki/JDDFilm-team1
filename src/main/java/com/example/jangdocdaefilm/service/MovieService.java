package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;

import java.util.List;

public interface MovieService {
    MoviesDto getSearchMovies(String url) throws Exception;

    MovieDto getMovieInfo(String url) throws Exception;
}
