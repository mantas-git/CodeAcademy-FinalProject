package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long deviceId;
    private LocalDateTime date;
    private Double latitude;
    private Double longitude;
    private Integer speed;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false )
    private Device device;

    public Position() {
    }

//    public Position(Long id, Long deviceId, LocalDateTime date, Double latitude, Double longitude, Integer speed, Device device) {
//        this.id = id;
//        this.deviceId = deviceId;
//        this.date = date;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.speed = speed;
//        this.device = device;
//    }
}
