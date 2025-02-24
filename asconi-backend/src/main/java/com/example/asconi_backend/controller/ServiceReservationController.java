package com.example.asconi_backend.controller;

import com.example.asconi_backend.dto.AvailabilityServiceRequestDTO;
import com.example.asconi_backend.dto.AvailabilityServiceResponseDTO;
import com.example.asconi_backend.dto.ServiceReservationDTO;
import com.example.asconi_backend.model.RestaurantReservation;
import com.example.asconi_backend.model.ServiceReservation;
import com.example.asconi_backend.model.TouristicService;
import com.example.asconi_backend.repository.ReservationServiceRepository;
import com.example.asconi_backend.repository.TouristicServiceRepository;
import com.example.asconi_backend.service.ServiceReservationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/public/service")
public class ServiceReservationController {

    private final TouristicServiceRepository touristicServiceRepository;
    private final ServiceReservationService reservationService;
    private final ServiceReservationService serviceReservationService;

    @Autowired
    public ServiceReservationController(TouristicServiceRepository touristicServiceRepository, ServiceReservationService reservationService, ServiceReservationService serviceReservationService) {
        this.touristicServiceRepository = touristicServiceRepository;
        this.reservationService = reservationService;
        this.serviceReservationService = serviceReservationService;
    }
    @GetMapping("/check-availability")
    public ResponseEntity<AvailabilityServiceResponseDTO> checkAvailability(@RequestBody AvailabilityServiceRequestDTO request) {
        boolean isAvailable = reservationService.checkAvailability(
                request.getServiceId(),
                request.getNrPeople(),
                request.getDate(),
                request.getHour()
        );
        AvailabilityServiceResponseDTO response = new AvailabilityServiceResponseDTO(
                isAvailable,
                isAvailable ? "Spots are available!" : "No available spots for the selected service.",
                request.getServiceId(),
                request.getDate(),
                request.getHour(),
                request.getNrPeople()
        );

        if (!isAvailable) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveService(@RequestBody @Valid ServiceReservationDTO reservationDTO) {
        try {
            System.out.println("Received reservation request: " + reservationDTO);

            // Fetch the TouristicService
            TouristicService service = touristicServiceRepository.findById(reservationDTO.getTouristicServiceId())
                    .orElseThrow(() -> new EntityNotFoundException("TouristicService not found"));

            // Make the reservation
            ServiceReservation serviceReservation = serviceReservationService.reserveService(
                    service.getId(),
                    reservationDTO.getNrPeople(),
                    reservationDTO.getNameSurname(),
                    reservationDTO.getEmail(),
                    reservationDTO.getPhone(),
                    reservationDTO.getDate(),
                    reservationDTO.getHour(),
                    reservationDTO.getLanguage(),
                    reservationDTO.getSpecifications()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(serviceReservation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the reservation.");
        }
    }
    @GetMapping("/by-date")
    public List<ServiceReservation> getReservationsByDate(
            @RequestParam(required = false) String date) {
        LocalDate parsedDate = (date != null) ? LocalDate.parse(date) : null;
        return serviceReservationService.getReservationsByDate(parsedDate);
    }

}
