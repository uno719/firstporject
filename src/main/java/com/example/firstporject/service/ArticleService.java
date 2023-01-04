package com.example.firstporject.service;

import com.example.firstporject.dto.AtricleForm;
import com.example.firstporject.entity.Atricle;
import com.example.firstporject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언!(서비스 객체를 스프링부트에 생성)
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;
    public List<Atricle> index(){
       return articleRepository.findAll();
    }
    public Atricle show(Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public Atricle create(AtricleForm dto){
        Atricle atricle =dto.toEntity();
        if(atricle.getId() != null){
            return null;
        }
        return articleRepository.save(atricle);
    }
    public Atricle update(Long id, AtricleForm dto){
        //1: 수정용 엔티티 생성
        Atricle atricle =dto.toEntity();
        log.info("id:{},atricle:{}",id,atricle.toString());
        // 2: 대상 엔티티 찾기
        Atricle target= articleRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리(대상이 없거나,id가 다른 경우)
        if(target == null || id != atricle.getId()){
            log.info("잘못된 요청입니다. id:{},atricle:{}",id,atricle.toString());
            return null;
        }
        // 4: 업데이트
        target.patch(atricle); // 이어붙임
        Atricle updated = articleRepository.save(target);
        return updated;
    }
    public Atricle delete(Long id){
        // 대상 찾기
        Atricle target=articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null){
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메소드를 트랜젝션으로 묶는다!
    public List<Atricle> createArticles(List<AtricleForm> dtos) {
        // dto 묶음을 엔티티 묶으로 변환
       List<Atricle> atricleList = dtos.stream()
                .map(dto->dto.toEntity())
                .collect(Collectors.toList());
//       List<Atricle> atricleList= new ArrayList<>();
//       for(int i=0; i<dtos.size();i++){
//           AtricleForm dto = dtos.get(i);
//           Atricle entity = dto.toEntity();
//           atricleList.add(entity);
//       }
        // 엔티티 묶음을 DB로 저장
        atricleList.stream()
                .forEach(atricle -> articleRepository.save(atricle));
//        for(int i=0; i<atricleList.size(); i++){
//            Atricle atricle =atricleList.get(id);
//            articleRepository.save(atricle);
//        }
        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                ()-> new IllegalArgumentException("결재 실패")
        );
        //결과값반환
        return atricleList;
    }
}
