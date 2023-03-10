package com.example.firstporject.dto;

import com.example.firstporject.entity.Atricle;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class AtricleForm {
    private Long id; //id νλ μΆκ°
    private String title;
    private String content;

    public Atricle toEntity() {
        return new Atricle(id, title,content);
    }
}
