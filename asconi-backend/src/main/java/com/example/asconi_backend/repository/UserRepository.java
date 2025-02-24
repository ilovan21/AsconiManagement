package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
    boolean existsByName(String name);
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    String name(String name);
}
