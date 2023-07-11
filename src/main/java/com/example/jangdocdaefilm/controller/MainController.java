package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.*;

import com.example.jangdocdaefilm.service.MovieService;

import com.example.jangdocdaefilm.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

import static java.lang.System.out;

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
  public ModelAndView getDailyBoxOfficeProcess(HttpServletRequest req) throws Exception {
    /***** 일일 박스오피스 *****/
    // 어제 날짜 계산
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    cal.add(cal.DATE, -1);
    String targetDt = simpleDateFormat.format(cal.getTime());

    ModelAndView mv = new ModelAndView("/main");
    String url = serviceUrl + "?key=" + serviceKey + "&targetDt=" + targetDt;
    List<DailyBoxOfficeDto> dailyBoxOfficeList = movieService.getDailyBoxOfficeList(url);
    /**************************/

    /***** 일일 박스 오피스의 영화 상세 정보 tmdb 검색 api *****/
    // 수정
//    Iterator<DailyBoxOfficeDto> it = dailyBoxOfficeList.iterator();
//    List<MovieDto> movieList = new ArrayList<>();
//    while (it.hasNext()) {
//      DailyBoxOfficeDto dailyBoxOffice = it.next();
//      String query = dailyBoxOffice.getMovieNm();
//      String openDt = dailyBoxOffice.getOpenDt();
//      String queryEncode = URLEncoder.encode(query, "UTF-8");
//      MoviesDto movies = movieService.getSearchMovies(tmdbServiceUrl + "search/movie?query=" + queryEncode + "&include_adult=false&language=ko&page=1&region=KR");
//
//      List<MovieDto> movieLists = movies.getResults();
//      int total = Integer.parseInt(movies.getTotal_results());
//      if (total != 1) {
//        for (int i = 0; i < movieLists.size(); i++) {
//          if (movieLists.get(i).getTitle().equals(query)) {
//            movieList.add(movieLists.get(i));
//          }
//        }
//      } else {
//        movieList.add(movieLists.get(0));
//      }
//    }

    // tmdb 영화 인기순

    List<MovieDto> movieList = movieService.getSearchMovies("https://api.themoviedb.org/3/movie/popular?language=ko&page=1&region=KR").getResults();

    mv.addObject("dailyBoxOfficeList", dailyBoxOfficeList);
    mv.addObject("movieList", movieList);

    HttpSession session = req.getSession();

    MemberDto member = new MemberDto();
    member.setId((String) session.getAttribute("id"));
    member.setUserName((String) session.getAttribute("userName"));
    member.setGrade((String) session.getAttribute("grade"));

    mv.addObject("member", member);

    // 장독대 순위
    List<UserScoreDto> jangDocDaeChart = memberService.getJangDocDaeChart();
    mv.addObject("jangDocDaeChart", jangDocDaeChart);

    // 커뮤니티 각각 게시글 정보 가져오기, 최신글 5개 출력(커뮤니티 병합 후)

    return mv;
  }


  //    로그인 구현
  @Autowired
  private MemberService memberService;

  @RequestMapping("/login")
  public String login() throws Exception {
    return "login/login";
  }

  @RequestMapping("/loginProcess")
  public String doLoginProcess(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpServletRequest req) throws Exception {
    int result = memberService.isMemberInfo(id, pw);

//    정보가 있으면 세션 생성 후 데이터를 세션에 저장
    if (result == 1) {
//      현 사용자의 사용자 정보를 DB에서 가져옴
      MemberDto memberInfo = memberService.getMemberInfo(id);

//      세션 생성
      HttpSession session = req.getSession();
//      세션에 현 사용자의 정보를 저장
      session.setAttribute("id", memberInfo.getId());
      session.setAttribute("userName", memberInfo.getUserName());
      session.setAttribute("grade", memberInfo.getGrade());
//            session.setMaxInactiveInterval(60); // 세션 삭제 시간 설정

      return "redirect:/main";
    } else { // 정보가 없으면 loginFail.do 페이지로 리다이렉트
      out.println("<script>alert('아이디 혹은 비밀번호가 다릅니다.'); return;</script>");
      return "redirect:/login";
    }
  }

  @RequestMapping("/recommend")
  public String recommend() throws Exception {
    return "movie/recommend";
  }

  //  로그아웃 프로세스 및 페이지
  @GetMapping("/logout")
  public String doLogout(HttpServletRequest req) throws Exception {

    HttpSession session = req.getSession();

    session.removeAttribute("id");
    session.removeAttribute("userName");
    session.removeAttribute("grade");

    session.invalidate(); // 세션의 모든 정보 삭제

    return "redirect:/main";
  }

  @RequestMapping(value = "/signUp", method = RequestMethod.GET)
  public String signUp() throws Exception {
    return "login/signUp";
  }

  @ResponseBody
  @GetMapping("/confirm")
  public int confirmId(@RequestParam("id") String id) throws Exception {
    int result = memberService.confirmId(id);
    return result;
  }

  // 게시물 등록(내부 프로세스)
  @RequestMapping(value = "/signUp", method = RequestMethod.POST)
  public String memberSignUpProcess(MemberDto member) throws Exception {
    memberService.signUpMember(member);
    return "redirect:/main";
  }

  @RequestMapping("/recommendDetail")
  public String recommendDetail() throws Exception {
    return "movie/recommendDetail";
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
  public ModelAndView movieDetail(@PathVariable("movieId") String movieId, HttpServletRequest req) throws Exception {
    // 영화 정보 가져오기
    ModelAndView mv = new ModelAndView("movie/movieDetail");
    MovieDetailDto movie = movieService.getMovieDetail(tmdbServiceUrl + "movie/" + movieId + "?language=ko");
    List<GenreDto> genre = movie.getGenres();
    CreditsDto credits = movieService.getCredits(tmdbServiceUrl + "movie/" + movieId + "/credits?&language=ko");

    // 제작진 정보
    List<CrewDto> crews = credits.getCrew();
    List<CrewDto> director = new ArrayList<>();
    List<CrewDto> writer = new ArrayList<>();
    for (CrewDto crew : crews) {
      if (crew.getJob().equals("Director")) {
        director.add(crew);
      } else if (crew.getJob().equals("Writer")) {
        writer.add(crew);
      }
    }

    // 출연진 정보
    List<CastDto> casts = credits.getCast();
    List<CastDto> actor = new ArrayList<>();
    for (CastDto cast : casts) {
      switch (cast.getOrder()) {
        case "0", "1", "2", "3", "4", "5" -> {
          actor.add(cast);
          break;
        }
      }
    }

    mv.addObject("movieInfo", movie);
    mv.addObject("genre", genre);
    mv.addObject("director", director);
    mv.addObject("writer", writer);
    mv.addObject("actor", actor);

    // 리뷰 조회
    HttpSession session = req.getSession();
    List<ReviewDto> reviewList;
    ReviewDto myReview = null;
    String userId;
    Object storedUserId = session.getAttribute("id");

    reviewList = memberService.getMovieReviewList(movieId);
    if (storedUserId != null) {
      userId = (String) storedUserId;
      myReview = memberService.getMyMovieReview(movieId, userId);
    }
    // 사용자 리뷰 평점 평균
    String userScoreAvg = memberService.userScoreAvg(movieId);

    mv.addObject("myReview", myReview);
    mv.addObject("reviewList", reviewList);
    mv.addObject("userScoreAvg", userScoreAvg);

    return mv;
  }

  // 영화 리뷰 작성
  @RequestMapping(value = "/insertMovieReview", method = RequestMethod.POST)
  public String insertMovieReview(ReviewDto review) throws Exception {
    memberService.insertMovieReview(review);
    // 새 리뷰 insert시 평균 계산하여 영화 테이블에 insert
    UserScoreDto movie = memberService.getScoreAvgMovie(review.getMovieId());
    if (movie == null) {
      UserScoreDto saveMovieScore = new UserScoreDto();
      saveMovieScore.setId(review.getMovieId());
      saveMovieScore.setTitle(review.getMovieTitle());
      saveMovieScore.setScoreAvg(String.valueOf(review.getUserScore()));

      memberService.insertUserScoreAvg(saveMovieScore);
    } else {
      String userScoreAvg = memberService.userScoreAvg(review.getMovieId());
      UserScoreDto updateMovieScore = new UserScoreDto();
      updateMovieScore.setId(review.getMovieId());
      updateMovieScore.setTitle(review.getMovieTitle());
      updateMovieScore.setScoreAvg(userScoreAvg);

      memberService.updateUserScoreAvg(updateMovieScore);
    }

    String movieId = review.getMovieId();
    return "redirect:/movieDetail/" + movieId;
  }

  // 영화 리뷰 수정
  @RequestMapping(value = "/updateMovieReview", method = RequestMethod.PUT)
  public String updateMovieReview(ReviewDto review) throws Exception {
    memberService.updateMovieReview(review);

    // 영화 평점 업데이트
    String userScoreAvg = memberService.userScoreAvg(review.getMovieId());
    UserScoreDto updateMovieScore = new UserScoreDto();
    updateMovieScore.setId(review.getMovieId());
    updateMovieScore.setTitle(review.getMovieTitle());
    updateMovieScore.setScoreAvg(userScoreAvg);
    memberService.updateUserScoreAvg(updateMovieScore);

    String movieId = review.getMovieId();
    String movieTitle = URLEncoder.encode(review.getMovieTitle(), "UTF-8");
    return "redirect:/movieReview/" + movieId + "/" + movieTitle;
  }

  // 영화 리뷰 삭제
  @RequestMapping(value = "/deleteMovieReview/{idx}", method = RequestMethod.DELETE)
  public String deleteMovieReview(@PathVariable("idx") int idx, @RequestParam("movieTitle") String movieTitle, @RequestParam("movieId") String movieId) throws Exception {
    memberService.deleteMovieReview(idx);
    movieTitle = URLEncoder.encode(movieTitle, "UTF-8");
    return "redirect:/movieReview/" + movieId + "/" + movieTitle;
  }

  // 해당 영화 모든 리뷰 조회
  @RequestMapping(value = "/movieReview/{movieId}/{movieTitle}", method = RequestMethod.GET)
  public ModelAndView movieReview(@PathVariable("movieId") String movieId, @PathVariable("movieTitle") String movieTitle, HttpServletRequest req) throws Exception {
    ModelAndView mv = new ModelAndView("/movie/movieReview");
    // 내가 쓴 영화 리뷰 조회
    // 로그인 확인 후 로그인 되어있을 경우에만 나의 리뷰 조회
    HttpSession session = req.getSession();

    // int checkLike = memberService.checkLike(,userId);
    List<ReviewDto> reviewList;
    ReviewDto myReview = null;

    List<ReviewLikesDto> reviewLikeCheck = null;
    String userId;
    Object storedUserId = session.getAttribute("id");

    reviewList = memberService.getMovieReviewList(movieId);
    if (storedUserId != null) {
      userId = (String) storedUserId;
      myReview = memberService.getMyMovieReview(movieId, userId);

      // 리뷰 좋아요 체크
      reviewLikeCheck = memberService.getReviewLike(userId);
      int checkLike = 0;
      for (ReviewDto review : reviewList) {
        for (ReviewLikesDto reviewLike : reviewLikeCheck) {
          checkLike = memberService.checkLike(reviewLike.getIdx(), userId, review.getIdx());
          if (checkLike == 1) break;
        }
        review.setReviewLikeCheck(checkLike);
      }
    }

    mv.addObject("movieTitle", movieTitle);
    mv.addObject("myReview", myReview);
    mv.addObject("reviewList", reviewList);

    return mv;
  }

  // 리뷰 좋아요
  @ResponseBody
  @RequestMapping(value = "/saveLike", method = RequestMethod.GET)
  public Object saveLike(@RequestParam("reviewIdx") int reviewIdx, HttpServletRequest req) throws Exception {
    HttpSession session = req.getSession();
    String memberId = (String) session.getAttribute("id");

    memberService.saveLike(reviewIdx, memberId);
    ReviewDto review = memberService.getMovieReview(reviewIdx);

    return review;
  }

  // 리뷰 좋아요 취소
  @ResponseBody
  @RequestMapping(value = "/removeLike", method = RequestMethod.GET)
  public Object removeLike(@RequestParam("reviewIdx") int reviewIdx, HttpServletRequest req) throws Exception {
    HttpSession session = req.getSession();
    String memberId = (String) session.getAttribute("id");

    memberService.removeLike(reviewIdx, memberId);
    ReviewDto review = memberService.getMovieReview(reviewIdx);
    return review;
  }

  @RequestMapping("/myMovie")
  public String myMovie() throws Exception {
    return "mypage/myMovie";
  }

  @RequestMapping("searchResult")
  public String searchResult() throws Exception {
    return "movie/searchResult";
  }

  @RequestMapping(value = "/disList", method = RequestMethod.GET)
  public ModelAndView disList() throws Exception {
    ModelAndView mv = new ModelAndView("board/dis/disList");

    return mv;
  }

  @RequestMapping(value = "/disDetail", method = RequestMethod.GET)
  public ModelAndView disDetail() throws Exception {
    ModelAndView mv = new ModelAndView("board/dis/disDetail");

    return mv;
  }

  @RequestMapping(value = "/disWrite", method = RequestMethod.GET)
  public String disInsertView() throws Exception {
    return "board/dis/disWrite";
  }

  @RequestMapping(value = "/freeList", method = RequestMethod.GET)
  public ModelAndView freeList() throws Exception {
    ModelAndView mv = new ModelAndView("board/free/freeList");

    return mv;
  }

  @RequestMapping(value = "/freeDetail", method = RequestMethod.GET)
  public ModelAndView freeDetail() throws Exception {
    ModelAndView mv = new ModelAndView("board/free/freeDetail");

    return mv;
  }

  @RequestMapping(value = "/freeWrite", method = RequestMethod.GET)
  public String freeInsertView() throws Exception {
    return "board/free/freeWrite";
  }

  @RequestMapping(value = "/nowList", method = RequestMethod.GET)
  public ModelAndView nowList() throws Exception {
    ModelAndView mv = new ModelAndView("board/now/nowList");

    return mv;
  }

  @RequestMapping(value = "/nowDetail", method = RequestMethod.GET)
  public ModelAndView nowDetail() throws Exception {
    ModelAndView mv = new ModelAndView("board/now/nowDetail");

    return mv;
  }

  @RequestMapping(value = "/nowWrite", method = RequestMethod.GET)
  public String nowInsertView() throws Exception {
    return "board/now/nowWrite";
  }

  @RequestMapping(value = "/qnaList", method = RequestMethod.GET)
  public ModelAndView qnaList() throws Exception {
    ModelAndView mv = new ModelAndView("board/qna/qnaList");

    return mv;
  }

  @RequestMapping(value = "/qnaDetail", method = RequestMethod.GET)
  public ModelAndView qnaDetail() throws Exception {
    ModelAndView mv = new ModelAndView("board/qna/qnaDetail");

    return mv;
  }

  @RequestMapping(value = "/qnaWrite", method = RequestMethod.GET)
  public String qnaInsertView() throws Exception {
    return "board/qna/qnaWrite";
  }
}
