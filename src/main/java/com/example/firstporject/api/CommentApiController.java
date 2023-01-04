package com.example.firstporject.api;

import com.example.firstporject.dto.CommentDto;
import com.example.firstporject.service.CommentService;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;
    // 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        // 서비스 위임
        List<CommentDto> dtos=commentService.comments(articleId);

        // 결과응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    // 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto){
        CommentDto createdto =commentService.create(articleId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdto);
    }
    // 수정
    // 생성
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto){
        CommentDto updatedDto =commentService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deletedDto =commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
