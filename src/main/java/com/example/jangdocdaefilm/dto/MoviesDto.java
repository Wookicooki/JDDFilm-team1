package com.example.jangdocdaefilm.dto;

import lombok.Data;
import java.util.List;

@Data
public class MoviesDto {
    private String page;
    private List<MovieDto> results;
    private String total_pages;
    private String total_results;
}
