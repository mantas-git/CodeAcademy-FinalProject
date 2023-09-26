package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(User user, Model model) {
        List<User> users = userService.getAllUsers();
        logger.info("Found Users {}", users.size());
        model.addAttribute("users", users);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "users";
    }

    @PutMapping("/update")
    public String updateUsers(User user, RedirectAttributes redirectAttributes) {
        logger.info("User update. New users data: {}", user);
        redirectAttributes.addFlashAttribute("infoMessage", userService.updateUser(user));
        logger.info("User updated.");
        return "redirect:/users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        logger.info("Trying delete User with ID {}", id);
        userService.deleteUser(id);
        logger.info("User deleted");
        return "redirect:/users";
    }

}
