package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.ServiceReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationServiceRepository extends JpaRepository<ServiceReservation, Integer> {

    @Query(value = """
        WITH rezervari_existente AS (
            SELECT service_id, SUM(nr_personnes) AS total_rezervat
            FROM service_reserve
            WHERE date = :date 
            AND heure = :hour 
            GROUP BY service_id
        )
        SELECT st.id, st.nom, st.capacite, 
               COALESCE(re.total_rezervat, 0) AS persoane_rezervate,
               (st.capacite - COALESCE(re.total_rezervat, 0)) AS locuri_disponibile
        FROM service_touristique st
        LEFT JOIN rezervari_existente re ON st.id = re.service_id
        WHERE st.id = :serviceId
        GROUP BY st.id, st.nom, st.capacite, re.total_rezervat
        HAVING (st.capacite - COALESCE(re.total_rezervat, 0)) >= :nrPeople
        """, nativeQuery = true)
    List<Object[]> findAvailableServices(
            @Param("serviceId") Integer serviceId,
            @Param("date") LocalDate date,
            @Param("hour") LocalTime hour,
            @Param("nrPeople") Integer nrPeople
    );

    List<ServiceReservation> findByDate(LocalDate date);
    //filtrare rezervari dupa zile si ora
    List<ServiceReservation>findByDateAndHour(LocalDate date, LocalTime hour);
    List<ServiceReservation> findByTouristicServiceId(Integer serviceId);
    List<ServiceReservation> findByTouristicServiceIdAndDate(Integer serviceId, LocalDate date);
}

