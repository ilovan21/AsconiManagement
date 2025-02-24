package com.example.asconi_backend.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.asconi_backend.model.TouristicService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TouristicServiceRepository extends JpaRepository<TouristicService, Integer> {
    List<TouristicService> findByName(String name);

    @Override
    Optional<TouristicService> findById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO service_reserve (service_id, date, heure,nr_personnes) " +
            "VALUES (:serviceId, :date, :hour, :nrPeople)", nativeQuery = true)
    void addServiceIndisponibility(@Param("serviceId") Integer serviceId,
                            @Param("date") LocalDate date,
                            @Param("hour") LocalTime hour,
                            @Param("nrPeople") Integer nrPeople);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM service_reserve " +
            "WHERE service_id = :serviceId " +
            "AND date = :date " +
            "AND heure = :hour " +
            "AND nr_personnes = :nrPeople", nativeQuery = true)
    void deleteServiceIndisponibility(@Param("serviceId") Integer service_id,
                               @Param("date") LocalDate date,
                               @Param("hour") LocalTime hour,
                               @Param("nrPeople") Integer nrPeople);
    }
