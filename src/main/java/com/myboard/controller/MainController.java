package com.myboard.controller;

import com.myboard.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    MemberMapper memberMapper;

    @RequestMapping("/")
    public String index(Model model){
        List<Map<String, Object>> test_1 = memberMapper.test_2();
        model.addAttribute("test_1", test_1);

        return "index";
    }
}
