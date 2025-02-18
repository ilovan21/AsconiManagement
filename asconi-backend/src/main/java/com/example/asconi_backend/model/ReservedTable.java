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
@Entity(name="ReservedTable")
@Table(name="indisponibilite_table")
public class ReservedTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private HallTable hallTable;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Column(name = "heure_arrive", nullable = false)
    private LocalTime arrivalTime;

    @Column(name = "heure_parti", nullable = false)
    private LocalTime leavingTime;

}
