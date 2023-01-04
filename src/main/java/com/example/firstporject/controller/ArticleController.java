package com.example.firstporject.controller;

import com.example.firstporject.dto.AtricleForm;
import com.example.firstporject.dto.CommentDto;
import com.example.firstporject.entity.Atricle;
import com.example.firstporject.repository.ArticleRepository;
import com.example.firstporject.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 골뱅이(어노태이션)
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결!
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String creatArticle(AtricleForm form){
        log.info(form.toString());
        // 기록하는 거 System.out.println 서버에 기록남지 않고 성능에 좋지 않다
        // System.out.println(form.toString()); --> 로깅기능으로 대체!

        // 1.Dto를 변환! Entity
        Atricle atricle= form.toEntity();
       log.info(atricle.toString());

        // 2.Repository에게 Entity를 DB안에 저장하게 함!
        Atricle saved= articleRepository.save(atricle);
        log.info(atricle.toString());
        return "redirect:/articles/"+saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);

        // 1: id로 데이터를 가져옴
        //.orElse(null); id값이 없으면 null을 반환해라!!
        Atricle articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos =commentService.comments(id);
        // 2: 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos",commentDtos);

        // 3: 보여줄 페이지를 설정

        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        // 1: 모든 Article을 가져온다!
        List<Atricle> articleEntityList = (List<Atricle>) articleRepository.findAll();
        // 2: 가져온 Article 묶음을 뷰로 전달!
         model.addAttribute("articleList",articleEntityList);
        // 3: 뷰 페이지를 설정!
        return "articles/index"; //articles/index.mustache
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터를 가져오기
        Atricle articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록!
        model.addAttribute("article",articleEntity);

        //뷰페이지 설정
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(AtricleForm form){
        log.info(form.toString());
        // 1: DTO를 엔티티로 변환한다.
         Atricle articleEntity = form.toEntity();
         log.info(articleEntity.toString());
        // 2: 엔티티를 DB로 저장한다.
           //2-1 : DB에서 기존 데이터를 가져온다
          Atricle target = articleRepository.findById(articleEntity.getId()).orElse(null);
           //2-2 : 기존 데이터의 값이 있다면! 값을 갱신한다.
        if(target!=null){
            articleRepository.save(articleEntity); // 엔티티가 DB로 갱신
        }

        // 3: 수정결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/"+ articleEntity.getId();
    }
   @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제요청이들어왔습니다.");
        // 1: 삭제 대상을 가져온다.
       Atricle target= articleRepository.findById(id).orElse(null);
       log.info(target.toString());
       // 2: 그대상을 삭제한다.
       if(target !=null){
           articleRepository.delete(target);
           rttr.addFlashAttribute("msg","delete success");
       }

       // 3: 결과페이지로 리다이렉트한다.
        return "redirect:/articles";
    }
}
