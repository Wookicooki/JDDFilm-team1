package com.example.jangdocdaefilm.controller;


import com.example.jangdocdaefilm.dto.*;
import com.example.jangdocdaefilm.service.*;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class BoardPageController {

    @Value("${jangDocDae.tmdb.json.Url}")
    private String tmdbServiceUrl;

    //  자유게시판 전체목록 페이징
    @Autowired
    private FreeService freeService;

    @RequestMapping(value = "/freeList", method = RequestMethod.GET)
    public ModelAndView freeList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        ModelAndView mv = new ModelAndView("board/free/freeList");

        PageInfo<FreeDto> p = new PageInfo<>(freeService.selectFreeListNewest(pageNum), 10);

        mv.addObject("freeList", p);

        return mv;
    }


    // 게시물 순서 변경
    @ResponseBody
    @RequestMapping(value = "/freeList", method = RequestMethod.POST)
    public Object freeList(@RequestParam(value = "checked", required = false, defaultValue = "") String checked, @RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        PageInfo<FreeDto> p = null;

        if (checked == null || checked.equals("") || checked.equals("null")) {
            p = new PageInfo<>(freeService.selectFreeListNewest(pageNum), 10);
        } else {
            if (checked.equals("newest")) {
                p = new PageInfo<>(freeService.selectFreeListNewest(pageNum), 10);
            } else if (checked.equals("viewed")) {
                p = new PageInfo<>(freeService.selectFreeListViewed(pageNum), 10);
            }
        }

        return p;
    }

    //  02개봉작 수다 전체목록 페이징
    @Autowired
    private NowService nowService;

    @RequestMapping(value = "/nowList", method = RequestMethod.GET)
    public ModelAndView nowList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        ModelAndView mv = new ModelAndView("board/now/nowList");

        PageInfo<NowDto> p = new PageInfo<>(nowService.selectNowListNewest(pageNum), 10);

        mv.addObject("nowList", p);

        return mv;
    }

    // 게시물 순서 변경
    @ResponseBody
    @RequestMapping(value = "/nowList", method = RequestMethod.POST)
    public Object nowList(@RequestParam(value = "checked", required = false, defaultValue = "") String checked, @RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        PageInfo<NowDto> p = null;

        if (checked == null || checked.equals("") || checked.equals("null")) {
            p = new PageInfo<>(nowService.selectNowListNewest(pageNum), 10);
        } else {
            if (checked.equals("newest")) {
                p = new PageInfo<>(nowService.selectNowListNewest(pageNum), 10);
            } else if (checked.equals("viewed")) {
                p = new PageInfo<>(nowService.selectNowListViewed(pageNum), 10);
            }
        }

        return p;
    }

    //  03할인정보 전체목록 페이징
    @Autowired
    private DisService disService;

    @RequestMapping(value = "/disList", method = RequestMethod.GET)
    public ModelAndView disList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        ModelAndView mv = new ModelAndView("board/dis/disList");

        PageInfo<DisDto> p = new PageInfo<>(disService.selectDisListNewest(pageNum), 10);

        mv.addObject("disList", p);

        return mv;
    }

    // 게시물 순서 변경
    @ResponseBody
    @RequestMapping(value = "/disList", method = RequestMethod.POST)
    public Object disList(@RequestParam(value = "checked", required = false, defaultValue = "") String checked, @RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        PageInfo<DisDto> p = null;

        if (checked == null || checked.equals("") || checked.equals("null")) {
            p = new PageInfo<>(disService.selectDisListNewest(pageNum), 10);
        } else {
            if (checked.equals("newest")) {
                p = new PageInfo<>(disService.selectDisListNewest(pageNum), 10);
            } else if (checked.equals("viewed")) {
                p = new PageInfo<>(disService.selectDisListViewed(pageNum), 10);
            }
        }
        return p;
    }

    //  04문의글 게시판
    @Autowired
    private QnaService qnaService;

    @RequestMapping(value = "/qnaList", method = RequestMethod.GET)
    public ModelAndView qnaList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        ModelAndView mv = new ModelAndView("board/qna/qnaList");

        PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaListNewest(pageNum), 10);

        mv.addObject("qnaList", p);

        return mv;
    }

    // 게시물 순서 변경
    @ResponseBody
    @RequestMapping(value = "/qnaList", method = RequestMethod.POST)
    public Object qnaPageList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
        PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaListNewest(pageNum), 10);

        return p;
    }

    //myPage 나의 글 게시판
    @Autowired
    private MyService myService;

    @RequestMapping(value = "/myPage/{id}", method = RequestMethod.GET)
    public ModelAndView myPage(HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("mypage/myPage");
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("id");
        String userName = (String) session.getAttribute("userName");

        List<FreeDto> free = myService.myFree(id);
        List<NowDto> now = myService.myNow(id);
        List<DisDto> dis = myService.myDis(id);

        List<RecomDto> myRecomList = myService.getRecoms(userName);
        List<RecomDto> myLikeRecom = myService.getLikeRecom();
        List<String> likes = myService.getRecomsLike(userName);

        for (RecomDto recom : myRecomList) {
            if (likes.contains(recom.getIdx())) {
                recom.setLike("Y");
            } else {
                recom.setLike("N");
            }
            recom.setPoster(myService.setPosterPath(tmdbServiceUrl + "movie/" + recom.getMovieId() + "?language=ko"));
        }

        for (RecomDto recom : myLikeRecom) {
            if (likes.contains(recom.getIdx())) {
                recom.setLike("Y");
            } else {
                recom.setLike("N");
            }
            recom.setPoster(myService.setPosterPath(tmdbServiceUrl + "movie/" + recom.getMovieId() + "?language=ko"));
        }

        mv.addObject("free", free);
        mv.addObject("now", now);
        mv.addObject("dis", dis);
        mv.addObject("myRecomList", myRecomList);
        mv.addObject("myLikeRecom", myLikeRecom);

        return mv;
    }

}






