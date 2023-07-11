package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.*;

import com.example.jangdocdaefilm.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //    회원정보 db와 연동
    @Autowired
    private MemberService memberService;

    // 회원가입 페이지로 이동(회원정보 수정)
    @ResponseBody
    @RequestMapping(value = "/myPage/{id}", method = RequestMethod.POST)
    public String MemberChangeProcess(@RequestParam(value = "pw", required = false, defaultValue = "") String pw, @RequestParam(value = "name", required = false, defaultValue = "") String name, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        String id = session.getAttribute("id").toString();


        if (pw == null || pw.equals("") || pw.equals("null")) {
            memberService.changeUserName(id, name);
            session.setAttribute("userName", name);
        } else if (name == null || name.equals("") || name.equals("null")) {
            memberService.changePassword(id, pw);
        } else {
            memberService.changeMember(id, pw, name);
            session.setAttribute("userName", name);
        }

        return "success";
    }

    //    password 중복확인
    @ResponseBody
    @GetMapping("/confirmPw")
    public int confirmPw(@RequestParam("pw") String pw, HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();
        String id = session.getAttribute("id").toString();

        int result = memberService.confirmPw(id, pw);
        return result;
    }

    //    user_name 중복확인
    @ResponseBody
    @GetMapping("/confirmName")
    public int confirmName(@RequestParam("name") String name) throws Exception {
        int result = memberService.confirmName(name);
        return result;
    }

    // 로그인페이지로 이동
    @RequestMapping("/login")
    public String login() throws Exception {
        return "login/login";
    }

    // 로그인 시 session값 저장

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

        } else { // 정보가 없으면 login페이지로 이동

            out.println("<script>alert('아이디 혹은 비밀번호가 다릅니다.'); return;</script>");
            return "redirect:/login";
        }
    }

    //  로그 아웃 프로세스 및 페이지
    @GetMapping("/logout")
    public String doLogout(HttpServletRequest req) throws Exception {

        HttpSession session = req.getSession();

        session.removeAttribute("id");
        session.removeAttribute("userName");
        session.removeAttribute("grade");

        session.invalidate(); // 세션의 모든 정보 삭제

        return "redirect:/main";
    }

    // 회원가입 페이지 이동
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp() throws Exception {
        return "login/signUp";
    }

    // 회원가입시 아이디 중복 확인
    @ResponseBody
    @GetMapping("/confirmId")
    public int confirmId(@RequestParam("id") String id) throws Exception {
        int result = memberService.confirmId(id);
        return result;
    }

    // 회원가입 등록(내부 프로세스)
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
        return "redirect:/movieReview/" + movieId;
    }

    // 영화 리뷰 삭제
    @RequestMapping(value = "/deleteMovieReview/{idx}", method = RequestMethod.DELETE)
    public String deleteMovieReview(@PathVariable("idx") int idx, @RequestParam("movieTitle") String movieTitle, @RequestParam("movieId") String movieId) throws Exception {
        memberService.deleteMovieReview(idx);

        String userScoreAvg = memberService.userScoreAvg(movieId);
        if (userScoreAvg != null) {
            UserScoreDto updateMovieScore = new UserScoreDto();
            updateMovieScore.setId(movieId);
            updateMovieScore.setTitle(movieTitle);
            updateMovieScore.setScoreAvg(userScoreAvg);
            memberService.updateUserScoreAvg(updateMovieScore);
        } else {
            memberService.deleteUserScoreAvg(movieId);
        }

        List<ReviewDto> reviewList = memberService.getMovieReviewList(movieId);

        if (reviewList.size() == 0) {
            return "redirect:/movieDetail/" + movieId;
        } else {
            return "redirect:/movieReview/" + movieId;
        }
    }

    // 해당 영화 모든 리뷰 조회
    @RequestMapping(value = "/movieReview/{movieId}", method = RequestMethod.GET)
    public ModelAndView movieReview(@PathVariable("movieId") String movieId, HttpServletRequest req) throws Exception {
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

    @RequestMapping("/recommend")
    public ModelAndView recommend() throws Exception {
        ModelAndView mv = new ModelAndView("movie/recommend");

        List<RecomDto> recomList = movieService.getRecoms();
        List<String> likes = movieService.getRecomsLike("tester1");

        for (RecomDto recom : recomList) {
            if (likes.contains(recom.getIdx())) {
                recom.setLike("Y");
            } else {
                recom.setLike("N");
            }
            recom.setPoster(movieService.setPosterPath(tmdbServiceUrl + "movie/" + recom.getMovieId() + "?language=ko"));

        }


        mv.addObject("recomList", recomList);

        return mv;
    }


    @RequestMapping("/movieDetail")
    public String movieDetail() throws Exception {
        return "movie/movieDetail";
    }

    @RequestMapping("/movieReview")
    public String movieReview() throws Exception {
        return "movie/movieReview";
    }

    //    댓글
    @Autowired
    private CommentService commentService;

    // 01 자유게시판
    @Autowired
    private FreeService freeService;

    // 자유게시판 상세 페이지
    @RequestMapping(value = "/free/{idx}", method = RequestMethod.GET)
    public ModelAndView freeDetail(@PathVariable("idx") int idx, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("board/free/freeDetail");

        // 세션에서 idx값 불러오기
        HttpSession session = req.getSession();
        session.setAttribute("idx", idx);

//        CommentDto comment = new CommentDto();
//        comment.setFreeIdx(idx);

        FreeDto free = freeService.selectFreeDetail(idx);
        mv.addObject("free", free);

        // 자유게시판 상세정보 및 해당 게시판의 comment정보를 상세보기 페이지로 전송
        List<CommentDto> commentList = commentService.freeCommentList(idx);
        mv.addObject("comment", commentList);

        List<FreeFileDto> freeFiles = freeService.selectFreeFile(idx);
        mv.addObject("freeFiles", freeFiles);

        return mv;
    }

    // 댓글 달기 구현(db에 저장 후 상세페이지로 다시 이동)
    @RequestMapping(value = "/freeCommentWrite", method = RequestMethod.POST)
    public String freeCommentWrite(CommentDto comment) throws Exception {
        commentService.freeWriteComment(comment);
        int idx = comment.getFreeIdx();
        return "redirect:/free/" + idx;
    }

    //    댓글 삭제( 모든 게시판 )
    @RequestMapping(value = "/commentDelete", method = RequestMethod.POST)
    public String commentDelete(CommentDto comment) throws Exception {
        int idx = comment.getIdx();
        int freeIdx = comment.getFreeIdx();
        int nowIdx = comment.getNowIdx();
        int disIdx = comment.getDisIdx();
        int qnaIdx = comment.getQnaIdx();

        String re = null;

        if (freeIdx != 0) {
            commentService.commentDelete(idx);
            re = "redirect:/free/" + freeIdx;
        } else if (nowIdx != 0) {
            commentService.commentDelete(idx);
            re = "redirect:/now/" + nowIdx;
        } else if (disIdx != 0) {
            commentService.commentDelete(idx);
            re = "redirect:/dis/" + disIdx;
        } else if (qnaIdx != 0) {
            commentService.commentDelete(idx);
            re = "redirect:/qna/" + qnaIdx;
        }

        return re;
    }

    // 자유게시판 수정 페이지로 이동(상세보기 페이지의 정보들을 수정페이지로 전송)
    @RequestMapping(value = "/freeUpdate/{idx}", method = RequestMethod.PUT)
    public ModelAndView freeUpdateView(@PathVariable("idx") int idx) throws Exception {
        ModelAndView mv = new ModelAndView("board/free/freeUpdate");

        FreeDto free = freeService.updateFreeView(idx);

        mv.addObject("free", free);

        return mv;
    }


    // 자유게시판 수정 구현(freeList 이동)
    @RequestMapping(value = "/freeUpdate", method = RequestMethod.POST)
    public String freeUpdateProcess(FreeDto free, MultipartHttpServletRequest multipart) throws Exception {
        freeService.updateFree(free, multipart);
        return "redirect:/freeList";
    }

    // 자유게시판 글 등록페이지로 이동
    @RequestMapping(value = "/freeWrite", method = RequestMethod.GET)
    public String freeWriteView() throws Exception {
        return "board/free/freeWrite";
    }

    // 자유게시판 글 쓰기(파일업로드 수정)
    @RequestMapping(value = "/freeWrite", method = RequestMethod.POST)
    public String freeWriteProcess(FreeDto free, MultipartHttpServletRequest multipart) throws Exception {
        freeService.writeFree(free, multipart);
        return "redirect:/freeList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/free/{idx}", method = RequestMethod.DELETE)
    public String freeDelete(@PathVariable("idx") int idx) throws Exception {
        freeService.deleteFree(idx);
        return "redirect:/freeList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/freeList", method = RequestMethod.DELETE)
    public Object freeMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception {
        freeService.freeMultiDelete(idx);
        return "success";
    }


    //    문의글 게시판
    @Autowired
    private QnaService qnaService;


    // 문의글 상세 페이지
    @RequestMapping(value = "/qna/{idx}", method = RequestMethod.GET)
    public ModelAndView qnaDetail(@PathVariable("idx") int idx, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("board/qna/qnaDetail");

        // 세션에서 idx값 불러오기
        HttpSession session = req.getSession();
        session.setAttribute("idx", idx);

        // db의 qna테이블에서 idx값 가져와 Comment테이블의 qna_idx값과 일치하는 정보 가져오기
        QnaDto qna = qnaService.selectQnaDetail(idx);
        mv.addObject("qna", qna);

        // 자유게시판 상세정보 및 해당 게시판의 comment정보를 상세보기 페이지로 전송
        List<CommentDto> commentList = commentService.qnaCommentList(idx);
        mv.addObject("comment", commentList);

        List<QnaFileDto> qnaFiles = qnaService.selectQnaFile(idx);
        mv.addObject("qnaFiles", qnaFiles);

        return mv;
    }

    // 댓글 달기 구현(db에 저장 후 상세페이지로 다시 이동)
    @RequestMapping(value = "/qnaCommentWrite", method = RequestMethod.POST)
    public String qnaCommentWrite(CommentDto comment) throws Exception {
        commentService.qnaWriteComment(comment);
        int idx = comment.getQnaIdx();
        return "redirect:/qna/" + idx;
    }

    // 문의글 수정 페이지로 이동(상세보기 페이지의 정보들을 수정페이지로 전송)
    @RequestMapping(value = "/qnaUpdate/{idx}", method = RequestMethod.PUT)
    public ModelAndView qnaUpdateView(@PathVariable("idx") int idx) throws Exception {
        ModelAndView mv = new ModelAndView("board/qna/qnaUpdate");

        QnaDto qna = qnaService.updateQnaView(idx);

        mv.addObject("qna", qna);

        return mv;
    }

    // 문의글 수정 구현(qnaList 이동)
    @RequestMapping(value = "/qnaUpdate", method = RequestMethod.POST)
    public String qnaUpdateProcess(QnaDto qna, MultipartHttpServletRequest multipart) throws Exception {
        qnaService.updateQna(qna, multipart);
        return "redirect:/qnaList";
    }

    // 문의글 글 등록페이지로 이동
    @RequestMapping(value = "/qnaWrite", method = RequestMethod.GET)
    public String qnaWriteView() throws Exception {
        return "board/qna/qnaWrite";
    }

    // 문의글 글 쓰기(파일업로드 수정)
    @RequestMapping(value = "/qnaWrite", method = RequestMethod.POST)
    public String qnaWriteProcess(QnaDto qna, MultipartHttpServletRequest multipart) throws Exception {
        qnaService.writeQna(qna, multipart);
        return "redirect:/qnaList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/qna/{idx}", method = RequestMethod.DELETE)
    public String qnaDelete(@PathVariable("idx") int idx) throws Exception {
        qnaService.deleteQna(idx);
        return "redirect:/qnaList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/qnaList", method = RequestMethod.DELETE)
    public Object qnaMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception {
        qnaService.qnaMultiDelete(idx);
        return "success";
    }


    //    할인정보 게시판
    @Autowired
    private DisService disService;


    // 할인정보 상세 페이지
    @RequestMapping(value = "/dis/{idx}", method = RequestMethod.GET)
    public ModelAndView disDetail(@PathVariable("idx") int idx, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("board/dis/disDetail");

        // 세션에서 idx값 불러오기
        HttpSession session = req.getSession();
        session.setAttribute("idx", idx);

        // db의 dis테이블에서 idx값 가져와 Comment테이블의 dis_idx값과 일치하는 정보 가져오기
        DisDto dis = disService.selectDisDetail(idx);
        mv.addObject("dis", dis);

        // 자유게시판 상세정보 및 해당 게시판의 comment정보를 상세보기 페이지로 전송
        List<CommentDto> commentList = commentService.disCommentList(idx);
        mv.addObject("comment", commentList);

        List<DisFileDto> disFiles = disService.selectDisFile(idx);
        mv.addObject("disFiles", disFiles);

        return mv;
    }

    // 댓글 달기 구현(db에 저장 후 상세페이지로 다시 이동)
    @RequestMapping(value = "/disCommentWrite", method = RequestMethod.POST)
    public String disCommentWrite(CommentDto comment) throws Exception {
        commentService.disWriteComment(comment);
        int idx = comment.getDisIdx();
        return "redirect:/dis/" + idx;
    }

    // 할인정보 수정 페이지로 이동(상세보기 페이지의 정보들을 수정페이지로 전송)
    @RequestMapping(value = "/disUpdate/{idx}", method = RequestMethod.PUT)
    public ModelAndView disUpdateView(@PathVariable("idx") int idx) throws Exception {
        ModelAndView mv = new ModelAndView("board/dis/disUpdate");

        DisDto dis = disService.updateDisView(idx);

        mv.addObject("dis", dis);

        return mv;
    }

    // 할인정보 수정 구현(disList 이동)
    @RequestMapping(value = "/disUpdate", method = RequestMethod.POST)
    public String disUpdateProcess(DisDto dis, MultipartHttpServletRequest multipart) throws Exception {
        disService.updateDis(dis, multipart);
        return "redirect:/disList";
    }

    // 할인정보 글 등록페이지로 이동
    @RequestMapping(value = "/disWrite", method = RequestMethod.GET)
    public String disWriteView() throws Exception {
        return "board/dis/disWrite";
    }

    // 할인정보 글 쓰기(파일업로드 수정)
    @RequestMapping(value = "/disWrite", method = RequestMethod.POST)
    public String disWriteProcess(DisDto dis, MultipartHttpServletRequest multipart) throws Exception {
        disService.writeDis(dis, multipart);
        return "redirect:/disList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/dis/{idx}", method = RequestMethod.DELETE)
    public String disDelete(@PathVariable("idx") int idx) throws Exception {
        disService.deleteDis(idx);
        return "redirect:/disList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/disList", method = RequestMethod.DELETE)
    public Object disMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception {
        disService.disMultiDelete(idx);
        return "success";
    }

    // 04개봉작수다 커뮤니티
    @Autowired
    private NowService nowService;

    //  개봉작수다 글 등록페이지로 이동
    @RequestMapping(value = "/nowWrite", method = RequestMethod.GET)
    public String nowWriteView() throws Exception {
        return "board/now/nowWrite";
    }

    // 개봉작수다 글 등록(내부 프로세스)
    @RequestMapping(value = "/nowWrite", method = RequestMethod.POST)
    public String nowWriteProcess(NowDto now, MultipartHttpServletRequest multipart) throws Exception {
        nowService.writeNow(now, multipart);
        return "redirect:/nowList";
    }

    //  개봉작수다 상세 페이지
    @RequestMapping(value = "/now/{idx}", method = RequestMethod.GET)
    public ModelAndView nowDetail(@PathVariable("idx") int idx, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("board/now/nowDetail");

        // 세션에서 idx값 불러오기
        HttpSession session = req.getSession();
        session.setAttribute("idx", idx);

        // db의 now테이블에서 idx값 가져와 Comment테이블의 now_idx값과 일치하는 정보 가져오기
        NowDto now = nowService.selectNowDetail(idx);
        mv.addObject("now", now);

        // 자유게시판 상세정보 및 해당 게시판의 comment정보를 상세보기 페이지로 전송
        List<CommentDto> commentList = commentService.nowCommentList(idx);
        mv.addObject("comment", commentList);

        List<NowFileDto> nowFiles = nowService.selectNowFile(idx);
        mv.addObject("nowFiles", nowFiles);

        return mv;
    }

    //  개봉작수다 수정
    @RequestMapping(value = "/nowUpdate/{idx}", method = RequestMethod.PUT)
    public ModelAndView nowUpdateView(@PathVariable("idx") int idx) throws Exception {
        ModelAndView mv = new ModelAndView("board/now/nowUpdate");

        NowDto now = nowService.updateNowView(idx);

        mv.addObject("now", now);

        return mv;
    }

    // 개봉작수다 글 수정페이지로 이동
    @RequestMapping(value = "/nowUpdate", method = RequestMethod.POST)
    public String nowUpdateProcess(NowDto now, MultipartHttpServletRequest multipart) throws Exception {
        nowService.updateNow(now, multipart);
        return "redirect:/nowList";
    }

    // 개봉작수다 글 삭제
    @RequestMapping(value = "/now/{idx}", method = RequestMethod.DELETE)
    public String nowDelete(@PathVariable("idx") int idx) throws Exception {
        nowService.deleteNow(idx);
        return "redirect:/nowList";
    }

    // 개봉작수다 글 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/nowList", method = RequestMethod.DELETE)
    public String nowMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception {
        nowService.nowMultiDelete(idx);
        return "success";
    }

    // 댓글 달기 구현(db에 저장 후 상세페이지로 다시 이동)
    @RequestMapping(value = "/nowCommentWrite", method = RequestMethod.POST)
    public String nowCommentWrite(CommentDto comment) throws Exception {
        commentService.nowWriteComment(comment);
        int idx = comment.getNowIdx();
        return "redirect:/now/" + idx;
    }

}