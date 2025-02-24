package com.example.asconi_backend.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservation_service")
public class ServiceReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false, referencedColumnName = "id")
    //@JsonIgnore
    //@JsonBackReference
    private TouristicService touristicService;
    @Column(name = "nom_prenom", nullable = false)
    private String nameSurname;
    private String email;
    private String phone;
    private LocalDate date;
    @Column(name="heure")
    private LocalTime hour;
    @Column(name="langue")
    private String language;
    @Column(name="nombre_personnes")
    private Integer nrPeople;
    @Column(columnDefinition = "TEXT")
    private String specifications;

}
