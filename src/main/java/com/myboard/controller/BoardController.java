package com.myboard.controller;

import com.myboard.entity.Board;
import com.myboard.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    @RequestMapping("/")
    public String main(Model model){
        return "main";
    }
    @GetMapping("/boardList.do")
    public @ResponseBody List<Board> boardList (Model model){
        List<Board> boardList = boardMapper.getLists();
        model.addAttribute("list", boardList);

        return boardList;
    }

    @PostMapping("/boardInsert.do")
    public @ResponseBody void boardInsert(Board board){
        boardMapper.boardInsert(board);
//        return "redirect:/boardList.do";
    }

    @GetMapping("/boardDelete.do")
    public @ResponseBody void boardDelete(@RequestParam("idx")int idx){
        boardMapper.boardDelete(idx);
    }

    @PostMapping("/boardUpdate.do")
    @ResponseBody
    public void boardUpdate(Board vo){
        boardMapper.boardUpdate(vo);
    }
}
