package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;
import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;
import com.example.jangdocdaefilm.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {
  // 영화 진흥원 일일 박스 오피스 api주소, 키(application.properties에서 가져옴)
  @Value("${jang.kobis.json.DailyBoxOfficeResultUrl}")
  private String serviceUrl;
  @Value("${jang.kobis.json.key}")
  private String serviceKey;

  @Value("${jangDocDae.tmdb.json.Url}")
  private String tmdbServiceUrl;

  @Autowired
  private MovieService movieService;

  @RequestMapping("/")
  public String index() throws Exception {
    return "index";
  }

  @ResponseBody
  @RequestMapping(value = "/main", method = RequestMethod.GET)
  public ModelAndView getDailyBoxOfficeProcess() throws Exception {
    /***** 일일 박스오피스 *****/
    // 어제 날짜 계산
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    cal.add(cal.DATE, -1);
    String targetDt = simpleDateFormat.format(cal.getTime());

    ModelAndView mv = new ModelAndView("/main");
    String url = serviceUrl + "?key=" + serviceKey + "&targetDt=" + targetDt;
    List<DailyBoxOfficeDTO> dailyBoxOfficeList = movieService.getDailyBoxOfficeList(url);
    /**************************/

    /***** 일일 박스 오피스의 영화 상세 정보 *****/
    // dailyBoxOfficeList에서 10편의 영화 제목만 가져와 리스트로 저장
    List<String> keywords = dailyBoxOfficeList.stream()
        .map(MapData -> MapData.getMovieNm())
        .toList();

    Iterator<String> it = keywords.iterator();
    List<MovieDto> movieList = new ArrayList<>();
    while (it.hasNext()) {
      String query = it.next();
      String queryEncode = URLEncoder.encode(query, "UTF-8");
      MoviesDto movies = movieService.getSearchMovies(tmdbServiceUrl + "search/movie?query=" + queryEncode + "&include_adult=false&language=ko&page=1");

      List<MovieDto> movieLists = movies.getResults();
      int total = Integer.parseInt(movies.getTotal_results());
      if (total != 1) {
        for (int i = 0; i < movieLists.size(); i++) {
          if (movieLists.get(i).getTitle().equals(query)){
            movieList.add(movieLists.get(i));
          }
        }
      } else {
        movieList.add(movieLists.get(0));
      }
    }
    /*******************************/

    mv.addObject("dailyBoxOfficeDTOList", dailyBoxOfficeList);
    mv.addObject("movieList", movieList);
    return mv;
  }

  @RequestMapping("/login")
  public String login() throws Exception {
    return "login/login";
  }

  @RequestMapping("/signUp")
  public String signUp() throws Exception {
    return "login/signUp";
  }

  @RequestMapping("/category")
  public String category() throws Exception {
    return "movie/category";
  }

  @RequestMapping("/recommend")
  public String recommend() throws Exception {
    return "movie/recommend";
  }

  @RequestMapping("/recommendSet")
  public String recommendSet() throws Exception {
    return "movie/recommendSet";
  }

  @RequestMapping("/myPage")
  public String myPage() throws Exception {
    return "mypage/myPage";
  }

  // 영화 상세 정보 페이지
  @RequestMapping(value = "/movieDetail/{movieId}", method = RequestMethod.GET)
  public ModelAndView movieDetail(@PathVariable("movieId") String movieId) throws Exception {
    ModelAndView mv = new ModelAndView("movie/movieDetail");
    MovieDto movie = movieService.getMovieInfo(tmdbServiceUrl + "movie/" + movieId + "?language=ko");
    mv.addObject("movieInfo", movie);

    return mv;
  }

  @RequestMapping("/movieReview")
  public String movieReview() throws Exception {
    return "movie/movieReview";
  }

  @RequestMapping("searchResult")
  public String searchResult() throws Exception {
    return "movie/searchResult";
  }
}
