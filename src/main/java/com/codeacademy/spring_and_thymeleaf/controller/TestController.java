package com.codeacademy.spring_and_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @RequestMapping({ "/home", "/" })
//    public String home() {
//        return "index";
//    }

    @PostMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello")
    public String helloTest() {
        return "hello";
    }
}
