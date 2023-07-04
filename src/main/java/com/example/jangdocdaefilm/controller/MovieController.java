package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;
import com.example.jangdocdaefilm.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

        MoviesDto movies = movieService.getSearchMovies(serviceUrl + "search/movie?query=" + keyword + "&include_adult=false&language=ko&page=1");
        List<MovieDto> movieList = movies.getResults();

        mv.addObject("movieList", movieList);
        mv.addObject("totalResults", movies.getTotal_results());

        return mv;
    }

}
