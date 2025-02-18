package com.example.asconi_backend.service;

import com.example.asconi_backend.model.RestaurantReservation;
import com.example.asconi_backend.model.Hall;
import com.example.asconi_backend.repository.RestaurantReservationRepository;
import com.example.asconi_backend.repository.HallRepository;
import com.example.asconi_backend.repository.HallTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RestaurantReservationService {

    @Autowired
    private HallTableRepository hallTableRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private RestaurantReservationRepository reservationRestaurantRepository;

    @Transactional
    public RestaurantReservation reserveTable(Integer nrPeople, String hallName, LocalDate date,
                                              LocalTime arrivalTime, LocalTime leavingTime,
                                              String nameSurname, String email, String phone, String specifications) {
        // 1. Se obtin mesele disponibile
        List<Object[]> availableTables = hallTableRepository.findAvailableTables(nrPeople, hallName,
                date, arrivalTime, leavingTime);

        if (availableTables.isEmpty()) {
            throw new RuntimeException("Il n'y a pas des tables disponibiles dans la salle " + hallName + " à cette heure.");
        }

        // extragere prima masa disponibila si se asigneaza clientului
        Object[] table = availableTables.get(0);
        String hallNameResult = (String) table[0];  // Numele salii
        Integer tableId = (Integer) table[1];//id-ul mesei

        Hall hall = hallRepository.findByHallName(hallNameResult)
                .orElseThrow(() -> new RuntimeException("Erreur"));

        //ocupare masa disponibila in intervalul dorit de utilizator
        hallTableRepository.addIndisponibility(tableId, date, arrivalTime, leavingTime);

        RestaurantReservation reservation = new RestaurantReservation();
        reservation.setHall(hall);
        reservation.setNameSurname(nameSurname);
        reservation.setEmail(email);
        reservation.setPhone(phone);
        reservation.setDate(date);
        reservation.setArrivalTime(arrivalTime);
        reservation.setLeavingTime(leavingTime);
        reservation.setNrPeople(nrPeople);
        reservation.setSpecifications(specifications);
        return reservationRestaurantRepository.save(reservation);
    }
}

