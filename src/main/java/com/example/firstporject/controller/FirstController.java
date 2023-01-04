package com.example.firstporject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceTomeetYou(Model model){
        model.addAttribute("username", "uno");
        return "greetings"; // template/greetings.mustache -> 브라우저로 전송
    }
    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nick","uno");
        return "goodbye";
    }
}
