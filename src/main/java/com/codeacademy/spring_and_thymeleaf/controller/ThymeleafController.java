package com.codeacademy.spring_and_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThymeleafController {

    @GetMapping
    public String runIndex() {
        return "index.html";
    }

}
