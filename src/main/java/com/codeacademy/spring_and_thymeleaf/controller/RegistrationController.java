package com.codeacademy.spring_and_thymeleaf.controller;


import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.io.UnsupportedEncodingException;

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

    @PostMapping()
    public String processRegister(@Valid User user,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  Model model)
            throws UnsupportedEncodingException, MessagingException {
        logger.info("Registration process BindingResult errors: {}", bindingResult);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(!userService.register(user, getSiteURL(request))){
            model.addAttribute("validUser", false);
            return "registration";
        }
        return "register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
