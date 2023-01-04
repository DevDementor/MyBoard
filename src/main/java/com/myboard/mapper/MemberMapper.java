package com.myboard.mapper;

import com.myboard.entity.AuthVO;
import com.myboard.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public Member memRegisterCheck(String memId);

    public int memberRegister(Member member);

    public Member memLogin(String username);

    public int memUpdate(Member m);

    public Member getMember(String memID);

    void memProfileUpdate(Member mvo);

    public void authInsert(AuthVO saveVO);
}
