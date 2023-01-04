package com.example.firstporject.service;

import com.example.firstporject.dto.CommentDto;
import com.example.firstporject.entity.Atricle;
import com.example.firstporject.entity.Comment;
import com.example.firstporject.repository.ArticleRepository;
import com.example.firstporject.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
      // 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 발생
       Atricle atricle = articleRepository.findById(articleId)
               .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다."));
        // 댓글 엔티티 생성
        Comment comment= Comment.createComment(dto,atricle);
        // 엔티티 DB 저장
         Comment created= commentRepository.save(comment);
        // DTO 변경하여 변경
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
      // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 실패 대상 댓글이 없음"));
       //수정
       target.patch(dto);
       // DB 갱신
       Comment updated=commentRepository.save(target);
       // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }


    @Transactional
    public CommentDto delete(Long id) {
        //  댓글 조회(및 예외 발생)
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));
        // 댓글 삭제
        commentRepository.delete(target);
        //삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);

    }
}
