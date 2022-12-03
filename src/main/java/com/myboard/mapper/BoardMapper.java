package com.myboard.mapper;

import com.myboard.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> getLists();

    void boardInsert(Board board);

    Board boardContent(int idx);
}

