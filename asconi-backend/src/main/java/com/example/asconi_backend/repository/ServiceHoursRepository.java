package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.ServiceHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceHoursRepository extends JpaRepository<ServiceHours,Integer> {
    @Query("SELECT st.name, h.hour FROM ServiceHours h " +
            "JOIN h.touristicService st " +
            "WHERE st.id = :serviceId")
    List<Object> findServiceHoursByServiceId(@Param("serviceId") Integer serviceId);
}
