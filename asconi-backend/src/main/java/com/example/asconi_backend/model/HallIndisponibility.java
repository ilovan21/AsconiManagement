package com.example.asconi_backend.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salle_indisponibilite")
public class HallIndisponibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Hall hall;

    @Column(name = "date_indisponible", nullable = false)
    private LocalDate unavailableDate;

    @Column(name="motif", columnDefinition = "TEXT")
    private String reason;
}
