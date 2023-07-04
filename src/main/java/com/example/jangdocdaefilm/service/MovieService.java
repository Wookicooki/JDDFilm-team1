package com.example.jangdocdaefilm.service;


import com.example.jangdocdaefilm.dto.MoviesDto;

public interface MovieService {
    MoviesDto getSearchMovies(String url) throws Exception;
}
