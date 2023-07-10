package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;
import com.example.jangdocdaefilm.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Value("${jangDocDae.tmdb.json.Url}")
    private String serviceUrl;

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ModelAndView searchMovie(@PathVariable String keyword) throws Exception {
        ModelAndView mv = new ModelAndView("movie/searchResult");

        String utf8Keyword = URLEncoder.encode(keyword, "UTF-8");

        MoviesDto movies = movieService.getSearchMovies(serviceUrl + "search/movie?query=" + utf8Keyword + "&include_adult=false&language=ko&page=1");
        List<MovieDto> movieList = movies.getResults();

        mv.addObject("movieList", movieList);
        mv.addObject("totalResults", movies.getTotal_results());

        return mv;
    }

    //페이지 로딩 함수
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ModelAndView categoryPage() throws Exception {
        ModelAndView mv = new ModelAndView("movie/category");
        return mv;
    }


    //카테고리 검색 함수
    @RequestMapping(value = "/category/search", method = RequestMethod.GET)
    public Object categorySearchMovie(@RequestParam("year") String year, @RequestParam("genre") String genre) throws Exception {

        String url = "discover/movie?include_adult=false&include_video=false&language=ko&page=1&sort_by=popularity.desc";
        String yearQuery = year != null ? "&primary_release_year=" + year : "";
        String genreQuery = genre != null ? "&with_genres=" + genre : "";

        MoviesDto movies = movieService.getSearchMovies(serviceUrl + url + yearQuery + genreQuery);
        List<MovieDto> movieList = movies.getResults();
        return movieList;
    }


    @RequestMapping(value = "/recom/set", method = RequestMethod.GET)
    public ModelAndView recomSetPage() throws Exception {
        ModelAndView mv = new ModelAndView("movie/recommendSet");
        return mv;
    }

    @RequestMapping(value = "/recom/search", method = RequestMethod.GET)
    public Object recomSearchMovie(@RequestParam("year") String year, @RequestParam("genre") String genre, @RequestParam("keyword") String keyword) throws Exception {

        String url = serviceUrl;

        if (!keyword.equals("")) {
            String utf8Keyword = URLEncoder.encode(keyword, "UTF-8");
            url += "search/movie?query=" + utf8Keyword + "&include_adult=false&language=ko&page=1";
        } else {
            String searchUrl = "discover/movie?include_adult=false&include_video=false&language=ko&page=1&sort_by=popularity.desc";
            String yearQuery = year != null ? "&primary_release_year=" + year : "";
            String genreQuery = genre != null ? "&with_genres=" + genre : "";
            url += searchUrl + yearQuery + genreQuery;
        }

        MoviesDto movies = movieService.getSearchMovies(url);
        return movies;
    }

    @RequestMapping(value = "/recom/set", method = RequestMethod.POST)
    public void recomInsert(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("movies[]") String[] movies) throws Exception {

//        // 1. recoms 생성
        int idx = movieService.insertRecoms(title, content);
//        // 2. recom 생성
        movieService.insertRecom(movies, idx);
    }

}
