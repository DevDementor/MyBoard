package com.myboard.controller;

import com.myboard.entity.Member;
import com.myboard.mapper.MemberMapper;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

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
            //rebase test
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

    // 회원정보수정
    @RequestMapping("/memUpdate.do")
    public String memUpdate(Member m, RedirectAttributes rttr,
                            String memPassword1, String memPassword2, HttpSession session) {
        if(m.getMemId()==null || m.getMemId().equals("") ||
                memPassword1==null || memPassword1.equals("") ||
                memPassword2==null || memPassword2.equals("") ||
                m.getMemName()==null || m.getMemName().equals("") ||
                m.getMemAge()==0 ||
                m.getMemGender()==null || m.getMemGender().equals("") ||
                m.getMemEmail()==null || m.getMemEmail().equals("")) {
            // 누락메세지를 가지고 가기? =>객체바인딩(Model, HttpServletRequest, HttpSession)
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
            return "redirect:/memUpdateForm.do";  // ${msgType} , ${msg}
        }
        if(!memPassword1.equals(memPassword2)) {
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
            return "redirect:/memUpdateForm.do";  // ${msgType} , ${msg}
        }
        // 회원을 수정저장하기
        int result=memberMapper.memUpdate(m);
        if(result==1) { // 수정성공 메세지
            rttr.addFlashAttribute("msgType", "성공 메세지");
            rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
            // 회원수정이 성공하면=>로그인처리하기
            //session.setAttribute("mvo", m); // ${!empty mvo}
            return "redirect:/";
        }else {
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "회원정보 수정에 실패했습니다.");
            return "redirect:/memUpdateForm.do";
        }
    }

    @RequestMapping("/memImageForm.do")
    public String memImageForm(){
        return "member/memImageForm";
    }

    @RequestMapping("/memImageUpdate.do")
    public String memImageUpdate(HttpServletRequest request, HttpSession session, RedirectAttributes rttr) throws IOException {
        // 파일업로드 API(cos.jar, 3가지)
        MultipartRequest multi=null;
        int fileMaxSize=10*1024*1024; // 10MB
        String savePath=request.getRealPath("resources/upload");
        try {
            // 이미지 업로드
            multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8",new DefaultFileRenamePolicy());
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("msgType", "실패 메세지");
            rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");
            return "redirect:/memImageForm.do";
        }
        //DB에 회원 이미지 업데이트
        String memId = multi.getParameter("memId");
        String newProfile="";
        File file = multi.getFile("memProfile");
        if(file != null){
            //이미지 파일 여부를 체크(확장자 체크)
            String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
            ext = ext.toUpperCase();

            if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")){
                String oldProfile = memberMapper.getMember(memId).getMemProfile();
                File oldFile=new File(savePath+"/"+oldProfile);
                if(oldFile.exists()) {
                    oldFile.delete();
                }
                newProfile=file.getName();
            }else{
                if(file.exists()) {
                    file.delete(); //삭제
                }
                rttr.addFlashAttribute("msgType", "실패 메세지");
                rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");
                return "redirect:/memImageForm.do";
            }

            Member mvo=new Member();
            mvo.setMemId(memId);
            mvo.setMemProfile(newProfile);
            memberMapper.memProfileUpdate(mvo); // 이미지 업데이트 성공
            Member m=memberMapper.getMember(memId);
            // 세션을 새롭게 생성한다.
            session.setAttribute("mvo", m);
            rttr.addFlashAttribute("msgType", "성공 메세지");
            rttr.addFlashAttribute("msg", "이미지 변경이 성공했습니다.");
            return "redirect:/";
        }

        return "";
    }
}
