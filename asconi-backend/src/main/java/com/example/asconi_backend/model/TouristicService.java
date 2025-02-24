package com.example.asconi_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    @OneToMany(mappedBy = "touristicService")
    private List<ServiceHours> serviceHours;
    @OneToMany(mappedBy = "touristicService", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ServiceReservation> reservations;
}
