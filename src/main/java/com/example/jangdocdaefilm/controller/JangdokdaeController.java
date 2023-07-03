package com.example.jangdocdaefilm.controller;

import com.example.jangdocdaefilm.dto.DailyBoxOfficeDTO;
import com.example.jangdocdaefilm.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class JangdokdaeController {

    @Value("${jang.kobis.json.DailyBoxOfficeResultUrl}")
    private String serviceUrl;

    @Value("${jang.kobis.json.key}")
    private String serviceKey;

    @Autowired
    private ParseService parseService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage() {
        return "jangdokdae/main";
    }
//    @ResponseBody
//    @RequestMapping(value = "/main", method = RequestMethod.GET)
//    public ModelAndView getDailyBoxOfficeProcess(@RequestParam("targetDt") String targetDt) throws Exception{
//        ModelAndView mv = new ModelAndView("jangdokdae/main");
//        String url = serviceUrl + "?key=" + serviceKey + "&targetDt" + targetDt;
//        List<DailyBoxOfficeDTO> dailyBoxOfficeDTOList = parseService.getDailyBoxOfficeList(url);
//        mv.addObject("dailyBoxOfficeDTOList", dailyBoxOfficeDTOList);
//        return mv;
//    }

    @RequestMapping(value = "/movieDetail", method = RequestMethod.GET)
    public String movieDetailPage() {
        return "jangdokdae/movieDetail";
    }

    @RequestMapping(value = "/movieReview", method = RequestMethod.GET)
    public String movieReviewPage() {
        return "jangdokdae/movieReview";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String movieSearchPage() {
        return "jangdokdae/searchResult";
    }
}
