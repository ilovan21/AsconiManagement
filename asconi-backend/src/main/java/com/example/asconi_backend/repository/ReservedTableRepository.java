package com.example.asconi_backend.repository;

import com.example.asconi_backend.model.ReservedTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedTableRepository extends JpaRepository<ReservedTable, Integer> {

}
