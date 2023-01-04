package com.example.firstporject.repository;

import com.example.firstporject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글 모든 조회
    @Query(value = "SELECT * FROM comment WHERE  atricle_id = :articleId",nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);
    //  특정 닉네임의 모든 댓글 조회
    List<Comment>  findByNickname(String nickname);

}
