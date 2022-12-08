package com.myboard.entity;

import lombok.Data;

@Data
public class Member {
    private int memIdx;
    private String memId;
    private String memPassword;
    private String memName;
    private int memAge; // <-null, 0
    private String memGender;
    private String memEmail;
    private String memProfile; //사진정보
}
