package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vnr;
    private String comment;
    private LocalDate createDate;
    private Integer userId;

    public Device(Integer id, String vnr, String comment, LocalDate createDate, Integer userId) {
        this.id = id;
        this.vnr = vnr;
        this.comment = comment;
        this.createDate = createDate;
        this.userId = userId;
    }

    public Device() {

    }
}
