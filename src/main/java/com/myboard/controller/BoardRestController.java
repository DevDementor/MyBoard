package com.myboard.controller;

import com.myboard.entity.Board;
import com.myboard.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RestController
public class BoardRestController {
    @Autowired
    BoardMapper boardMapper;

    // @ResponseBody->jackson-databind(객체를->JSON 데이터포멧으로 변환)
    @GetMapping("/all")
    public List<Board> boardList(){
        List<Board> list=boardMapper.getLists();
        return list; // JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
    }
    //@RequestMapping("/boardInsert.do")
    @PostMapping("/new")
    public void boardInsert(Board vo) {
        boardMapper.boardInsert(vo); // 등록성공
    }
    @DeleteMapping("/{idx}")
    public void boardDelete(@PathVariable("idx") int idx) {
        boardMapper.boardDelete(idx);
    }
    @PutMapping("/update")
    public void boardUpdate(@RequestBody Board vo) {
        boardMapper.boardUpdate(vo);
    }
    @GetMapping("/{idx}")
    public Board boardContent(@PathVariable("idx") int idx) {
        Board vo=boardMapper.boardContent(idx);
        return vo; // vo->JSON
    }
    @PutMapping("/count/{idx}")
    public Board boardCount(@PathVariable("idx") int idx) {
        boardMapper.boardCount(idx);
        Board vo=boardMapper.boardContent(idx);
        return vo;
    }

}
