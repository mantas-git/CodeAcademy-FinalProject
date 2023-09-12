package com.codeacademy.spring_and_thymeleaf.repository;

import com.codeacademy.spring_and_thymeleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}