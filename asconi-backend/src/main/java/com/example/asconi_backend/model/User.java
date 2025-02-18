package com.example.asconi_backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="utilisateur")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name="nom")
        private String name;
        private String email;

        private String password_hash;
        private String telephone;
        @ManyToOne
        @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
        private Role role;
    }