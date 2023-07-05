package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;

import com.example.jangdocdaefilm.dto.MovieDto;
import com.example.jangdocdaefilm.dto.MoviesDto;
import com.example.jangdocdaefilm.service.MovieService;

import com.example.jangdocdaefilm.dto.MemberDto;
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
                    if (movieLists.get(i).getTitle().equals(query)) {
                        movieList.add(movieLists.get(i));
                    }
                }
            } else {
                movieList.add(movieLists.get(0));
            }
        }
        mv.addObject("dailyBoxOfficeDTOList", dailyBoxOfficeList);
        mv.addObject("movieList", movieList);

        HttpSession session = req.getSession();

        MemberDto member = new MemberDto();
        member.setId((String) session.getAttribute("id"));
        member.setUserName((String) session.getAttribute("userName"));
        member.setGrade((String) session.getAttribute("grade"));

        mv.addObject("member", member);

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
    public ModelAndView movieDetail(@PathVariable("movieId") String movieId) throws Exception {
        ModelAndView mv = new ModelAndView("movie/movieDetail");
        MovieDto movie = movieService.getMovieInfo(tmdbServiceUrl + "movie/" + movieId + "?language=ko");
        mv.addObject("movieInfo", movie);

        return mv;
    }



    @RequestMapping("/myMovie")
    public String myMovie() throws Exception {
        return "mypage/myMovie";
    }

    @RequestMapping("/movieDetail")
    public String movieDetail() throws Exception {
        return "movie/movieDetail";
    }

    @RequestMapping("/movieReview")
    public String movieReview() throws Exception {
        return "movie/movieReview";
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
