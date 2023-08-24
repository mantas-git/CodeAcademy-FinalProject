package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Table(name = "positions")
public class Position {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private LocalDateTime date;
    @Getter
    private Double latitude;
    @Getter
    private Double longitude;
    @Getter
    private Integer speed;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public Position(LocalDateTime date, Double latitude, Double longitude, Integer speed, Device device) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", date=" + date +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                '}';
    }
}
