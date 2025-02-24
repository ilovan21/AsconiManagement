package com.example.asconi_backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityServiceResponseDTO {
    private boolean available;
    private String message;
    private Integer serviceId;
    private LocalDate date;
    private LocalTime hour;
    private Integer nrPeople;

    public AvailabilityServiceResponseDTO(boolean available, String message, Integer serviceId, LocalDate date, LocalTime hour, Integer nrPeople) {
        this.available = available;
        this.message = message;
        this.serviceId = serviceId;
        this.date = date;
        this.hour = hour;
        this.nrPeople = nrPeople;
    }

    public boolean isAvailable() { return available; }
    public String getMessage() { return message; }
    public Integer getServiceId() { return serviceId; }
    public LocalDate getDate() { return date; }
    public LocalTime getHour() { return hour; }
    public Integer getNrPeople() { return nrPeople; }
}
