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
    private Long id;
    private Long deviceId;
    private String transportNr;
    private String comment;
    private LocalDate createDate;
    private Integer userId;

    public Device(Long id, String transportNr, String comment, LocalDate createDate, Integer userId) {
        this.id = id;
        this.transportNr = transportNr;
        this.comment = comment;
        this.createDate = createDate;
        this.userId = userId;
    }

    public Device() {

    }
}
