package com.bitc.SpringBoard_sein.controller;

import com.bitc.SpringBoard_sein.dto.BoardDto;
import com.bitc.SpringBoard_sein.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Autowired
  private BoardService boardService;

  @GetMapping("/")
  public String index() throws Exception {
    return "index";
  }

  //  회원가입 페이지로 이동
  @RequestMapping("/join.do")
  public ModelAndView join() throws Exception {
    ModelAndView mv = new ModelAndView("board/join");

    List<BoardDto> join = boardService.selectBoardList();

    mv.addObject("join", join);

    return mv;
  }

  //  로그인 페이지로 이동
  @RequestMapping("/login.do")
  public ModelAndView login() throws Exception {
    ModelAndView mv = new ModelAndView("board/login");

    List<BoardDto> login = boardService.selectBoardList();

    mv.addObject("login", login);

    return mv;
  }

  //  마이페이지로 이동
  @RequestMapping("/myPage.do")
  public ModelAndView myPage() throws Exception {
    ModelAndView mv = new ModelAndView("board/myPage");

    List<BoardDto> myPage = boardService.selectBoardList();

    mv.addObject("myPage", myPage);

    return mv;
  }
}


















