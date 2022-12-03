package com.myboard.mapper;

import com.myboard.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> getLists();

    void boardInsert(Board board);

    Board boardContent(int idx);

    void boardDelete(int idx);

    void boardUpdate(Board vo);

    @Update("update myboard set count=count+1 where idx=#{idx}")
    public void boardCount(int idx);
}

