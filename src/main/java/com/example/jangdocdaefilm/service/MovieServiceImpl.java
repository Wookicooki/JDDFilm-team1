package com.example.jangdocdaefilm.service;

import com.example.jangdocdaefilm.dto.*;
import com.example.jangdocdaefilm.mapper.MovieMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

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

    @Override
    public MovieDetailDto getMovieDetail(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        MovieDetailDto movieDetail = gson.fromJson(response.body(), MovieDetailDto.class);
        return movieDetail;
    }

    @Override
    public List<DailyBoxOfficeDto> getDailyBoxOfficeList(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        BoxOfficeDto boxOffice = gson.fromJson(response.body(), BoxOfficeDto.class);
        List<DailyBoxOfficeDto> boxOfficeList = boxOffice.getBoxOfficeResult().getDailyBoxOfficeList();

        return boxOfficeList;
    }

    @Override
    public CreditsDto getCredits(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        CreditsDto credits = gson.fromJson(response.body(), CreditsDto.class);

        return credits;
    }

    @Override
    public int insertRecoms(String title, String content) throws Exception {
        movieMapper.insertRecoms(title, content);
        int idx = movieMapper.lastRecomsIdx();
        return idx;
    }

    @Override
    public void insertRecom(String[] movies, int idx) throws Exception {
        movieMapper.insertRecom(movies, idx);
    }

    @Override
    public List<RecomDto> getRecoms() throws Exception {

        return movieMapper.selectRecoms();
    }

    @Override
    public String setPosterPath(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        PosterDto poster = gson.fromJson(response.body(), PosterDto.class);
        return poster.getPoster_path();
    }

    @Override
    public List<String> getRecomsLike(String userName) throws Exception {

        return movieMapper.selectRecomsLike(userName);
    }

    @Override
    public void deleteRecomsLike(String idx, String userName) throws Exception {
        movieMapper.deleteRecomsLike(idx, userName);
    }

    @Override
    public int isRecomsLike(String idx, String userName) throws Exception {
        return movieMapper.isRecomsLike(idx, userName);
    }

    @Override
    public void insertRecomsLike(String idx, String userName) throws Exception {
        movieMapper.insertRecomsLike(idx, userName);
    }

    @Override
    public void updateRecomsLike(String idx, String userName) throws Exception {
        movieMapper.updateRecomsLike(idx, userName);
    }

    @Override
    public RecomDto selectRecom(int idx) throws Exception {
        return movieMapper.selectRecom(idx);
    }

    @Override
    public List<String> selectMovieIds(int idx) throws Exception {
        return movieMapper.selectMovieIds(idx);
    }

    @Override
    public RecomMovieDto getMovie(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", serviceAuthor)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        RecomMovieDto movie = gson.fromJson(response.body(), RecomMovieDto.class);
        return movie;
    }

    @Override
    public void deleteRecoms(int idx) throws Exception {
        movieMapper.deleteRecomsLikes(idx);
        movieMapper.deleteRecom(idx);
        movieMapper.deleteRecoms(idx);
    }
}
