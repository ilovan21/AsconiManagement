package com.example.asconi_backend.repository;
import com.example.asconi_backend.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {

    Optional<Hall> findByHallName(String hallName);

    @Query("SELECT s.id FROM Hall s WHERE s.hallName = :hallName")
    Optional<Integer> findIdByHallName(@Param("hallName") String hallName);
}
