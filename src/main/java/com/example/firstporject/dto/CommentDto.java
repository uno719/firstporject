package com.example.firstporject.dto;

import com.example.firstporject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class CommentDto {
private Long id;
@JsonProperty("article_Id")
private Long articleId;
private String nickname;
private String body;

    public static CommentDto createCommentDto(Comment comment) {
        return  new CommentDto(
                comment.getId(),
                comment.getAtricle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
