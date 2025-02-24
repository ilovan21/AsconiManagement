package com.example.asconi_backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class AvailabilityRestaurantResponseDTO {
    private boolean available;
    private String message;
    private Integer hallId;
    private LocalDate date;
    private LocalTime arrivingTime;
    private LocalTime leavingTime;
    private Integer nrPeople;
    private Integer tableId; // Add this

    // Constructorul actualizat
    public AvailabilityRestaurantResponseDTO(boolean available, String message, Integer hallId, LocalDate date,
                                             LocalTime arrivingTime, LocalTime leavingTime, Integer nrPeople, Integer tableId) {
        this.available = available;
        this.message = message;
        this.hallId = hallId;
        this.date = date;
        this.arrivingTime = arrivingTime;
        this.leavingTime = leavingTime;
        this.nrPeople = nrPeople;
        this.tableId = tableId;
    }

    // Getters și setters
}

