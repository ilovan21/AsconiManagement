package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.HallIndisponibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HallIndisponibilityRepository extends JpaRepository<HallIndisponibility,Integer> {
    //extragere date indisponibile pentru o anumita sala
    @Query("SELECT si.unavailableDate, si.reason FROM HallIndisponibility si " +
            "JOIN si.hall s " +
            "WHERE s.id = :hallId")
    List<LocalDate> findHallIndisponibilityByHallId(@Param("hallId") Integer hallId);
}
