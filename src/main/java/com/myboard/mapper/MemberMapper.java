package com.myboard.mapper;

import com.myboard.entity.AuthVO;
import com.myboard.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    public Member memRegisterCheck(String memId);

    public int memberRegister(Member member);

    public Member memLogin(String username);

    public int memUpdate(Member m);

    public Member getMember(String memID);

    void memProfileUpdate(Member mvo);

    public void authInsert(AuthVO saveVO);

    public List<Map<String, Object>> test_2();
}
