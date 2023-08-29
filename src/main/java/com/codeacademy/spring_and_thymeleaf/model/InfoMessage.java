package com.codeacademy.spring_and_thymeleaf.model;

import lombok.Data;

@Data
public class InfoMessage {
    private boolean error;
    private String messageText;
}
