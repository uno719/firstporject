package com.example.firstporject.entity;

import com.example.firstporject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany // 해당 댓글 엔티티 여러개가, 하나의 Atricle에 연관된다.
    @JoinColumn(name = "atricle_id") // "atricle_id" 컬럼에 Article의 대표값을 저장
    private Atricle atricle;

    @Column
    private String nickname;

    @Column
    private String body;

    @SneakyThrows
    public static Comment createComment(CommentDto dto, Atricle atricle) {
        // 예외 처리
        if(dto.getId() != null)
            throw  new IllegalAccessException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(dto.getArticleId() != atricle.getId())
            throw  new IllegalAccessException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        //엔티티생성 및반환
        return new Comment(
                dto.getId(),
                atricle,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId())
            throw new IllegalAccessException("댓글 수정 실패! 잘못된 id가 입력되었습니다. ")
        //객체 생신
        if(dto.getNickname()!=null)
            this.nickname= dto.getNickname();
        if(dto.getBody() != null)
            this.body =dto.getBody();
    }
}
