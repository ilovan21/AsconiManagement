package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.ServiceAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceAvailabilityRepository extends JpaRepository<ServiceAvailability, Integer> {

    //extragere date indisponibile pentru un serviciu
    @Query("SELECT si.unavailableDate FROM ServiceAvailability si " +
            "JOIN si.touristicService st " +
            "WHERE st.id = :serviceId")
    List<LocalDate> findServiceAvailabilityByServiceId(@Param("serviceId") Integer serviceId);
}
