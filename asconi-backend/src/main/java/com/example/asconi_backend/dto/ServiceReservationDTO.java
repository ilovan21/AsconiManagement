package com.example.asconi_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class ServiceReservationDTO {
    private Integer touristicServiceId;  // Only the ID
    private Integer nrPeople;
    private String nameSurname;
    private String email;
    private String phone;
    private LocalDate date;
    private LocalTime hour;
    private String language;
    private String specifications;

    // Getters and setters
}

