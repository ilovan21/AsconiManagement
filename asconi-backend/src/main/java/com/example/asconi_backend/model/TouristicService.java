package com.example.asconi_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service_touristique")
public class TouristicService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nom")
    private String name;

    @Column(name="duree",nullable=false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType type;

    @OneToMany(mappedBy = "touristicService")
    private List<ServiceHours> serviceHours;
}
