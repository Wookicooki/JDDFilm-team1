package com.example.jangdocdaefilm.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetailDto {
  private List<GenreDto> genres;
  private String id;
  private String overview;
  private String poster_path;
  private String release_date;
  private String runtime;
  private String tagline;
  private String title;
  private String vote_average;
}
