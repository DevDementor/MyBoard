package com.myboard.controller;

import com.myboard.entity.Board;
import com.myboard.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardMapper boardMapper;

    @RequestMapping("/")
    public String main(Model model){
        List<Board> boardList = boardMapper.getLists();
        model.addAttribute("list", boardList);
        return "main";
    }
}
