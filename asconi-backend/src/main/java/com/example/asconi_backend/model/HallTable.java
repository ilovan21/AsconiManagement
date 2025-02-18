package com.example.asconi_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="salle_table")
public class HallTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Hall hall;

    @Column(name="capacite",nullable = false)
    private Integer capacity;

    @Column(name="statut",nullable = false, columnDefinition = "INT DEFAULT 0 CHECK (statut IN (0,1))")
    private Integer status = 0;
}
