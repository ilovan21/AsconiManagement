package com.example.asconi_backend.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class AvailabilityRestaurantRequestDTO {
    private Integer hallId;
    private LocalDate date;
    private LocalTime arrivingTime;
    private LocalTime leavingTime;
    private Integer nrPeople;
}
