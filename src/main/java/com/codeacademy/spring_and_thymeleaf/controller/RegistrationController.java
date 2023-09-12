package com.codeacademy.spring_and_thymeleaf.controller;


import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(User user) {

        return "registration";
    }
    @PostMapping
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }
}
