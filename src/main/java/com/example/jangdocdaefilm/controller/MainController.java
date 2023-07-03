package com.example.jangdocdaefilm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() throws Exception {
        return "index";
    }

    @RequestMapping("/main")
    public String main() throws Exception {
        return "main";
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

    @RequestMapping("/myMovie")
    public String myMovie() throws Exception {
        return "mypage/myMovie";
    }

    @RequestMapping("/myPage")
    public String myPage() throws Exception {
        return "mypage/myPage";
    }

    @RequestMapping("/myRoom")
    public String myRoom() throws Exception {
        return "mypage/myRoom";
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
}
