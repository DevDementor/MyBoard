package com.myboard.controller;

import com.myboard.entity.Member;
import com.myboard.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @Autowired
    MemberMapper memberMapper;

    @RequestMapping("/memJoin.do")
    public String memJoin(){
        return "member/join";
    }

    @RequestMapping("/memRegisterCheck.do")
    public @ResponseBody int memRegisterCheck(@RequestParam String memId){
        Member member = memberMapper.memRegisterCheck(memId);

        if(member != null || memId.equals("")){
            return 0;
        }

        return 1;
    }
}
