package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "device", orphanRemoval = true)
    private List<Position> positions = new ArrayList<>();

//    public Device(Long id, Long deviceId, String transportNr, String comment, LocalDate createDate, Integer userId, List<Position> positions) {
//        this.id = id;
//        this.deviceId = deviceId;
//        this.transportNr = transportNr;
//        this.comment = comment;
//        this.createDate = createDate;
//        this.userId = userId;
//        this.positions = positions;
//    }

    public Device() {

    }
}
