package com.codeacademy.spring_and_thymeleaf.model;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
