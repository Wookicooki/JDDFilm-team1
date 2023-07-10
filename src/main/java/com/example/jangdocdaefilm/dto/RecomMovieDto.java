package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecomMovieDto {

    private List<GenreDto> genres;
    private String id;
    private String original_title;
    private String overview;
    private String poster_path;
    private String status;
    private String tagline;
    private String title;
    private String vote_average;
    private String vote_count;
    private String release_date;

}
