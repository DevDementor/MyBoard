package com.myboard.mapper;

import com.myboard.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public Member memRegisterCheck(String memId);

    public int memberRegister(Member member);

    public Member memLogin(String memId, String password);

    public int memUpdate(Member m);
}
