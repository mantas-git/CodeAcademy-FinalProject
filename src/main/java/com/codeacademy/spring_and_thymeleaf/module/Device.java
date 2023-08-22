package com.codeacademy.spring_and_thymeleaf.module;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Device {
    private Integer id;
    private String vnr;
    private String comment;
    private Date createDate;

    public Device(Integer id, String vnr, String comment, Date createDate) {
        this.id = id;
        this.vnr = vnr;
        this.comment = comment;
        this.createDate = createDate;
    }
}
