package com.myboard.controller;

import com.myboard.entity.Member;
import com.myboard.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    MemberMapper memberMapper;

    @RequestMapping("/memJoin.do")
    public String memJoin(){
        return "member/join";
    }

    @RequestMapping("/memRegisterCheck.do")
    public @ResponseBody
    int memRegisterCheck(@RequestParam String memId) {
        Member member = memberMapper.memRegisterCheck(memId);

        if (member != null || memId.equals("")) {
            return 0;
        }
        return 1;
    }

    @RequestMapping("/memRegister.do")
    public String memRegister(Member member, String memPassword, String memPassword2, RedirectAttributes rttr, HttpSession session) {
        if (member.getMemId() == null || member.getMemId().equals("") || memPassword == null || memPassword.equals("") || memPassword2 == null || memPassword2.equals("") || member.getMemName() == null || member.getMemName().equals("") || member.getMemAge() == 0 || member.getMemGender() == null || member.getMemGender().equals("") || member.getMemEmail() == null || member.getMemEmail().equals("")) {
            // 누락메세지를 가지고 가기? =>객체바인딩(Model, HttpServletRequest, HttpSession)
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
            return "redirect:/memJoin.do";
        }

        if(!memPassword.equals(memPassword2)){
            rttr.addFlashAttribute("msgType", "비밀번호가 다릅니다");
            rttr.addFlashAttribute("msg", "같은 비밀번호를 입력하세요.");
            return "redirect:/memJoin.do";
        }

        member.setMemProfile("");

        int registerResult = memberMapper.memberRegister(member);

        if(registerResult == 1){//가입 성공
            rttr.addFlashAttribute("msgType","성공 메세지");
            rttr.addFlashAttribute("msg","회원 가입에 성공했습니다.");

            session.setAttribute("mvo", member);
            return "redirect:/";
        }else{
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
            return "redirect:/memJoin.do";
        }
    }

    @RequestMapping("/memLogout.do")
    public String memLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/memLoginForm.do")
    public String memLoginForm() {
        return "member/memLoginForm";
    }

    @RequestMapping(value = "/memLogin.do", method = RequestMethod.POST)
    public String memLogin(String memId, String password, RedirectAttributes rttr, HttpSession session) {
        //유효성 체크
        if (memId == null || memId.equals("") || password == null || password.equals("")) {
            rttr.addFlashAttribute("msgType", "로그인 실패.");
            rttr.addFlashAttribute("msg", "유효한 값을 입력하세요.");
            return "redirect:/memLoginForm.do";
        }

        //로그인 성공 유무 분기 처리
        Member member = memberMapper.memLogin(memId, password);

        if(member != null) {
            session.setAttribute("mvo", member);
            rttr.addFlashAttribute("msgType", "로그인 성공.");
            rttr.addFlashAttribute("msg", "로그인에 성공하였습니다.");
            return "redirect:/";
        } else {
            rttr.addFlashAttribute("msgType", "로그인 실패.");
            rttr.addFlashAttribute("msg", "로그인에 실패하였습니다.");
            return "redirect:/memLoginForm.do";
        }
    }

    @RequestMapping("/memUpdateForm.do")
    public String memUpdateForm() {
        return "member/memUpdateForm_1";
    }


}
