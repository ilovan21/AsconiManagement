package com.example.asconi_backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_restaurant")
public class RestaurantReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nom_prenom", nullable = false)
    private String nameSurname;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id")
    private Hall hall;

    private String email;
    private String phone;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "heure_arrivee", nullable = false)
    private LocalTime arrivingTime;

    @Column(name = "heure_partir", nullable = false)
    private LocalTime leavingTime;

    @Column(name = "nombre_personnes", nullable = false)
    private Integer nrPeople;

    @Column(columnDefinition = "TEXT")
    private String specifications;
    private Integer tableId;
}
