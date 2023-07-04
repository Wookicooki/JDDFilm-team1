package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Value("${jangDocDae.tmdb.json.Authorization}")
    private String serviceAuthor;

    @Override
    public MoviesDto getSearchMovies(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        MoviesDto movies = gson.fromJson(response.body(), MoviesDto.class);


        return movies;
    }
}
