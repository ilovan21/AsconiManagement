package com.example.asconi_backend.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class AvailabilityServiceRequestDTO {
    private Integer serviceId;
    private LocalDate date;
    private LocalTime hour;
    private Integer nrPeople;

}

