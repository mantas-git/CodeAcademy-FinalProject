package com.codeacademy.spring_and_thymeleaf.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "login";
    }

//    @RequestMapping({ "/home", "/" })
//    public String home() {
//        return "index";
//    }

    @PostMapping("/welcome")
    public String welcome() {
        return "redirect:/";
    }

//    @GetMapping("/hello")
//    public String helloTest(Model model) {
//        model.addAttribute("locale", LocaleContextHolder.getLocale());
//        return "hello";
//    }
}
