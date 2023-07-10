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

//        *******로그인 시 member에 id, userName, grade 저장 및 main페이지로 전송
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

    // 회원가입 페이지 이동
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp() throws Exception {
        return "login/signUp";
    }

    // 회원가입시 아이디 중복 확인
    @ResponseBody
    @GetMapping("/confirm")
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

    // 마이페이지로 이동
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

    //    댓글
    @Autowired
    private CommentService commentService;

    @Autowired
    private FreeService freeService;

    @RequestMapping(value = "/free/{idx}", method = RequestMethod.GET)
    public ModelAndView freeDetail(@PathVariable("idx") int idx, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("board/free/freeDetail");

        // 세션에서 idx값 불러오기
        HttpSession session = req.getSession();
        session.setAttribute("idx", idx);

        // db의 free테이블에서 idx값 가져와 Comment테이블의 free_idx값과 일치하는 정보 가져오기
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
    public String freeCommentWrite(CommentDto comment) throws Exception{
        commentService.freeWriteComment(comment);
        int idx = comment.getFreeIdx();
        return "redirect:/free/" + idx;
    }

    // 댓글 삭제 구현
    @RequestMapping(value = "/freeCommentDelete", method = RequestMethod.POST)
    public String freeCommentDelete(CommentDto comment) throws Exception{
        int idx = comment.getIdx();
        int freeIdx = comment.getFreeIdx();
        commentService.freeCommentDelete(idx);

        return "redirect:/free/" + freeIdx;
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
    public String freeUpdateProcess(FreeDto free, MultipartHttpServletRequest multipart) throws Exception{
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
    public String freeWriteProcess(FreeDto free, MultipartHttpServletRequest multipart) throws Exception{
        freeService.writeFree(free, multipart);
        return "redirect:/freeList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/free/{idx}", method = RequestMethod.DELETE)
    public String freeDelete(@PathVariable("idx") int idx) throws Exception{
        freeService.deleteFree(idx);
        return "redirect:/freeList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/freeList", method = RequestMethod.DELETE)
    public Object freeMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception{
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
    public String qnaCommentWrite(CommentDto comment) throws Exception{
        commentService.qnaWriteComment(comment);
        int idx = comment.getQnaIdx();
        return "redirect:/qna/" + idx;
    }

    // 댓글 삭제 구현
    @RequestMapping(value = "/qnaCommentDelete", method = RequestMethod.POST)
    public String qnaCommentDelete(CommentDto comment) throws Exception{
        int idx = comment.getIdx();
        int qnaIdx = comment.getQnaIdx();
        commentService.qnaCommentDelete(idx);

        return "redirect:/qna/" + qnaIdx;
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
    public String qnaUpdateProcess(QnaDto qna, MultipartHttpServletRequest multipart) throws Exception{
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
    public String qnaWriteProcess(QnaDto qna, MultipartHttpServletRequest multipart) throws Exception{
        qnaService.writeQna(qna, multipart);
        return "redirect:/qnaList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/qna/{idx}", method = RequestMethod.DELETE)
    public String qnaDelete(@PathVariable("idx") int idx) throws Exception{
        qnaService.deleteQna(idx);
        return "redirect:/qnaList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/qnaList", method = RequestMethod.DELETE)
    public Object qnaMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception{
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
    public String disCommentWrite(CommentDto comment) throws Exception{
        commentService.disWriteComment(comment);
        int idx = comment.getDisIdx();
        return "redirect:/dis/" + idx;
    }

    // 댓글 삭제 구현
    @RequestMapping(value = "/disCommentDelete", method = RequestMethod.POST)
    public String disCommentDelete(CommentDto comment) throws Exception{
        int idx = comment.getIdx();
        int disIdx = comment.getDisIdx();
        commentService.disCommentDelete(idx);

        return "redirect:/dis/" + disIdx;
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
    public String disUpdateProcess(DisDto dis, MultipartHttpServletRequest multipart) throws Exception{
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
    public String disWriteProcess(DisDto dis, MultipartHttpServletRequest multipart) throws Exception{
        disService.writeDis(dis, multipart);
        return "redirect:/disList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/dis/{idx}", method = RequestMethod.DELETE)
    public String disDelete(@PathVariable("idx") int idx) throws Exception{
        disService.deleteDis(idx);
        return "redirect:/disList";
    }

    // 게시물 일괄 삭제
    @ResponseBody
    @RequestMapping(value = "/disList", method = RequestMethod.DELETE)
    public Object disMultiDelete(@RequestParam(value = "valueArrTest[]") Integer[] idx) throws Exception{
        disService.disMultiDelete(idx);
        return "success";
    }


    //    현재상영작 수다
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
}
