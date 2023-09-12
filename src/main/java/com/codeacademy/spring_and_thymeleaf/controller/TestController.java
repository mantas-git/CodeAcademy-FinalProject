package com.codeacademy.spring_and_thymeleaf.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "login";
    }

//    @RequestMapping({ "/home", "/" })
//    public String home() {
//        return "index";
//    }

    @PostMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "hello";
    }

    @GetMapping("/hello")
    public String helloTest(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "hello";
    }
}
