package com.example.jangdocdaefilm.controller;


import com.example.jangdocdaefilm.dto.DisDto;
import com.example.jangdocdaefilm.dto.QnaDto;
import com.example.jangdocdaefilm.dto.FreeDto;
import com.example.jangdocdaefilm.service.DisService;
import com.example.jangdocdaefilm.service.QnaService;

import com.example.jangdocdaefilm.service.FreeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardPageController {

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
  public Object freeList(@RequestParam(value = "checked", required = false, defaultValue = "") String checked, @RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception{
    PageInfo<FreeDto> p = null;

    if (checked == null || checked.equals("") || checked.equals("null")) {
      p = new PageInfo<>(freeService.selectFreeListNewest(pageNum), 10);
    }
    else {
      if (checked.equals("newest")) {
        p = new PageInfo<>(freeService.selectFreeListNewest(pageNum), 10);
      } else if (checked.equals("viewed")) {
        p = new PageInfo<>(freeService.selectFreeListViewed(pageNum), 10);
      }
    }

    return p;
  }

//  문의글 게시판
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
  public Object qnaPageList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception{
    PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaListNewest(pageNum), 10);

    return p;
  }

//  할인정보 게시판
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
  public Object disList(@RequestParam(value = "checked", required = false, defaultValue = "") String checked, @RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception{
    PageInfo<DisDto> p = null;

    if (checked == null || checked.equals("") || checked.equals("null")) {
      p = new PageInfo<>(disService.selectDisListNewest(pageNum), 10);
    }
    else {
      if (checked.equals("newest")) {
        p = new PageInfo<>(disService.selectDisListNewest(pageNum), 10);
      } else if (checked.equals("viewed")) {
        p = new PageInfo<>(disService.selectDisListViewed(pageNum), 10);
      }
    }

    return p;
  }

//  //  할인정보 전체목록 페이징
//  @Autowired
//  private QnaService qnaService;
//
//  @RequestMapping("/qnaList")
//  public ModelAndView qnaList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    ModelAndView mv = new ModelAndView("board/qna/qnaList");
//
//    PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaList(pageNum), 10);
//
//    mv.addObject("qnaList", p);
//    return mv;
//  }
//
//  @ResponseBody
//  @RequestMapping(value = "/board/qna/qnaListAjax")
//  public Object qnaListAjax(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaList(pageNum), 10);
//    return p;
//  }
//
////  개봉작수다 전체목록 페이징
//  @Autowired
//  private NowService nowService;
//
//  @RequestMapping("/nowList")
//  public ModelAndView nowList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    ModelAndView mv = new ModelAndView("board/now/nowList");
//
//    PageInfo<NowDto> p = new PageInfo<>(nowService.selectNowList(pageNum), 10);
//
//    mv.addObject("nowList", p);
//    return mv;
//  }
//
//  @ResponseBody
//  @RequestMapping(value = "/board/now/nowListAjax")
//  public Object nowListAjax(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    PageInfo<NowDto> p = new PageInfo<>(nowService.selectNowList(pageNum), 10);
//    return p;
//  }
//
//  //  문의게시판 전체목록 페이징
//  @Autowired
//  private QnaService qnaService;
//
//  @RequestMapping("/qnaList")
//  public ModelAndView qnaList(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    ModelAndView mv = new ModelAndView("board/qna/qnaList");
//
//    PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaList(pageNum), 10);
//
//    mv.addObject("qnaList", p);
//    return mv;
//  }
//
//  @ResponseBody
//  @RequestMapping(value = "/board/qna/qnaListAjax")
//  public Object qnaListAjax(@RequestParam(required = false, defaultValue = "1") int pageNum) throws Exception {
//    PageInfo<QnaDto> p = new PageInfo<>(qnaService.selectQnaList(pageNum), 10);
//    return p;
//  }
}






