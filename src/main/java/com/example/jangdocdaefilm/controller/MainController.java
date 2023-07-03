package com.example.jangdocdaefilm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() throws Exception {
        return "index";
    }

    @RequestMapping("/category")
    public String category() throws Exception {
        return "category";
    }

    @RequestMapping("/recommend")
    public String recommend() throws Exception {
        return "recommend";
    }

    @RequestMapping("/recommendSet")
    public String recommendSet() throws Exception {
        return "recommendSet";
    }
}
