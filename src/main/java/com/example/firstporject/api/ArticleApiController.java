package com.example.firstporject.api;

import com.example.firstporject.dto.AtricleForm;
import com.example.firstporject.entity.Atricle;
import com.example.firstporject.repository.ArticleRepository;
import com.example.firstporject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController // RestAPI용 컨트롤러! 데이터(JSON)를 반환!
public class ArticleApiController {
   @Autowired //DI ,생성 객체를 가져와 연결!
  private ArticleService articleService;
   // GET 목록 조회
    @GetMapping("/api/articles")
    public List<Atricle> index(){
        return articleService.index();
    }
    // 단일 조회
    @GetMapping("/api/articles/{id}")
    public Atricle show(@PathVariable Long id){
        return articleService.show(id);
    }
    // POST // DB 저장
     @PostMapping("/api/articles")
    public ResponseEntity <Atricle> create(@RequestBody AtricleForm dto){
        Atricle created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created):
                  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     }
    //PATCH
    @PatchMapping("/api/articles/{id}") //ResponseEntity : 상태 코드를 반환하기 위해서 묶어주는 것
    public ResponseEntity<Atricle> update(@PathVariable Long id, @RequestBody AtricleForm dto){
     Atricle updated= articleService.update(id,dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity <Void> delete(@PathVariable Long id){
        Atricle deleted = articleService.delete(id);
        return (deleted != null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 트레잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Atricle>> transactionTest(@RequestBody List<AtricleForm> dtos){
        List<Atricle> createList = articleService.createArticles(dtos);
        return (createList != null) ? ResponseEntity.status(HttpStatus.OK).body(createList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
