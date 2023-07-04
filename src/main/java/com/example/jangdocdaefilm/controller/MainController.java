package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;
import com.example.jangdocdaefilm.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class MainController {
    // 영화 진흥원 일일 박스 오피스 api주소, 키(application.properties에서 가져옴)
    @Value("${jang.kobis.json.DailyBoxOfficeResultUrl}")
    private String serviceUrl;
    @Value("${jang.kobis.json.key}")
    private String serviceKey;

    @Autowired
    private ParseService parseService;

    @RequestMapping("/")
    public String index() throws Exception {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView getDailyBoxOfficeProcess() throws Exception{

        // 어제 날짜 계산
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        cal.add(cal.DATE, -1);
        String targetDt = simpleDateFormat.format(cal.getTime());

        ModelAndView mv = new ModelAndView("/main");
        String url = serviceUrl + "?key=" + serviceKey + "&targetDt=" + targetDt;
        List<DailyBoxOfficeDTO> dailyBoxOfficeDTOList = parseService.getDailyBoxOfficeList(url);
        mv.addObject("dailyBoxOfficeDTOList", dailyBoxOfficeDTOList);
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

    // discount controll
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

    // 게시물 등록(사용자 입력 페이지)
    @RequestMapping(value = "/disWrite", method = RequestMethod.GET)
    public String disInsertView() throws Exception{
        return "board/dis/disWrite";
    }

    // 게시물 등록(내부 프로세스)
    @RequestMapping(value = "/disWrite", method = RequestMethod.POST)
    public String disInsertProcess() throws Exception{
        return "redirect:/disList";
    }

    // 게시물 수정
    @RequestMapping(value = "/disWrite", method = RequestMethod.PUT)
    public String disUpdate() throws Exception{
        return "redirect:/disList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/disDetail", method = RequestMethod.DELETE)
    public String disDelete() throws Exception{
        return "redirect:/disList";
    }

    // freeBoard controll
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

    // 게시물 등록(사용자 입력 페이지)
    @RequestMapping(value = "/freeWrite", method = RequestMethod.GET)
    public String freeInsertView() throws Exception{
        return "board/free/freeWrite";
    }

    // 게시물 등록(내부 프로세스)
    @RequestMapping(value = "/freeWrite", method = RequestMethod.POST)
    public String freeInsertProcess() throws Exception{
        return "redirect:/freeList";
    }

    // 게시물 수정
    @RequestMapping(value = "/freeWrite", method = RequestMethod.PUT)
    public String freeUpdate() throws Exception{
        return "redirect:/freeList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/freeDetail", method = RequestMethod.DELETE)
    public String freeDelete() throws Exception{
        return "redirect:/freeList";
    }

    // nowMovie controll
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

    // 게시물 등록(사용자 입력 페이지)
    @RequestMapping(value = "/nowWrite", method = RequestMethod.GET)
    public String nowInsertView() throws Exception{
        return "board/now/nowWrite";
    }

    // 게시물 등록(내부 프로세스)
    @RequestMapping(value = "/nowWrite", method = RequestMethod.POST)
    public String nowInsertProcess() throws Exception{
        return "redirect:/nowList";
    }

    // 게시물 수정
    @RequestMapping(value = "/nowWrite", method = RequestMethod.PUT)
    public String nowUpdate() throws Exception{
        return "redirect:/nowList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/nowDetail", method = RequestMethod.DELETE)
    public String nowDelete() throws Exception{
        return "redirect:/nowList";
    }

    // QnA controll
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

    // 게시물 등록(사용자 입력 페이지)
    @RequestMapping(value = "/qnaWrite", method = RequestMethod.GET)
    public String qnaInsertView() throws Exception{
        return "board/qna/qnaWrite";
    }

    // 게시물 등록(내부 프로세스)
    @RequestMapping(value = "/qnaWrite", method = RequestMethod.POST)
    public String qnaInsertProcess() throws Exception{
        return "redirect:/qnaList";
    }

    // 게시물 수정
    @RequestMapping(value = "/qnaWrite", method = RequestMethod.PUT)
    public String qnaUpdate() throws Exception{
        return "redirect:/qnaList";
    }

    // 게시물 삭제
    @RequestMapping(value = "/qnaDetail", method = RequestMethod.DELETE)
    public String qnaDelete() throws Exception{
        return "redirect:/qnaList";
    }
}
