package com.example.asconi_backend.repository;
import com.example.asconi_backend.model.HallTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HallTableRepository extends JpaRepository<HallTable, Integer> {

    @Query("SELECT s.hallName, st.id, st.capacity FROM HallTable st " +
            "JOIN st.hall s " +
            "WHERE st.capacity >= :nrPersonnes " +
            "AND s.hallName = :salleNom " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM ReservedTable it " +
            "    WHERE it.hallTable.id = st.id " +
            "    AND it.date =:date" +
            "    AND (" +
            "        (:ora BETWEEN it.arrivalTime AND it.leavingTime) " +
            "        OR " +
            "        (it.arrivalTime BETWEEN :ora AND :leavingTime) " +
            "    )" +
            ")")
    List<Object[]> findAvailableTables(
            @Param("nrPeople") Integer nrPeople,
            @Param("hallName") String hallName,
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
                            @Param("heurePartir") LocalTime leavingTime);
}
