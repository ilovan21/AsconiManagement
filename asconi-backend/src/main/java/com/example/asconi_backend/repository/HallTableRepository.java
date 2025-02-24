package com.example.asconi_backend.repository;
import com.example.asconi_backend.model.HallTable;
import com.example.asconi_backend.model.TouristicService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HallTableRepository extends JpaRepository<HallTable, Integer> {

    @Query("SELECT s.hallName, st.id, st.capacity FROM HallTable st " +
            "JOIN st.hall s " +
            "WHERE st.capacity >= :nrPeople " +
            "AND s.id = :hallId " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM ReservedTable it " +
            "    WHERE it.hallTable.id = st.id " +
            "    AND it.date = :date " +
            "    AND (" +
            "        (:arrivalTime BETWEEN it.arrivalTime AND it.leavingTime) " +
            "        OR " +
            "        (it.arrivalTime BETWEEN :arrivalTime AND :leavingTime) " +
            "    )" +
            ") " +
            "ORDER BY st.capacity ASC")
    List<Object[]> findAvailableTables(
            @Param("nrPeople") Integer nrPeople,
            @Param("hallId") Integer hallId,
            @Param("date") LocalDate date,
            @Param("arrivalTime") LocalTime arrivalTime,
            @Param("leavingTime") LocalTime leavingTime
    );
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO indisponibilite_table (table_id, data, heure_arrive, heure_parti) " +
            "VALUES (:tableId, :date, :heureArrivee, :heurePartir)", nativeQuery = true)
    void addIndisponibility(@Param("tableId") Integer tableId,
                            @Param("date") LocalDate date,
                            @Param("heureArrivee") LocalTime arrivalTime,
                            @Param("heurePartir") LocalTime leavingTime
        );
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM indisponibilite_table " +
            "WHERE table_id = :tableId " +
            "AND date = :date " +
            "AND heure_arrive = :arrivalTime " +
            "AND heure_parti = :leavingTime", nativeQuery = true)
    void deleteIndisponibility(@Param("tableId") Integer tableId,
                               @Param("date") LocalDate date,
                               @Param("arrivalTime") LocalTime arrivalTime,
                               @Param("leavingTime") LocalTime leavingTime);
    @Override
    Optional<HallTable> findById(Integer id);
}
