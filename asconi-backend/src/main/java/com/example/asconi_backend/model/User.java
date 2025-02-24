package com.example.asconi_backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
        @Column(name="password_hash")
        private String password;

        private String telephone;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        private Role role;

        public User(String name, String email, String password, String telephone, Role role) {
                this.name = name;
                this.email = email;
                this.password = password;
                this.telephone = telephone;
                this.role = role;
        }
    }