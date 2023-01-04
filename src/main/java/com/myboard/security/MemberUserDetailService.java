package com.myboard.security;

import com.myboard.entity.Member;
import com.myboard.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberUserDetailService implements UserDetailsService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인 처리
        Member mvo = memberMapper.memLogin(username);

        if(mvo != null){

        }else{
         throw new UsernameNotFoundException("user with username "+username+"not found");
        }

        return null;
    }
}
