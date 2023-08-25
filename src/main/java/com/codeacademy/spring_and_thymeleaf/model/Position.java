package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceId", referencedColumnName = "deviceId")
    private Device device;

    public Position(LocalDateTime date, Double latitude, Double longitude, Integer speed, Device device) {
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.device = device;
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
