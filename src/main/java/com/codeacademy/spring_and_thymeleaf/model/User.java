package com.codeacademy.spring_and_thymeleaf.model;

//import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String email;
    private String role;
}
