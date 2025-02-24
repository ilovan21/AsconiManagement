package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.RestaurantReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RestaurantReservationRepository extends JpaRepository<RestaurantReservation, Integer> {
    List<RestaurantReservation> findByDate(LocalDate date);
    List<RestaurantReservation>findByHallId(Integer hallId);
    Optional<RestaurantReservation> findById(Integer reservationId);
}
