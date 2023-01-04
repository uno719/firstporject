package com.example.firstporject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // DB가 해당 객체를 인식 가능!(해당 클래슬 테이블을 만든다.)
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자를 추가해주는 어노테이션
@ToString
@Getter
public class Atricle{

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동 생성 어노테이션(id값 중복을 막기위해서)
    private  Long id;

    @Column
    private String title;
    @Column
    private String content;
    public  void patch(Atricle atricle){
        if(atricle.title != null)
            this.title = atricle.title;
        if(atricle.content != null)
            this.content =atricle.content;

    }


}
