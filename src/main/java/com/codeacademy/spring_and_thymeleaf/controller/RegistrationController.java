package com.codeacademy.spring_and_thymeleaf.controller;


import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(User user, Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "registration";
    }
    @PostMapping
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        logger.info("BindingResult errors: {}", bindingResult);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(!userService.addUser(user)){
            model.addAttribute("validUser", false);
            return "registration";
        }
        return "redirect:/login";
    }
}
