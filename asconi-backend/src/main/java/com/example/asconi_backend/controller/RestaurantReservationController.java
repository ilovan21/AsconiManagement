package com.example.asconi_backend.controller;

import com.example.asconi_backend.dto.AvailabilityRestaurantRequestDTO;
import com.example.asconi_backend.dto.AvailabilityRestaurantResponseDTO;
import com.example.asconi_backend.dto.AvailabilityServiceRequestDTO;
import com.example.asconi_backend.dto.AvailabilityServiceResponseDTO;
import com.example.asconi_backend.model.Hall;
import com.example.asconi_backend.model.RestaurantReservation;
import com.example.asconi_backend.repository.HallRepository;
import com.example.asconi_backend.service.RestaurantReservationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/restaurant")
public class RestaurantReservationController {

    private final RestaurantReservationService restaurantReservationService;

    @Autowired
    public RestaurantReservationController(RestaurantReservationService restaurantReservationService) {
        this.restaurantReservationService = restaurantReservationService;
    }

    @GetMapping("/public/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody AvailabilityRestaurantRequestDTO request) {
        Integer nrPeople = request.getNrPeople();
        Integer hallId = request.getHallId();
        LocalDate date = request.getDate();
        LocalTime arrivingTime = request.getArrivingTime();
        LocalTime leavingTime = request.getLeavingTime();

        Optional<Object[]> availableTable = restaurantReservationService.getFirstAvailableTable(nrPeople, hallId, date, arrivingTime, leavingTime);
        if (availableTable.isPresent()) {
            Object[] tableData = availableTable.get();
            Integer tableId = (Integer) tableData[1];

            AvailabilityRestaurantResponseDTO response = new AvailabilityRestaurantResponseDTO(
                    true,
                    "Table available!",
                    request.getHallId(),
                    request.getDate(),
                    request.getArrivingTime(),
                    request.getLeavingTime(),
                    request.getNrPeople(),
                    tableId
            );
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("available", false);
            errorResponse.put("message", "No available tables.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @PostMapping("public/reserve")
    public ResponseEntity<RestaurantReservation> createReservation(@RequestBody @Valid RestaurantReservation reservationRequest) {
        try {
            RestaurantReservation reservation = restaurantReservationService.reserveTable(
                    reservationRequest.getNrPeople(),
                    reservationRequest.getHall().getId(),
                    reservationRequest.getDate(),
                    reservationRequest.getArrivingTime(),
                    reservationRequest.getLeavingTime(),
                    reservationRequest.getNameSurname(),
                    reservationRequest.getEmail(),
                    reservationRequest.getPhone(),
                    reservationRequest.getSpecifications(),
                    reservationRequest.getTableId()

            );
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/view/by-date")
    public List<RestaurantReservation> getReservationsByDate(
            @RequestParam(required = false) String date) {
        LocalDate parsedDate = (date != null) ? LocalDate.parse(date) : null;
        return restaurantReservationService.getReservationsByDate(parsedDate);
    }
    @GetMapping("/view/by-hall")
    public List<RestaurantReservation> getReservationsByHall(
            @RequestParam(required = false) Integer hallId) {
        return restaurantReservationService.getReservationsByHall(hallId);
    }
    @GetMapping("/view/filter")
    public List<RestaurantReservation> getReservationsByDateAndHall(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer hallId) {

        LocalDate parsedDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();

        if ( hallId == null) {
            return restaurantReservationService.getReservationsByDate(parsedDate);
        }
            return restaurantReservationService.getReservationsByDateAndHallId(parsedDate, hallId);
    }

    @DeleteMapping("/manage/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Integer id) {
        try {
            restaurantReservationService.deleteReservation(id);
            return ResponseEntity.ok("Reservation deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the reservation.");
        }
    }

}
