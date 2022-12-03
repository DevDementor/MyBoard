package com.myboard.controller;

import com.myboard.entity.Board;
import com.myboard.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    @RequestMapping("/boardList.do")
    public String main(Model model){
        List<Board> boardList = boardMapper.getLists();
        model.addAttribute("list", boardList);
        return "boardList";
    }

    @GetMapping(value = "/boardForm.do")
    public String boardForm(){
        return "boardForm";
    }

    @PostMapping(value="/boardInsert.do")
    public String boardInsert(Board board){
        boardMapper.boardInsert(board);
        return "redirect:/boardList.do";
    }

}
