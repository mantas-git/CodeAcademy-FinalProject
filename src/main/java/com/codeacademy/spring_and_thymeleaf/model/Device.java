package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Device(Integer id, String vnr, String comment, LocalDate createDate) {
        this.id = id;
        this.vnr = vnr;
        this.comment = comment;
        this.createDate = createDate;
    }

    public Device() {

    }
}
